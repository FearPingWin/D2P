package application;
	
import javafx.application.Application;
import gui_fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**Die Hauptklasse, in der die Anwendung gestartet wird.
 * Einrichten der Einstellungen und des ersten Anwendungsfensters.
 *@see Application
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader= new FXMLLoader();
			//getClass().getResource("src/gui_fxml/Login.fxml")
			//loader.setLocation(new File("src/gui_fxml/Login.fxml").toURL());
			loader.setLocation(GUI.class.getResource("Login.fxml"));
			primaryStage.setTitle("Dota2predict"); 
			primaryStage.getIcons().add(   new Image( getClass().getResourceAsStream( "logo.png" ))); 
			AnchorPane root = loader.load();		
			Scene scene = new Scene (root);	
			String stylesheet = getClass().getResource("/mycss.css").toExternalForm();
			scene.getStylesheets().add(stylesheet);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * die Anwendung  wird gestartet
	 * @param args - default
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
