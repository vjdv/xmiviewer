package net.vjdv.xmiviewer.modelos;

import java.util.Random;

/**
 * Métodos estáticos que sirven de ayuda a otras clases
 * @author Jassiel
 */
public class Utils {
  public static String getRandomID(String seed){
    Random r = new Random();
    seed = (seed==null || seed.isEmpty()) ? "" : seed+"_";
    String time = ((Long)System.currentTimeMillis()).toString().substring(7);
    int num = r.nextInt(99999);
    return seed+time+"_"+num;
  }
  public static String getRandomID(){
    return getRandomID("element");
  }
}