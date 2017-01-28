package net.vjdv.xmiviewer.modelos;

import java.util.Random;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Jassiel
 */
public class OwnedAttribute {
  //<ownedAttribute aggregation="none" isDerived="false" isDerivedUnion="false" isID="false" isLeaf="false" isReadOnly="false" isStatic="false" name="hola" type="String_id" visibility="private" xmi:id="jbhmJdKGAqACQwu6" xmi:type="uml:Property">
  @XmlAttribute
  public String aggregation,name,type,visibility;
  @XmlAttribute
  public Boolean isDerived,isDerivedUnion,isID,isLeaf,isReadOnly,isStatic;
  @XmlAttribute(namespace = "http://schema.omg.org/spec/XMI/2.1")
  public String id;
  @XmlAttribute(name="type",namespace = "http://schema.omg.org/spec/XMI/2.1")
  public String typexmi;
  
  public void crearIdAleatorio(){
    Random r = new Random();
    id = "attr"+r.nextInt(9999999);
  }
}