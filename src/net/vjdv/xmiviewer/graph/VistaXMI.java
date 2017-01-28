package net.vjdv.xmiviewer.graph;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import net.vjdv.xmiviewer.modelos.Diagram;
import net.vjdv.xmiviewer.modelos.XMI;

/**
 *
 * @author Jassiel
 */
public class VistaXMI extends Pane{
  private XMI xmi;
  private Diagram diagram;
  public VistaXMI(){
    setPadding(new Insets(5));
  }
  public void setModels(XMI xmi, Diagram d){
    this.xmi = xmi;
    diagram = d;
  }
  public void draw(){
    DiagramI diagrama;
    switch(diagram.diagramType){
      case "ClassDiagram":
        diagrama = new ClassDiagram(xmi, diagram, this);
        break;
      default:
        throw new UnsupportedOperationException("No se soporta este tipo de diagrama");
    }
  }
  public void setAndDraw(XMI xmi, Diagram d){
    clear();
    setModels(xmi, d);
    draw();
  }
  public void clear(){
    getChildren().clear();
  }
}