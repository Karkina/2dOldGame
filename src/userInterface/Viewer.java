/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import tools.HardCodedParameters;

import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.PhantomService;
import specifications.PilierService;
import tools.Position;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Popup;

import java.util.ArrayList;

public class Viewer implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private ReadService data;
  private ImageView heroesAvatar;
  private Image heroesSpriteSheet;
  private ArrayList<Rectangle2D> heroesAvatarViewports;
  private ArrayList<Integer> heroesAvatarXModifiers;
  private ArrayList<Integer> heroesAvatarYModifiers;
  private int heroesAvatarViewportIndex;
  private double xShrink,yShrink,shrink,xModifier,yModifier,heroesScale;
  private  Popup popup;

  public Viewer(){}
  
  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){
    xShrink=1;
    yShrink=1;
    xModifier=0;
    yModifier=0;
    popup = new Popup();
    Button button = new Button("button");
    Image imgGameOVer = new Image("file:src/images/gameOver2.png");
    ImageView imageView = new ImageView(imgGameOVer);
    popup.getContent().add(imageView);
    imageView.setFitHeight(400);
    imageView.setFitWidth(400);

    //Yucky hard-conding
    //heroesSpriteSheet = new Image("file:src/images/modern soldier large.png");
    heroesSpriteSheet = new Image("file:src/images/vaisseau.png");
    heroesAvatar = new ImageView(heroesSpriteSheet);
    heroesAvatarViewports = new ArrayList<Rectangle2D>();
    heroesAvatarXModifiers = new ArrayList<Integer>();
    heroesAvatarYModifiers = new ArrayList<Integer>();

    heroesAvatarViewportIndex=0;
    
    //TODO: replace the following with XML loader
    //heroesAvatarViewports.add(new Rectangle2D(301,386,95,192));
    /*heroesAvatarViewports.add(new Rectangle2D(570,194,115,190));

    heroesAvatarViewports.add(new Rectangle2D(570,194,115,190));
    heroesAvatarViewports.add(new Rectangle2D(398,386,133,192));
    heroesAvatarViewports.add(new Rectangle2D(155,194,147,190));
    heroesAvatarViewports.add(new Rectangle2D(785,386,127,194));
    heroesAvatarViewports.add(new Rectangle2D(127,582,135,198));
    heroesAvatarViewports.add(new Rectangle2D(264,582,111,200));
    heroesAvatarViewports.add(new Rectangle2D(2,582,123,198));
    heroesAvatarViewports.add(new Rectangle2D(533,386,115,192));*/


    heroesAvatarViewports.add(new Rectangle2D(0,0,2000,2000));
    heroesAvatarViewports.add(new Rectangle2D(0,0,2000,2000));
    heroesAvatarViewports.add(new Rectangle2D(0,0,2000,2000));
    heroesAvatarViewports.add(new Rectangle2D(0,0,2000,2000));
    heroesAvatarViewports.add(new Rectangle2D(0,0,2000,2000));
    heroesAvatarViewports.add(new Rectangle2D(0,0,2000,2000));
    heroesAvatarViewports.add(new Rectangle2D(0,0,2000,2000));
    heroesAvatarViewports.add(new Rectangle2D(0,0,2000,2000));

    //heroesAvatarViewports.add(new Rectangle2D(204,386,95,192));

    //heroesAvatarXModifiers.add(10);heroesAvatarYModifiers.add(-7);

    heroesAvatarXModifiers.add(6);heroesAvatarYModifiers.add(-6);
    heroesAvatarXModifiers.add(2);heroesAvatarYModifiers.add(-8);
    heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-10);
    heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-13);
    heroesAvatarXModifiers.add(5);heroesAvatarYModifiers.add(-15);
    heroesAvatarXModifiers.add(5);heroesAvatarYModifiers.add(-13);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(-9);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(-6);

    //heroesAvatarXModifiers.add(10);heroesAvatarYModifiers.add(-7);
    
  }

  @Override
  public Popup getPopUp(){
        return popup;

  }
  @Override
  public Parent getPanel(Stage stage){


    shrink=Math.min(xShrink,yShrink);
    xModifier=.01*shrink*defaultMainHeight;
    yModifier=.01*shrink*defaultMainHeight;

    //Yucky hard-conding
    Rectangle map = new Rectangle(-2*xModifier+shrink*defaultMainWidth,
                                  -.2*shrink*defaultMainHeight+shrink*defaultMainHeight);
    Image img = new Image("file:src/images/astrologie.jpg");
    map.setFill(new ImagePattern(img));
    map.setStroke(Color.DIMGRAY);
    map.setStrokeWidth(.01*shrink*defaultMainHeight);
    map.setArcWidth(.04*shrink*defaultMainHeight);
    map.setArcHeight(.04*shrink*defaultMainHeight);
    map.setTranslateX(xModifier);
    map.setTranslateY(yModifier);
    
    Text greets = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
                           -0.1*shrink*defaultMainWidth+shrink*defaultMainHeight,
                           "Round 1");
    greets.setFont(new Font(.05*shrink*defaultMainHeight));
    greets.setFill(Color.WHITE);
    
    Text score = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
                           -0.05*shrink*defaultMainWidth+shrink*defaultMainHeight,
                           "Score: "+data.getScore());
    score.setFont(new Font(.05*shrink*defaultMainHeight));
    score.setFill(Color.WHITE);
    
    //int index=heroesAvatarViewportIndex/spriteSlowDownRate;
    int index =0;
    heroesScale=data.getHeroesHeight()*shrink/heroesAvatarViewports.get(index).getHeight();
    heroesAvatar.setViewport(heroesAvatarViewports.get(index));
    heroesAvatar.setFitHeight(data.getHeroesHeight()*shrink);
    heroesAvatar.setPreserveRatio(true);
    heroesAvatar.setTranslateX(shrink*data.getHeroesPosition().x+
                               shrink*xModifier+
                               -heroesScale*0.5*heroesAvatarViewports.get(index).getWidth()+
                               shrink*heroesScale*heroesAvatarXModifiers.get(index)
                              );
    heroesAvatar.setTranslateY(shrink*data.getHeroesPosition().y+
                               shrink*yModifier+
                               -heroesScale*0.5*heroesAvatarViewports.get(index).getHeight()+
                               shrink*heroesScale*heroesAvatarYModifiers.get(index)
                              );
    heroesAvatarViewportIndex=(heroesAvatarViewportIndex+1)%(heroesAvatarViewports.size()*spriteSlowDownRate);

    Group panel = new Group();
    panel.getChildren().addAll(map,greets,score,heroesAvatar);

    ArrayList<PhantomService> phantoms = data.getPhantoms();
    PhantomService p;

    for (int i=0; i<phantoms.size();i++) {
      p = phantoms.get(i);
      double radius = .5 * Math.min(shrink * data.getPhantomWidth(), shrink * data.getPhantomHeight());
      Circle phantomAvatar = new Circle(radius);
      Image imgCreature = new Image("file:src/images/creature.png");
      phantomAvatar.setFill(new ImagePattern(imgCreature));
      phantomAvatar.setEffect(new Lighting());
      phantomAvatar.setTranslateX(shrink * p.getPosition().x + shrink * xModifier - radius);
      phantomAvatar.setTranslateY(shrink * p.getPosition().y + shrink * yModifier - radius);
      panel.getChildren().add(phantomAvatar);
    }


    ArrayList<PhantomService> phantoms5PV = data.getPhantoms5PV();
    PhantomService p5PV;

    for (int i=0; i<phantoms5PV.size();i++) {
      p5PV = phantoms5PV.get(i);
      double radius = .5 * Math.min(shrink * data.getPhantomWidth(), shrink * data.getPhantomHeight());
      Circle phantomAvatar = new Circle(radius);
      Image imgCreature = new Image("file:src/images/extra5PV.png");
      phantomAvatar.setFill(new ImagePattern(imgCreature));
      phantomAvatar.setEffect(new Lighting());
      phantomAvatar.setTranslateX(shrink * p5PV.getPosition().x + shrink * xModifier - radius);
      phantomAvatar.setTranslateY(shrink * p5PV.getPosition().y + shrink * yModifier - radius);
      panel.getChildren().add(phantomAvatar);
    }

    ArrayList<PilierService> pilars = data.getPiliers();

    PilierService pilar;
    for (int i=0; i<pilars.size();i++){
      pilar=pilars.get(i);
      double radius=.5*Math.min(shrink*data.getPhantomWidth(),shrink*data.getPhantomHeight());
      Rectangle pilarAvatar = new Rectangle(data.getPilierWidth(),data.getPilierHeight(),Color.rgb(0,0,0));
      Image imgElec = new Image("file:src/images/electro.png");
      pilarAvatar.setFill(new ImagePattern(imgElec));
      pilarAvatar.setEffect(new Lighting());
      pilarAvatar.setTranslateX(shrink*pilar.getPosition().x+shrink*xModifier-radius);
      pilarAvatar.setTranslateY(shrink*pilar.getPosition().y+shrink*yModifier-radius);
      panel.getChildren().add(pilarAvatar);
    }

    return panel;
  }
  public Parent getGameOver(){

    Rectangle map = new Rectangle(-2*xModifier+shrink*defaultMainWidth,
            -.2*shrink*defaultMainHeight+shrink*defaultMainHeight);
    Image img = new Image("file:src/images/gameOver2.png");
    map.setFill(new ImagePattern(img));
    map.setStroke(Color.DIMGRAY);
    map.setStrokeWidth(.01*shrink*defaultMainHeight);
    map.setArcWidth(.04*shrink*defaultMainHeight);
    map.setArcHeight(.04*shrink*defaultMainHeight);
    map.setTranslateX(xModifier);
    map.setTranslateY(yModifier);

    Group panel = new Group();
    panel.getChildren().addAll(map);
    return  panel;

  }


  @Override
  public void setMainWindowWidth(double width){
    xShrink=width/defaultMainWidth;
  }
  
  @Override
  public void setMainWindowHeight(double height){
    yShrink=height/defaultMainHeight;
  }

}
