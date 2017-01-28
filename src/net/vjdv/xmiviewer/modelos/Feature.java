package net.vjdv.xmiviewer.modelos;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Jassiel
 */
public class Feature {
  @XmlAttribute(name="name")
  public String nombre;
  @Override
  public String toString(){
    return nombre;
  }
}