package net.vjdv.xmiviewer.modelos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jassiel
 */
public class OwnedOperation {
  @XmlAttribute
  public boolean isAbstract,isLead,isOrdered,isQuery,isStatic,isUnique;
  @XmlAttribute
  public String name,visibility;
  @XmlAttribute(namespace = "http://schema.omg.org/spec/XMI/2.1")
  public String id,type;
  @XmlElement(name="ownedParameter")
  public List<OwnedParameter> parameters = new ArrayList<>();
  public static class OwnedParameter{
    @XmlAttribute
    String kind;
    @XmlAttribute(namespace = "http://schema.omg.org/spec/XMI/2.1")
    String id,type;
  }
}
