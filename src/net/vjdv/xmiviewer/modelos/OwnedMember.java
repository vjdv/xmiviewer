package net.vjdv.xmiviewer.modelos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jassiel
 */
public class OwnedMember {
  //De cajón
  @XmlAttribute
  public String name;
  @XmlAttribute(namespace = "http://schema.omg.org/spec/XMI/2.1")
  public String id, type;
  //Otros
  @XmlAttribute
  public Boolean isAbstract,isLeaf,isActive;
  @XmlAttribute
  public String visibility;
  //Composición
  @XmlElement(name="ownedMember")
  public List<OwnedMember> lista_miembros = new ArrayList<>();
  //Para atributos
  @XmlElement(name="ownedAttribute")
  public List<OwnedAttribute> lista_atributos = new ArrayList<>();
  //Para métodos
  @XmlElement(name="ownedOperation")
  public List<OwnedOperation> lista_metodos = new ArrayList<>();
  //Para realización
  @XmlAttribute
  public String client, realizingClassifier, supplier;
  //Para agregaciones y realizaciones
  @XmlElement(name="memberEnd")
  public List<MemberEnd> memberEnd_list = new ArrayList<>();
  @XmlElement(name="ownedEnd")
  public List<OwnedEnd> ownedEnd_list = new ArrayList<>();
  /**
   * Retrive
   * @param id
   * @return 
   */
  public OwnedMember getOwnedMemberByID(String id){
    
    if(this.id.equals(id)) return this;
    for(OwnedMember m : lista_miembros){
      OwnedMember om = m.getOwnedMemberByID(id);
      if(om!=null) return om;
    }
    return null;
  }
}