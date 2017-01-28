package net.vjdv.xmiviewer.modelos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jassiel
 */
@XmlRootElement(name="XMI",namespace="http://schema.omg.org/spec/XMI/2.1")
public class XMI {
  @XmlAttribute(namespace="http://schema.omg.org/spec/XMI/2.1")
  public String version = "2.1";
  @XmlElement(name="Documentation",namespace="http://schema.omg.org/spec/XMI/2.1")
  public Documentation doc = new Documentation();
  @XmlElement(name="Extension",namespace="http://schema.omg.org/spec/XMI/2.1")
  public List<Extension> ext_list = new ArrayList<>();
  @XmlElement(name="Model",namespace="http://schema.omg.org/spec/UML/2.0")
  public Model model = new Model();
  @XmlElement(name="Diagram",namespace="http://schema.omg.org/spec/UML/2.0")
  public List<Diagram> diagram_list = new ArrayList<>();
  //Métodos helpers
  public void setTitulo(String titulo){
    model.name = titulo;
  }
  public OwnedMember getOwnedmemberByID(String id){
    for(OwnedMember member : model.lista_members){
      OwnedMember om = member.getOwnedMemberByID(id);
      if(om!=null) return om;
    }
    return null;
  }
  public Diagram getDiagramByID(String id){
    for(Diagram d : diagram_list) if(d.id.equals(id)) return d;
    return null;
  }
  public Diagram combinar(Diagram d1, Diagram d2){
    return combinar(d1, d2, false);
  }
  public Diagram combinar(Diagram d1, Diagram d2, boolean ordenar){
    Diagram dnuevo = d1.clonar();
    dnuevo.combineWith(d2,ordenar);
    dnuevo.crearIdAleatorio();
    dnuevo.name = "Generado";
    diagram_list.add(dnuevo);
    return dnuevo;
  }
  public Diagram combinar(Diagram d, Diagram... diagrams){
    return combinar(d, false, diagrams);
  }
  public Diagram combinar(Diagram d, boolean ordenar, Diagram... diagrams){
    Diagram dnuevo = d.clonar();
    for(Diagram dx : diagrams) dnuevo.combineWith(dx,ordenar);
    diagram_list.add(dnuevo);
    return dnuevo;
  }
}