package net.vjdv.xmiviewer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import net.vjdv.xmiviewer.graph.VistaXMI;
import net.vjdv.xmiviewer.modelos.Diagram;
import net.vjdv.xmiviewer.modelos.XMI;

/**
 *
 * @author Jassiel
 */
public class VentanaController implements Initializable {
  //Variables
  private final FileChooser xmiChooser = new FileChooser();
  private final VistaXMI view_xmi = new VistaXMI();
  private File file_model;
  private XMI xmi_model;
  
  @FXML
  private ScrollPane view_scroll;
  
  @FXML
  private void abrir(ActionEvent event) {
    File f = xmiChooser.showOpenDialog(null);
    if(f==null) return;
    XMI xmi = TraductorXMI.file2modelo(f);
    if(xmi!=null){
      file_model = f;
      xmi_model = xmi;
      Diagram d1 = xmi.diagram_list.get(0);
      view_xmi.setModels(xmi, d1);
      view_xmi.draw();
    }
  }
  @FXML
  private void guardar(ActionEvent event){
    if(file_model==null) guardarComo(null);
    else guardar();
  }
  @FXML
  private void guardarComo(ActionEvent event){
    File f = xmiChooser.showSaveDialog(null);
    if(f==null) return;
    file_model = f;
    guardar();
  }
  /**
   * Guarda el modelo en archivo
   */
  private void guardar(){
    boolean exito = TraductorXMI.modelo2file(xmi_model, file_model);
    if(exito==false){
      JOptionPane.showMessageDialog(null, "Error al guardar");
      file_model = null;
    }
  }
  @FXML
  private void test(){
    File f = new File("C:\\Users\\Jassiel\\Desktop\\domo.xmi");
    XMI xmi = TraductorXMI.file2modelo(f);
    Diagram d1 = xmi.diagram_list.get(0);
    Diagram d2 = xmi.diagram_list.get(1);
    Diagram d3 = xmi.diagram_list.get(2);
    Diagram d4 = xmi.diagram_list.get(4);
    Diagram d5 = xmi.diagram_list.get(5);
    Diagram d6 = xmi.diagram_list.get(6);
    Diagram d7 = xmi.diagram_list.get(7);
    Diagram d8 = xmi.diagram_list.get(8);
    Diagram d9 = xmi.combinar(d1, true, d2, d3, d4, d5, d6, d7, d8);
//    System.out.println(xmi.diagram_list.indexOf(d3));
    view_xmi.setModels(xmi, d9);
    view_xmi.draw();
    xmi_model = xmi;
//    File f2 = new File("C:\\Users\\Jassiel\\Desktop\\juju.xmi");
//    boolean exito = TraductorXMI.xmi2file(xmi, f2);
//    System.out.println(exito);
  }
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    view_scroll.setContent(view_xmi);
  }
}