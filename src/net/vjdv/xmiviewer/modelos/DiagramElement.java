package net.vjdv.xmiviewer.modelos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jassiel
 */
public class DiagramElement {
  @XmlAttribute
  public String geometry,preferredShapeType,subject,fromDiagramElement,toDiagramElement;
  @XmlAttribute(namespace="http://schema.omg.org/spec/XMI/2.1")
  public String id;
  @XmlElement(name="elementFill")
  public Fill fill = new Fill();
  @XmlElement(name="elementFont")
  public Font font = new Font();
  @XmlElement(name="elementLine")
  public Line line = new Line();
  public static class Fill{
    @XmlAttribute
    public String color1="Cr:255,255,255,255", color2="", style="1", transparency="0", type="1";
  }
  public static class Font{
    @XmlAttribute
    public String color="Cr:0,0,0,255", name="Consolas", size="14", style="0";
    @XmlAttribute
    public Boolean bold=false, italic=false;
  }
  public static class Line{
    @XmlAttribute
    public String color="Cr:0,0,0,255", style="1", transparency="0", weight="1.0";
  }
  /**
   * Clona el elemento actual, crea un nuevo elemento con los mismos atributos
   * @return nueva instancia
   */
  public DiagramElement getClone(){
    DiagramElement d = new DiagramElement();
    d.geometry = geometry;
    d.subject = subject;
    d.preferredShapeType = preferredShapeType;
    d.fromDiagramElement = fromDiagramElement;
    d.toDiagramElement = toDiagramElement;
    d.id = id;
    d.fill.color1 = fill.color1;
    d.fill.color2 = fill.color2;
    d.fill.style = fill.style;
    d.fill.transparency = fill.transparency;
    d.fill.type = fill.type;
    d.font.bold = font.bold;
    d.font.color = font.color;
    d.font.italic = font.italic;
    d.font.name = font.name;
    d.font.size = font.size;
    d.font.style = font.style;
    d.line.color = line.color;
    d.line.style = line.style;
    d.line.transparency = line.transparency;
    d.line.weight = line.weight;
    return d;
  }
  /**
   * Establece un ID aleatorio para este elemento
   */
  public void setRandomID(){
    id = Utils.getRandomID();
  }
  public double[] parseGeometry(){
    return parseGeometry(geometry);
  }
  public double[] parseGeometry(String geo_param){
    double geo[] = new double[4];
    String geo_array[];
    if(geo_param.contains(";")){
      String p[] = geo_param.split(";");
      String p1[] = p[0].split(",");
      String p2[] = p[1].split(",");
      geo_array = new String[]{p1[0],p1[1],p2[0],p2[1]};
    }else{
      geo_array = geo_param.split(",");
    }
    geo[0] = Double.parseDouble(geo_array[0]);
    geo[1] = Double.parseDouble(geo_array[1]);
    geo[2] = Double.parseDouble(geo_array[2]);
    geo[3] = Double.parseDouble(geo_array[3]);
    return geo;
  }
}