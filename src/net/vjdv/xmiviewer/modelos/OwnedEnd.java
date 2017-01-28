package net.vjdv.xmiviewer.modelos;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Jassiel
 */
public class OwnedEnd {
  @XmlAttribute
  public String aggregation, association, type;
  @XmlAttribute
  public Boolean isDerived, isDerivedUnion, isLeaf, isNavigable, isReadOnly, isStatic;
  @XmlAttribute(namespace="http://schema.omg.org/spec/XMI/2.1")
  public String id;
  @XmlAttribute(name="type",namespace="http://schema.omg.org/spec/XMI/2.1")
  public String xmi_type;
}