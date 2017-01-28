package net.vjdv.xmiviewer.modelos;

/**
 *
 * @author Jassiel
 */
public class GeoL {
  public double x1,y1,x2,y2;
  public GeoL(double geo[]){
    x1 = geo[0];
    y1 = geo[1];
    x2 = geo[2];
    y2 = geo[3];
  }
  public double[] toDouble(){
    return new double[]{x1,y1,x2,y2};
  }
  @Override
  public String toString(){
    return x1+","+y1+";"+x2+","+y2+"";
  }
}