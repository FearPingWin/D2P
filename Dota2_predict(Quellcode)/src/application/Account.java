package application;

import java.net.URL;
/**
 * Controller-Klasse fuer Account.fxml
 * Benutzereinstellungen zum aendern von Passwort und e-Mail.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.print.attribute.standard.MediaSize.Other;

import Server.DB_MySQL_const;
import Server.DataBaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/**
 * Controller fuer Account. Benutzereinstellungen: Name und Passwort
 *@see Conrollers_function
 *@see Initializable
 */
public class Account extends Conrollers_function implements Initializable{
	/**
	 * Matches-Knopf
	 */
    @FXML
    public  Button btnMatches;
    /**
     * Rating-Knopf
     */
    @FXML
    public  Button btnRating;
    /**
     * History-Knopf
     */
    @FXML
    public  Button btnHistory;
    /**
     * Spitznamen zu anzeigen
     */
    @FXML
    public   Label nickname;
    /**
     * Balance zu anzeigen
     */
    @FXML
    public  Label balance;
    /**
     * Knopf um Spielpunkte zu bekommen
     */
    @FXML
    public  Button btn_getPoint;
    /**
     *e-Mail Adressfeld
     */
    @FXML
    public  TextField fieldNewEmail;
    /**
     *Knopf um e-Mail Adresse zu aendern 
     */
    @FXML
    public  Button btn_NewEmail;
    /**
     *altes Passwort-Eingabefeld 
     */
    @FXML
    public  TextField fieldOldPassword;
    /**
     *neues Passwort-Eingabefeld 
     */
    @FXML
    public  TextField fieldNewPassword;
    /**
     *Knopf um Passwort zu aendern 
     */
    @FXML
    public Button btn_NewPassword;
    /**
     *Knopf um Passwort zu aendern 
     */
    @FXML
    public Label lbMessage;
/**
 * Spielpunktenauffuellung, wenn gedrueckt
 * @param event - gedrueckt @see {@link #btn_getPoint}
 * @see Conrollers_function#getPoint()
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
 */
    @FXML
    public void getPoint(ActionEvent event) throws ClassNotFoundException, SQLException {
    	userGetPoints(balance);
    }
	/**
	 * aendert das aktuelle Fenster in das Historyfenster
	 * @see Conrollers_function#setwindow(Button, String)
	 * @param event - gedrueckt @see {@link #btnHistory}
	 */
    @FXML
    public void setWindHistory(ActionEvent event) {
    	setwindow(btnHistory,"History.fxml");
    }
    /**
     * aendert das aktuelle Fenster in das Matchesfenster
     * @see Conrollers_function#setwindow(Button, String)
     * @param event - gedrueckt @see {@link #btnMatches}
     */
    @FXML
    public void setWindMatches(ActionEvent event) {
    	setwindow(btnMatches,"Matches.fxml");
    }
    /**
     * aendert das aktuelle Fenster in das Ratingfenster
     * @see Conrollers_function#setwindow(Button, String)
     * @param event - gedrueckt @see {@link #btnRating}
     */
    @FXML
    public void setWindRating(ActionEvent event) {
    	setwindow(btnRating,"Rating.fxml");
    }
/**
 * e-Mail Adresse zu aendern
 * @param event - gedrueckt @see {@link #btn_NewEmail}
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
 */
    @FXML
    public void confirmNewEmail(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String mail=fieldNewEmail.getText().trim();

		if (find_sonder_zeichen(mail)==true) {
			lbMessage.setText("Invalid characters \\ \' \" ");
		}else {	
			if (!mail.equals("") ) {
				// изменить €чейку с мэйlом		
				DataBaseHandler handler =new DataBaseHandler();
				handler.update_cell(DB_MySQL_const.USER_TABLE,DB_MySQL_const.USER_EMAIL,  DB_MySQL_const.ID_TABLEUSER,handler.getUserIDDB(getUsername()),mail);
				lbMessage.setText("E-mail address \nsuccessfully changed");
				handler.closeConnection();//17.08
			}
		}

    }
    /**
     * das Passwort zu aendern
     * @param event  - gedrueckt @see {@link #btn_NewPassword}
     * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
     * @throws SQLException - Fehler beim Zugriff auf die Datenbank
     */
    @FXML
    public void confirmNewPassword(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String oldPass=fieldOldPassword.getText().trim();
    	String newPass=fieldNewPassword.getText().trim();
    	DataBaseHandler handler =new DataBaseHandler();
    	String oldPassTable=handler.find_info_id_collum(DB_MySQL_const.USER_TABLE, DB_MySQL_const.ID_TABLEUSER, handler.getUserIDDB(getUsername()), DB_MySQL_const.USER_PASS);// вз€ть пас из таблицы
    	//System.out.println(oldPassTable);
    	if (oldPass.equals(oldPassTable)) {
    		
    		if (find_sonder_zeichen(newPass)==true) {
    			lbMessage.setText("Invalid characters \\ \' \" ");
    		}else {	
    			if (!newPass.equals("") ) {
    				handler.update_cell(DB_MySQL_const.USER_TABLE,DB_MySQL_const.USER_PASS,  DB_MySQL_const.ID_TABLEUSER,handler.getUserIDDB(getUsername()),newPass);
    				lbMessage.setText("Password \nsuccessfully changed");
    			}
    		}
    	}else {
    		lbMessage.setText("Invalid old Password");
    	}
    	handler.closeConnection();
    }
    /**
     * Initialisierung von Spitznamen und Punkten im aktuellen Fenster
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nickname.setText(getUsername());
		balance.setText(Integer.toString(getPoint()));
		
	}

}
