@XmlSchema(
  namespace = "",
  elementFormDefault = XmlNsForm.QUALIFIED,
  xmlns = {
    @XmlNs(namespaceURI = "http://schema.omg.org/spec/UML/2.0", prefix = "uml"),
    @XmlNs(namespaceURI = "http://schema.omg.org/spec/XMI/2.1", prefix = "xmi")
  }
)
package net.vjdv.xmiviewer.modelos;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;