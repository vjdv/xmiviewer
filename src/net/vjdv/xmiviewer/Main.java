package net.vjdv.xmiviewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase Main
 * @author Jassiel Díaz
 */
public class Main extends Application {
  
  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("Ventana.fxml"));
    
    Scene scene = new Scene(root);
    stage.setTitle("XMI Viewer");
    stage.setScene(scene);
    stage.show();
  }
  /**
   * Inicia la aplicación
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}