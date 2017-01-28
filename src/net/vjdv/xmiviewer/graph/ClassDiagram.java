package net.vjdv.xmiviewer.graph;

import java.io.StringWriter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import net.vjdv.xmiviewer.modelos.Diagram;
import net.vjdv.xmiviewer.modelos.DiagramElement;
import net.vjdv.xmiviewer.modelos.OwnedEnd;
import net.vjdv.xmiviewer.modelos.OwnedMember;
import net.vjdv.xmiviewer.modelos.XMI;

/**
 * Dibuja un diagrama de clases
 * @author Jassiel
 */
public class ClassDiagram implements DiagramI{
  private final XMI xmi;
  private final Diagram diagram;
  private final Pane container;
  public ClassDiagram(XMI xmi, Diagram d, Pane c){
    this.xmi = xmi;
    diagram = d;
    container = c;
    diagram.elementos_list.forEach((element)->{
      switch(element.preferredShapeType){
        case "Class":
          Clase clase = new Clase(element);
          break;
        case "Association":
          Asociacion assoc = new Asociacion(element);
          break;
        case "Generalization":
          Generalizacion gen = new Generalizacion(element);
          break;
        case "Realization":
          Realizacion real = new Realizacion(element);
          break;
      }
    });
  }
  private String fixCR(String color){
    color = color.replaceAll("Cr:", "");
    String parts[] = color.split(",");
    return "rgb("+parts[0]+","+parts[1]+","+parts[2]+")";
  }
  private Color cr2color(String color){
    color = color.replaceAll("Cr:", "");
    String parts[] = color.split(",");
    double r = Double.parseDouble(parts[0]);
    double g = Double.parseDouble(parts[1]);
    double b = Double.parseDouble(parts[2]);
    double opacity = 1d/255*Double.parseDouble(parts[3]);
    return new Color(r, g, b, opacity);
  }
  /**
   * Dibuja una clase
   */
  public class Clase extends VBox{
    double geo[] = new double[4];
    public Clase(DiagramElement element){
      geo = element.parseGeometry();
      setTranslateX(geo[0]);
      setTranslateY(geo[1]);
      setPrefWidth(geo[2]);
      setPrefHeight(geo[3]);
      setSpacing(1d);
      String style = "";
      if(element.fill.color1.startsWith("Cr:")) style += "-fx-background-color:"+fixCR(element.fill.color1)+";";
      style += "-fx-border-color:"+fixCR(element.line.color)+";";
      style += "-fx-border-width:"+element.line.weight+";";
      setStyle(style);
      StringWriter styleFont = new StringWriter();
      styleFont.write("-fx-font-family:\""+element.font.name+"\";");
      styleFont.write("-fx-font-size:"+element.font.size+"px;");
      styleFont.write("-fx-text-fill:"+fixCR(element.font.color)+";");
      if(element.font.italic) styleFont.write("-fx-font-style: italic;");
      if(element.font.bold) styleFont.write("-fx-font-weight: bold;");
      OwnedMember classMember = xmi.getOwnedmemberByID(element.subject);
      //Título
      Label titulo = new Label(classMember.name);
      titulo.setStyle(styleFont.toString()+"-fx-font-weight: bold;");
      HBox head = new HBox(titulo);
      head.setAlignment(Pos.TOP_CENTER);
      getChildren().add(head);
      //Atributos
      if(!classMember.lista_atributos.isEmpty()) addLine();
      classMember.lista_atributos.forEach((attribute)->{
        Label lb = new Label(getVisibilityChar(attribute.visibility)+attribute.name);
        lb.setStyle(styleFont.toString());
        getChildren().add(lb);
      });
      //Métodos
      if(!classMember.lista_metodos.isEmpty()) addLine();
      classMember.lista_metodos.forEach((method)->{
        Label lb = new Label(getVisibilityChar(method.visibility)+method.name+"()");
        lb.setStyle(styleFont.toString());
        getChildren().add(lb);
      });
      //Por si no hubo ni atributos ni métodos
      if(classMember.lista_atributos.isEmpty() && classMember.lista_metodos.isEmpty()) addLine();
      //Agrega
      container.getChildren().add(Clase.this);
    }
    private void addLine(){
      Line line = new Line();
      line.setEndX(geo[2]);
      line.setEndY(0);
      getChildren().add(line);
    }
    private String getVisibilityChar(String visibility){
      switch(visibility){
        case "public":
          return "+";
        case "private":
          return "-";
        case "protected":
          return "#";
        default:
          return "~";
      }
    }
  }
  public class Asociacion {
    double geo[] = new double[4];
    double stroke_width = 1d;
    Line linea;
    Line lineas[];
    public Asociacion(DiagramElement element){
      String points[] = element.geometry.split(";");
      geo = new double[points.length*2];
      for(int i=0; i<points.length; i++){
        String point = points[i];
        String xy[] = point.split(",");
        geo[i*2] = Double.parseDouble(xy[0]);
        geo[i*2+1] = Double.parseDouble(xy[1]);
      }
      lineas = new Line[geo.length/2-1];
      stroke_width = Double.parseDouble(element.line.weight);
      Color color = cr2color(element.line.color);
      for(int i=0; i<lineas.length; i++){
        Line l = new Line(geo[i*2]+0.5,geo[i*2+1]+0.5,geo[i*2+2]+0.5,geo[i*2+3]+0.5);
        l.setStrokeWidth(stroke_width);
        l.setStroke(color);
        lineas[i] = l;
        container.getChildren().add(l);
      }
      //Agrega diamante para composicion o agregación
      OwnedMember member = xmi.getOwnedmemberByID(element.subject);
      if(member!=null && member.ownedEnd_list.size()==2){
        OwnedEnd end1 = member.ownedEnd_list.get(0);
        OwnedEnd end2 = member.ownedEnd_list.get(1);
        if(!end1.aggregation.equals("none") || !end2.aggregation.equals("none")){
          Polygon diamante = new Polygon();
          double px1 = geo[0], py1 = geo[1];
          diamante.getPoints().addAll(px1, py1);
          double px2, py2, px3, py3, px4, py4;
          double atan = Math.toDegrees(Math.atan2((geo[3] - geo[1]), (geo[2] - geo[0])));
//          System.out.println("ATAN:"+atan);
          double angulo = 26;
          double hip = 14.577379737;
          double rev1 = Math.toRadians(atan - angulo);
          double rev2 = Math.toRadians(atan);
          double rev3 = Math.toRadians(atan + angulo);
          px2 = px1 + Math.cos(rev1) * hip;
          py2 = py1 + Math.sin(rev1) * hip;
          px3 = px1 + Math.cos(rev2) * 25;
          py3 = py1 + Math.sin(rev2) * 25;
          px4 = px1 + Math.cos(rev3) * hip;
          py4 = py1 + Math.sin(rev3) * hip;
          diamante.getPoints().addAll(px2, py2, px3, py3, px4, py4);
          container.getChildren().add(diamante);
          if(end1.aggregation.equals("shared") || end2.aggregation.equals("shared")){
            diamante.setFill(Color.WHITE);
            diamante.setStroke(Color.BLACK);
          }
        }
      }
    }
  }
  public class Generalizacion extends Asociacion{
    Polygon poligono;
    public Generalizacion(DiagramElement element){
      super(element);
      poligono = new Polygon();
      double px1 = geo[0], py1 = geo[1];
      poligono.getPoints().addAll(px1,py1);
      double px2, py2, px3, py3;
//      if(geo[2]<px1 && geo[1]==geo[3]){
//        px2 = px1-15;
//        py2 = py1-7.5;
//        px3 = px1-15;
//        py3 = py1+7.5;
//      }else if(geo[2]>px1 && geo[1]==geo[3]){
//        px2 = px1+15;
//        py2 = py1-7.5;
//        px3 = px1+15;
//        py3 = py1+7.5;
//      }else if(geo[2]==px1 && geo[1]>geo[3]){
//        px2 = px1-7.5;
//        py2 = py1-15;
//        px3 = px1+7.5;
//        py3 = py1-15;
//      }else if(geo[2]==px1 && geo[1]<geo[3]){
//        px2 = px1-7.5;
//        py2 = py1+15;
//        px3 = px1+7.5;
//        py3 = py1+15;
//      }else{
        double atan = Math.toDegrees(Math.atan2((geo[3]-geo[1]),(geo[2]-geo[0])));
//        System.out.println("ATAN:"+atan);
        double angulo = 26;
        double hip = 16.77;
        double rev1 = Math.toRadians(atan-angulo);
        double rev2 = Math.toRadians(atan+angulo);
        px2 = px1+Math.cos(rev1)*hip;
        py2 = py1+Math.sin(rev1)*hip;
        px3 = px1+Math.cos(rev2)*hip;
        py3 = py1+Math.sin(rev2)*hip;
      //}
      poligono.getPoints().addAll(px2,py2,px3,py3);
      poligono.setFill(Color.WHITE);
      poligono.setStrokeWidth(1d);
      poligono.setStroke(Color.BLACK);
      container.getChildren().add(poligono);
    }
  }
  public class Realizacion extends Generalizacion{
    public Realizacion(DiagramElement element){
      super(element);
      poligono.setFill(Color.CYAN);
      for(Line l : lineas) l.getStrokeDashArray().add(5d);
    }
  }
}