package net.vjdv.xmiviewer.modelos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jassiel
 */
public class Extension {
  @XmlAttribute
  public String extender = "Jassiel";
  @XmlElement(name="Info")
  public Informacion info;
  @XmlElement(name="Feature")
  public List<Feature> caracteristicas = new ArrayList<>();
  public static class Informacion{
    @XmlAttribute
    public String tipo,combinableCon;
    @XmlAttribute
    public Boolean deDominio;
  }
  public Extension clonar(){
    Extension e = new Extension();
    e.extender = extender;
    if(info!=null){
      e.info = new Informacion();
      e.info.tipo = info.tipo;
      e.info.combinableCon = info.combinableCon;
      e.info.deDominio = info.deDominio;
    }
    caracteristicas.stream().forEach((f) -> {
      e.caracteristicas.add(f);
    });
    return e;
  }
}