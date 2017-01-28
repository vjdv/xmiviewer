package net.vjdv.xmiviewer.modelos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jassiel
 */
public class Model {
  @XmlAttribute
  public String name = "untitled";
  @XmlAttribute(namespace="http://schema.omg.org/spec/XMI/2.1")
  public String id = "aasdsdf";
  @XmlElement(name="ownedMember")
  public List<OwnedMember> lista_members = new ArrayList<>();
}