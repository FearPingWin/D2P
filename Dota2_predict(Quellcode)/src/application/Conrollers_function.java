package application;

import java.io.File;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Server.DB_MySQL_const;
import Server.DataBaseHandler;
import gui_fxml.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import gui_fxml.*;
/**
 * gemeinsame Funktionen fuer Klassencontroller
 *
 */
public class Conrollers_function {

	private static String username;
	private static int point;
	private static int matchID;
	private static String team1;
	private static String team2;
	//-------------------------------------------------------
	/**
	 * Matchnummer zu erhalten
	 * @return - Matchnummer
	 */
	public static int getMatchID() {
		return matchID;
	}
	/**
	 * Match-Nummer zu festlegen
	 * @param matchID - Match-Nummer
	 */
	public static void setMatchID(int matchID) {
		Conrollers_function.matchID = matchID;
	}
	/**
	 * Erstes Team zu erhalten
	 * @return - Erstes Team
	 */
	public static String getTeam1() {
		return team1;
	}
	/**
	 * Erstes Team zu festlegen
	 * @param team1 - Erstes Team
	 */
	public static void setTeam1(String team1) {
		Conrollers_function.team1 = team1;
	}
	/**
	 * Zweites Team zu erhalten
	 * @return - Zweites Team
	 */
	public static String getTeam2() {
		return team2;
	}
	/**
	 * Zweites Team zu festlegen
	 * @param team2 - Erstes Team
	 */
	public static void setTeam2(String team2) {
		Conrollers_function.team2 = team2;
	}
	// nick soll mit buchtabe anfangen
	/**
	 * prueft, ob ein Spitzname mit einem Buchstaben beginnt
	 * @param nick -Spitzname des Benutzers 
	 * @return - true wenn ehaltet, false wenn nicht erhaltet
	 */
	public static boolean check_nick(String nick) {
		Pattern pattern =Pattern.compile("^[a-zA-Z]");
		Matcher matcher=pattern.matcher(nick);
		if (matcher.find()) {
			return true;
		}else {
			return false;
		}
	
	}
	
	//----------------проверка ввода на сец символы----------------------
	/**
	 * Ueberpruefung der Benutzereingaben auf den Inhalt von Sonderzeichen
	 * @param unserestring - Benutzereingabe
	 * @return true wenn erhaltet, false wenn nicht erhaltet
	 */
	public static boolean find_sonder_zeichen(String unserestring) {
		if (unserestring.contains("\"")){
			return true;
		}	
		if (unserestring.contains("\\")){
			return true;
		}
		if (unserestring.contains("\'")){
			return true;
		}
		if (unserestring.contains("|'")){
			return true;
		}
		if (unserestring.contains("&'")){
			return true;
		}
		return false;
	}
	//----------------установить окно----------------------
	/**
	 * laedt ein neues Voreinstellungsfenster (fxml), wenn die Taste gedrueckt wird 
	 * @param btn - gedrueckter Knopf
	 * @param fxml - Name der fxml Datei
	 */
	public void setwindow (Button btn, String fxml) {
		try {
			Parent root = FXMLLoader.load(GUI.class.getResource(fxml));
			//Parent root = FXMLLoader.load(new File("src/gui_fxml/"+fxml).toURL());
			Stage window =(Stage)btn.getScene().getWindow();
			Scene scene = new Scene(root,1024,768);
			scene.getStylesheets().add(getClass().getResource("/mycss.css").toExternalForm());
			window.setScene(scene);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
/**
 * Benutzerspitzname zu erhalten
 * @return - Benutzerspitzname
 */
	public String getUsername() {
		return username;
	}
	/**
	 * Benutzerspitzname zu festlegen
	 * @param username - Benutzerspitzname
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	//----------------открыть новое окно----------------------
	/**
	 * laedt ein neues Fenster (fxml) fuer Vor, wenn die Taste gedrueckt wird
	 * @param btn - gedrueckter Knopf
	 * @param fxml - Name der fxml-Datei
	 */
	public void openNewWindow (Button btn, String fxml) {
		try {
			Parent root = FXMLLoader.load(GUI.class.getResource(fxml));
	        //FXMLLoader fxmlLoader = new FXMLLoader();
	        //fxmlLoader.setLocation(GUI.class.getResource("src/gui_fxml/"+fxml));
	        //System.out.println(GUI.class.getResource(fxml));
	        //fxmlLoader.setLocation(new File("src/gui_fxml/"+fxml).toURL());
	        
	        Scene scene = new Scene(root, 550, 450);
	        //Scene scene = new Scene(fxmlLoader.load(), 550, 450);
	        Stage stage = new Stage();
	        stage.setTitle("Make forcast");
			String stylesheet = getClass().getResource("/mycss.css").toExternalForm();
			scene.getStylesheets().add(stylesheet);
	        stage.getIcons().add(   new Image( getClass().getResourceAsStream( "logo.png" )));
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}	
	/**
	 * Der Benutzer erhaelt 1000 Punkte, wenn er  weniger als 1000 Spielunkte betraegt.
	 * @param label - zeigt die Spielpunkte des Benutzers an
	 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
	 * @throws SQLException -Fehler beim Zugriff auf die Datenbank
	 */
//----------------получить очки если их меньше 1000----------------------	
	public void userGetPoints (Label label) throws ClassNotFoundException, SQLException {	
		DataBaseHandler handler=new DataBaseHandler();
		int score=handler.getScore(getUsername());
		if (score<1000) {
			handler.edit_cell(DB_MySQL_const.USER_TABLE, handler.getUserIDDB(getUsername()), DB_MySQL_const.USER_SCORE, Integer.toString (score+1000));
			setPoint(score+1000);
			long dateNow = new Date(System.currentTimeMillis()).getTime();
			handler.save_predict_History(getUsername(), dateNow, 0, "Refill", 0,"+1000", score);
			label.setText(Integer.toString(getPoint()));			
		}
		handler.closeConnection();
	}
	/**
	 * Spielpunkte zu erhalten
	 * @return - Spielpunkte
	 */
	public static int getPoint() {
		return point;
	}
	/**
	 * Spielpunkte zu festlegen
	 * @param point - Spielpunkte
	 */
	public static void setPoint(int point) {
		Conrollers_function.point = point;
	}
	
	
}
