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
import specifications.LaserService;

import data.ia.MoveLeftPhantom;
import data.ia.MoveRightLaser;

import java.util.ArrayList;

public class Data implements DataService{
  //private Heroes hercules;
  private Position heroesPosition;
  private int stepNumber, score;
  private ArrayList<PhantomService> phantoms;
  private ArrayList<LaserService> lasers;
  private double heroesWidth,heroesHeight,phantomWidth,phantomHeight,laserWidth,laserHeight;
  private Sound.SOUND sound;

  public Data(){}

  @Override
  public void init(){
    //hercules = new Heroes;
    heroesPosition = new Position(HardCodedParameters.heroesStartX,HardCodedParameters.heroesStartY);
    phantoms = new ArrayList<PhantomService>();
    lasers = new ArrayList<LaserService>();
    stepNumber = 0;
    score = 0;
    heroesWidth = HardCodedParameters.heroesWidth;
    heroesHeight = HardCodedParameters.heroesHeight;
    phantomWidth = HardCodedParameters.phantomWidth;
    phantomHeight = HardCodedParameters.phantomHeight;

    laserWidth = HardCodedParameters.laserWidth;
    laserHeight = HardCodedParameters.laserHeight;
    sound = Sound.SOUND.None;
  }

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
  public double getLaserWidth(){ return laserWidth; }

  @Override
  public double getLaserHeight(){ return laserHeight; }

  @Override
  public int getStepNumber(){ return stepNumber; }

  @Override
  public int getScore(){ return score; }

  @Override
  public ArrayList<PhantomService> getPhantoms(){ return phantoms; }

  @Override
  public ArrayList<LaserService> getLasers(){ return lasers; }

  @Override
  public Sound.SOUND getSoundEffect() { return sound; }

  @Override
  public void setHeroesPosition(Position p) { heroesPosition=p; }

  @Override
  public void setStepNumber(int n){ stepNumber=n; }

  @Override
  public void addScore(int score){ this.score+=score; }

  @Override
  public void addPhantom(Position p) { phantoms.add(new MoveLeftPhantom(p)); }

  @Override
  public void setPhantoms(ArrayList<PhantomService> phantoms) { this.phantoms=phantoms; }

  @Override
  public void addLaser(Position p) { lasers.add(new MoveRightLaser(p)); }

  @Override
  public void setLasers(ArrayList<LaserService> lasers) { this.lasers=lasers; }

  @Override
  public void setSoundEffect(Sound.SOUND s) { sound=s; }
}
