/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import tools.HardCodedParameters;
import tools.User;
import tools.Position;
import tools.Sound;

import specifications.EngineService;
import specifications.DataService;
import specifications.RequireDataService;
import specifications.PhantomService;
import specifications.PilierService;

import java.util.Timer;
import javafx.scene.image.ImageView;
import java.util.TimerTask;
import javafx.scene.layout.BorderPane;
import java.util.Random;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.stage.Popup;


public class Engine implements EngineService, RequireDataService{
  private static final double friction=HardCodedParameters.friction,
                              heroesStep=HardCodedParameters.heroesStep,
                              phantomStep=HardCodedParameters.phantomStep;
  private Timer engineClock;
  private DataService data;
  private User.COMMAND command;
  private Random gen;
  private float timerHitElectro;
  private boolean moveLeft,moveRight,moveUp,moveDown;
  private double heroesVX,heroesVY;

  public Engine(){}

  @Override
  public void bindDataService(DataService service){
    data=service;
  }
  
  @Override
  public void init(){
    engineClock = new Timer();
    command = User.COMMAND.NONE;
    gen = new Random();
    data.addPilier(new Position(300,400));
    data.addPilier(new Position(500,150));
    data.addPilier(new Position(250,20));
    data.addPilier(new Position(650,175));
    moveLeft = false;
    moveRight = false;
    moveUp = false;
    moveDown = false;
    timerHitElectro = 2;
    heroesVX = 0;
    heroesVY = 0;
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {
        //System.out.println("Game step #"+data.getStepNumber()+": checked.");
        
        if (gen.nextInt(80)<3) spawnPhantom();

        if (gen.nextInt(200)<3) spawnPhantom5PV();

        updateSpeedHeroes();
        updateCommandHeroes();
        updatePositionHeroes();
        testPositionPhantom();

        ArrayList<PhantomService> phantoms = new ArrayList<PhantomService>();
        ArrayList<PhantomService> phantoms5PV = new ArrayList<PhantomService>();
        int score=0;

        data.setSoundEffect(Sound.SOUND.None);

        for (PhantomService p:data.getPhantoms()){
          if (p.getAction()==PhantomService.MOVE.LEFT) moveLeft(p);
          if (p.getAction()==PhantomService.MOVE.RIGHT) moveRight(p);
          if (p.getAction()==PhantomService.MOVE.UP) moveUp(p);
          if (p.getAction()==PhantomService.MOVE.DOWN) moveDown(p);

          if (collisionHeroesPhantom(p)){
            data.setSoundEffect(Sound.SOUND.HeroesGotHit);
            score++;
          } else {
            if (p.getPosition().x>0) phantoms.add(p);
          }
        }

        for (PhantomService p:data.getPhantoms5PV()){
          if (p.getAction()==PhantomService.MOVE.LEFT) moveLeft(p);
          if (p.getAction()==PhantomService.MOVE.RIGHT) moveRight(p);
          if (p.getAction()==PhantomService.MOVE.UP) moveUp(p);
          if (p.getAction()==PhantomService.MOVE.DOWN) moveDown(p);

          if (collisionHeroesPhantom(p)){
            data.setSoundEffect(Sound.SOUND.HeroesGotHit);
            score+=3;
          } else {
            if (p.getPosition().x>0) phantoms5PV.add(p);
          }
        }

        for (PilierService p : data.getPiliers()) {

          if (collisionHeroesPilliersTest(p) && timerHitElectro <0){
            int xElec= gen.nextInt(500);
            int yElec= gen.nextInt(500);
            data.setSoundEffect(Sound.SOUND.ShipElectrocut);
            System.out.println("Aie le pilier");
            timerHitElectro=2;
            score+=10;
            p.setPosition(new Position(xElec,yElec));
          }
        }
        timerHitElectro-=0.1;
        System.out.println(timerHitElectro);

        data.addScore(score);

        data.setPhantoms(phantoms);
        data.setPhantoms5PV(phantoms5PV);

        data.setStepNumber(data.getStepNumber()+1);
      }
    },0,HardCodedParameters.enginePaceMillis);
  }

  @Override
  public void stop(){
    engineClock.cancel();
  }


  @Override
  public void setHeroesCommand(User.COMMAND c){
    if (c==User.COMMAND.LEFT) moveLeft=true;
    if (c==User.COMMAND.RIGHT) moveRight=true;
    if (c==User.COMMAND.UP) moveUp=true;
    if (c==User.COMMAND.DOWN) moveDown=true;
  }
  
  @Override
  public void releaseHeroesCommand(User.COMMAND c){
    if (c==User.COMMAND.LEFT) moveLeft=false;
    if (c==User.COMMAND.RIGHT) moveRight=false;
    if (c==User.COMMAND.UP) moveUp=false;
    if (c==User.COMMAND.DOWN) moveDown=false;
  }

  private void updateSpeedHeroes(){
    heroesVX*=friction;
    heroesVY*=friction;
  }

  private void updateCommandHeroes(){
    if (moveLeft) heroesVX-=heroesStep;
    if (moveRight) heroesVX+=heroesStep;
    if (moveUp) heroesVY-=heroesStep;
    if (moveDown) heroesVY+=heroesStep;
  }
  
  private void updatePositionHeroes(){
    data.setHeroesPosition(new Position(data.getHeroesPosition().x+heroesVX,data.getHeroesPosition().y+heroesVY));
    //if (data.getHeroesPosition().x<0) data.setHeroesPosition(new Position(0,data.getHeroesPosition().y));
    //etc...
  }

  private void spawnPhantom(){
    int x=(int)(HardCodedParameters.defaultWidth*.9);
    int y=0;
    boolean cont=true;
    while (cont) {
      y=(int)(gen.nextInt((int)(HardCodedParameters.defaultHeight*.6))+HardCodedParameters.defaultHeight*.1);
      cont=false;
      for (PhantomService p:data.getPhantoms()){
        if (p.getPosition().equals(new Position(x,y))) cont=true;
      }
    }
    data.addPhantom(new Position(x,y));
  }

  private void spawnPhantom5PV(){
    int x=(int)(HardCodedParameters.defaultWidth*.9);
    int y=0;
    boolean cont=true;
    while (cont) {
      y=(int)(gen.nextInt((int)(HardCodedParameters.defaultHeight*.6))+HardCodedParameters.defaultHeight*.1);
      cont=false;
      for (PhantomService p:data.getPhantoms()){
        if (p.getPosition().equals(new Position(x,y))) cont=true;
      }
    }
    data.addPhantom5PV(new Position(x,y));
  }

  private void moveLeft(PhantomService p){
    p.setPosition(new Position(p.getPosition().x-phantomStep,p.getPosition().y));
  }

  private void moveRight(PhantomService p){
    p.setPosition(new Position(p.getPosition().x+phantomStep,p.getPosition().y));
  }
  private void testPositionPhantom()
  {

    for (PhantomService p:data.getPhantoms()){
      if(p.getPosition().x < 5){
        System.out.println(" JE VEUX PAS mourrir");
        //data.removePhantom(p);
        data.removeScore(1);

      }
      }

    for (PhantomService p:data.getPhantoms5PV()){
      if(p.getPosition().x < 5){
        System.out.println(" JE VEUX PAS mourrir");
        //data.removePhantom(p);
        data.removeScore(5);

      }
    }
  }
  private void moveUp(PhantomService p){
    p.setPosition(new Position(p.getPosition().x,p.getPosition().y-phantomStep));
  }

  private void moveDown(PhantomService p){
    p.setPosition(new Position(p.getPosition().x,p.getPosition().y+phantomStep));
  }

  private boolean collisionHeroesPhantom(PhantomService p){
    return (
      (data.getHeroesPosition().x-p.getPosition().x)*(data.getHeroesPosition().x-p.getPosition().x)+
      (data.getHeroesPosition().y-p.getPosition().y)*(data.getHeroesPosition().y-p.getPosition().y) <
      0.25*(data.getHeroesWidth()+data.getPhantomWidth())*(data.getHeroesWidth()+data.getPhantomWidth())
    );
  }

  private boolean collisionHeroesPilliers(PilierService p){
    return (
            (data.getHeroesPosition().x-p.getPosition().x)*(data.getHeroesPosition().x-p.getPosition().x)+
                    (data.getHeroesPosition().y-p.getPosition().y)*(data.getHeroesPosition().y-p.getPosition().y) <
                    0.25*(data.getHeroesWidth()+data.getPilierWidth())*(data.getHeroesWidth()+data.getPilierWidth())
    );
  }

  private boolean collisionHeroesPilliersTest(PilierService p){
    return (
            (Math.abs(data.getHeroesPosition().x-p.getPosition().x)<data.getPilierWidth()) &&(Math.abs(data.getHeroesPosition().y-p.getPosition().y)<data.getPilierHeight())
    );
  }


  private boolean collisionHeroesPhantoms(){
    for (PhantomService p:data.getPhantoms()) if (collisionHeroesPhantom(p)) return true; return false;
  }
}
