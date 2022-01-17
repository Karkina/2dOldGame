/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import tools.HardCodedParameters;
import tools.Position;
import tools.Sound;

import specifications.DataService;
import specifications.PhantomService;
import specifications.PilierService;


import data.ia.PilierStatic;
import data.ia.MoveLeftPhantom;

import java.util.ArrayList;

public class Data implements DataService{
  //private Heroes hercules;
  private Position heroesPosition;
  private int stepNumber, score;
  private ArrayList<PhantomService> phantoms;
  private ArrayList<PhantomService> phantoms5PV;
  private ArrayList<PilierService> piliers;
  private double heroesWidth,heroesHeight,phantomWidth,phantomHeight,pilierWidth,pilierHeight;
  private Sound.SOUND sound;
  private int maxHorizontal, maxVertical,minHorizontal,minVertical;
  private int round;
  public Data(){}

  @Override
  public void init(){
    //hercules = new Heroes;
    heroesPosition = new Position(HardCodedParameters.heroesStartX,HardCodedParameters.heroesStartY);
    phantoms = new ArrayList<PhantomService>();
    phantoms5PV = new ArrayList<PhantomService>();
    stepNumber = 0;
    score = 0;
    round =1;
    maxHorizontal = 1180;
    minHorizontal = 50;
    maxVertical =500;
    minVertical=10;
    heroesWidth = HardCodedParameters.heroesWidth;
    heroesHeight = HardCodedParameters.heroesHeight;
    phantomWidth = HardCodedParameters.phantomWidth;
    phantomHeight = HardCodedParameters.phantomHeight;
    pilierWidth = HardCodedParameters.pilierWidth;
    pilierHeight = HardCodedParameters.pilierHeight;
    piliers = new ArrayList<PilierService>();
    sound = Sound.SOUND.None;
  }


  @Override
  public int getRound(){ return this.round; }

  @Override
  public void setRound(int newRound){  this.round=newRound ; }
  @Override
  public Position getHeroesPosition(){ return heroesPosition; }
  
  @Override
  public double getHeroesWidth(){ return heroesWidth; }
  
  @Override
  public double getHeroesHeight(){ return heroesHeight; }
  
  @Override
  public double getPhantomWidth(){ return phantomWidth; }
  
  @Override
  public double getPhantomHeight(){ return phantomHeight; }

  @Override
  public double getPilierWidth(){ return pilierWidth; }

  @Override
  public double getPilierHeight(){ return pilierHeight; }

  @Override
  public int getStepNumber(){ return stepNumber; }
  
  @Override
  public int getScore(){ return score; }

  @Override
  public ArrayList<PhantomService> getPhantoms(){ return phantoms; }
  @Override
  public ArrayList<PhantomService> getPhantoms5PV(){ return phantoms5PV; }


  @Override
  public ArrayList<PilierService> getPiliers(){return piliers;}
  
  @Override
  public Sound.SOUND getSoundEffect() { return sound; }

  @Override
  public void setHeroesPosition(Position p) { if(p.x <maxHorizontal && p.x > minHorizontal && p.y <maxVertical && p.y > minVertical) heroesPosition = p; }
  
  @Override
  public void setStepNumber(int n){ stepNumber=n; }
  
  @Override
  public void addScore(int score){ this.score+=score; }

  @Override
  public void removeScore(int score){ this.score-=score; }

  @Override
  public void setScore( int scoreAmount){  this.score=scoreAmount; }

  @Override
  public void addPhantom(Position p) { phantoms.add(new MoveLeftPhantom(p)); }

  @Override
  public void addPhantom5PV(Position p) { phantoms5PV.add(new MoveLeftPhantom(p)); }

  @Override
  public void removePhantom(PhantomService p) { phantoms.remove(p); }

  @Override
  public void addPilier(Position p) { piliers.add(new PilierStatic(p)); }

  
  @Override
  public void setPhantoms(ArrayList<PhantomService> phantoms) { this.phantoms=phantoms; }

  @Override
  public void setPhantoms5PV(ArrayList<PhantomService> phantoms) { this.phantoms5PV=phantoms; }
  
  @Override
  public void setSoundEffect(Sound.SOUND s) { sound=s; }
}
