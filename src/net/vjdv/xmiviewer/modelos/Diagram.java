package net.vjdv.xmiviewer.modelos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.geometry.Rectangle2D;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *
 * @author Jassiel
 */
public class Diagram{
  @XmlAttribute
  public String diagramType,documentation,name,toolName = "XMI Viewer";
  @XmlAttribute(namespace="http://schema.omg.org/spec/XMI/2.1")
  public String id;
  @XmlElement(name="Extension",namespace="http://schema.omg.org/spec/XMI/2.1")
  public List<Extension> ext_list = new ArrayList<>();
  @XmlElementWrapper(name="Diagram.element",namespace="http://schema.omg.org/spec/UML/2.0")
  @XmlElement(name="DiagramElement",namespace="http://schema.omg.org/spec/UML/2.0")
  public List<DiagramElement> elementos_list = new ArrayList<>();
  @Override
  public String toString(){
    return name+" ("+id+")";
  }
  public Diagram clonar() {
    Diagram d = new Diagram();
    d.diagramType = diagramType;
    d.documentation = documentation;
    d.name = name;
    d.toolName = toolName;
    d.id = id;
    ext_list.stream().forEach((ext) ->  d.ext_list.add(ext.clonar()));
    elementos_list.stream().forEach((dm) -> d.elementos_list.add(dm.getClone()));
    return d;
  }
  public void crearIdAleatorio(){
    Random r = new Random();
    id = "diagram"+r.nextInt(9999999);
  }
  public boolean existsSubject(String subject){
    boolean encontrado = false;
    for(DiagramElement element : elementos_list) encontrado = encontrado || element.subject.equals(subject);
    return encontrado;
  }
  public DiagramElement getSubject(String subject){
    for(DiagramElement element : elementos_list) if(element.subject.equals(subject)) return element;
    return null;
  }
  public void normalizarReferencias(){
    Map<String,String> map = new HashMap<>();
    elementos_list.forEach((dm) -> map.put(dm.id, dm.subject));
    elementos_list.forEach((dm)->{
      if(dm.fromDiagramElement!=null) dm.fromDiagramElement = map.get(dm.fromDiagramElement);
      if(dm.toDiagramElement!=null) dm.toDiagramElement = map.get(dm.toDiagramElement);
      dm.id = dm.subject;
    });
  }
  private void seekSpaceFor(double geo[]){
    seekSpaceFor(new Geo(geo));
  }
  private double start_x = 10, start_y = 10;
  private Geo seekSpaceFor(Geo geo){
    double x_max = 800-geo.w;
    for(double x=start_x, y=start_y; y<=2000; x+=10){
      boolean choca = false;
      for(DiagramElement element : elementos_list){
        if(!element.preferredShapeType.equals("Class")) continue;
        if(x>=x_max){
          x = 10;
          y += 20;
          continue;
        }
        Geo g = new Geo(element.parseGeometry());
        Rectangle2D rect1 = new Rectangle2D(x,y,geo.w,geo.h);
        Rectangle2D rect2 = new Rectangle2D(g.x-29d,g.y-29d,g.w+59d,g.h+59d);
        if(rect1.intersects(rect2) || rect2.intersects(rect1)) choca = choca || true;
      }
      if(!choca){
        geo.x = x;
        geo.y = y;
        start_x = x+geo.w+29;
        start_y = y;
        return geo;
      }
    }
    return null;
  }
//  private Geo seekSpaceFor(Geo geo){
//    double x_max = 750-geo.w;
//    double cx1 = geo.x+geo.w/2;
//    double cy1 = geo.y+geo.h/2;
//    for(double x=10, y=10; y<=2000; x+=10){
//      boolean choca = false;
//      for(DiagramElement element : elementos_list){
//        if(!element.preferredShapeType.equals("Class")) continue;
//        GeoL g = new GeoL(element.parseGeometry());
//        g.x1 -= 29;
//        g.y1 -= 29;
//        g.x2 = g.x1+g.x2+29;
//        g.y2 = g.y1+g.y2+29;
//        double cx2 = g.x1+(g.x2-g.x1)/2;
//        double cy2 = g.y1+(g.y2-g.y1)/2;
//        double dw = (geo.w+(g.x2-g.x1))/2;
//        double dh = (geo.h+(g.y2-g.y1))/2;
//        double dx = cx1 - cx2;
//        double dy = cy1 - cy2;
////        //X no está dentro del cuadrado g
////        boolean b1 = x>=g.x1 && x<=g.x2;
////        //Y no está dentro del cuadrado g
////        boolean b2 = y>=g.y1 && y<=g.y2;
////        //X+ancho no está dentro del cuadrado g
////        boolean b3 = x+geo.w>=g.x1 && x+geo.w<=g.x2;
////        //Y+algo no está dentro del cuadrado g
////        boolean b4 = y+geo.h>=g.y1 && y+geo.h<=g.y2;
////        //X no está dentro del cuadrado g
////        boolean b5 = g.x1>=x && g.x2<=x;
////        //Y no está dentro del cuadrado g
////        boolean b6 = g.y1>=y && g.y2<=y;
////        //X+ancho no está dentro del cuadrado g
////        boolean b7 = g.x1>=x+geo.w && g.x2<=x+geo.w;
////        //Y+algo no está dentro del cuadrado g
////        boolean b8 = g.y1>=y+geo.h && g.y2<=y+geo.h;
////        //IF
////        if(((b1 && b2) || (b3 && b4)) && ((b5 && b6) || (b7 && b8))) choca = choca || true;
//        if(Math.abs(dx)<=dw && Math.abs(dy)<=dh) choca = choca || true;
//      }
//      if(!choca){
//        geo.x = x;
//        geo.y = y;
//        start_x = x+geo.w+29;
//        start_y = y;
//        return geo;
//      }
//      if(x>=x_max){
//        x = 10;
//        y += 20;
//      }
//    }
//    return null;
//  }
  public void combineWith(Diagram diagram){
    combineWith(diagram, false);
  }
  public void combineWith(Diagram original_diagram, boolean ordenar){
    Diagram d = original_diagram.clonar();
    normalizarReferencias();
    d.normalizarReferencias();
//    d.elementos_list.stream().filter((element) -> (!existsSubject(element.subject))).forEach((element)->{
//      elementos_list.add(element);
//    });
    List<DiagramElement> flechas = new ArrayList<>();
    d.elementos_list.stream().filter((element) -> (!existsSubject(element.subject))).forEach(element -> {
      if(ordenar){
        if(element.preferredShapeType.equals("Class")){
          Geo geo = new Geo(element.parseGeometry());
          geo = seekSpaceFor(geo);
          element.geometry = geo.toString();
        }
        if(element.preferredShapeType.equals("Association") || element.preferredShapeType.equals("Generalization") || element.preferredShapeType.equals("Realization")){
          flechas.add(element);
        }
      }
      elementos_list.add(element);
    });
    if(ordenar) flechas.forEach((flecha) -> {
      DiagramElement desde = getSubject(flecha.fromDiagramElement);
      DiagramElement hasta = getSubject(flecha.toDiagramElement);
      GeoL geo = new GeoL(flecha.parseGeometry());
      Geo geo1 = new Geo(desde.parseGeometry());
      Geo geo2 = new Geo(hasta.parseGeometry());
      if(geo1.x+geo1.w<geo2.x){
        geo.x1 = geo1.x+geo1.w;
        geo.x2 = geo2.x;
      }else if(geo2.x+geo2.w<geo1.x){
        geo.x1 = geo1.x;
        geo.x2 = geo2.x+geo2.w;
      }else if(geo1.x+Math.round(geo1.w/2)<geo2.x){
        geo.x1 = geo1.x+Math.round(geo2.w/2);
        geo.x2 = geo2.x+Math.round(geo2.w/2);
      }else if(geo1.x+Math.round(geo1.w/2)>geo2.x){
        geo.x1 = geo1.x+Math.round(geo1.w/2);
        geo.x2 = geo2.x+Math.round(geo1.w/2);
      }
      if(geo1.y+geo1.h<geo2.y){
        geo.y1 = geo1.y+geo1.h;
        geo.y2 = geo2.y;
      }else if(geo2.y+geo2.h<geo1.y){
        geo.y1 = geo1.y;
        geo.y2 = geo2.y+geo2.h;
      }else if(geo1.y+Math.round(geo1.h/2)<geo2.y){
        geo.y1 = geo1.y+Math.round(geo2.h/2);
        geo.y2 = geo2.y+Math.round(geo2.h/2);
      }else if(geo1.y+Math.round(geo1.h/2)>geo2.y){
        geo.y1 = geo1.y+Math.round(geo1.h/2);
        geo.y2 = geo2.y+Math.round(geo1.h/2);
      }
      flecha.geometry = geo.toString();
    });
    elementos_list.forEach((element)->{
      String old_id = element.id;
      element.setRandomID();
      elementos_list.forEach((targetElement)->{
        if(!element.id.equals(targetElement.id)){
          if(targetElement.fromDiagramElement!=null) if(targetElement.fromDiagramElement.equals(old_id)) targetElement.fromDiagramElement = element.id;
          if(targetElement.toDiagramElement!=null) if(targetElement.toDiagramElement.equals(old_id)) targetElement.toDiagramElement = element.id;
        }
      });
    });
  }
}