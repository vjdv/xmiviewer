package net.vjdv.xmiviewer.modelos;

import javafx.geometry.Rectangle2D;

/**
 *
 * @author Jassiel
 */
public class Geo {
  public double x,y;
  public double w,h;
  public Geo(double geo[]){
    x = geo[0];
    y = geo[1];
    w = geo[2];
    h = geo[3];
  }
  public double[] toDouble(){
    return new double[]{x,y,w,h};
  }
  public Rectangle2D toRectangle(){
    return new Rectangle2D(x, y, w, h);
  }
  @Override
  public String toString(){
    return x+","+y+","+w+","+h;
  }
}