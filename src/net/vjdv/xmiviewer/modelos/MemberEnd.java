package net.vjdv.xmiviewer.modelos;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Jassiel
 */
public class MemberEnd {
  @XmlAttribute(namespace = "http://schema.omg.org/spec/XMI/2.1")
  public String idref;
}