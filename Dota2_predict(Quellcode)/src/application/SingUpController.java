package application;

import java.io.IOException;
import java.net.UnknownHostException;

import Server.DataBaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * Controller fuer Registrierung.
 *@see Conrollers_function
 *@see Initializable
 */
public class SingUpController extends Conrollers_function {
/**
 * zeigt Nachrichten	
 */
@FXML
public Label lb_singup_message;
/**
 * das Spitzname-Eingabefeld
 */
@FXML
public TextField field_nick;
/**
 * das Vorname-Eingabefeld
 */
@FXML 
public TextField field_first_name;
/**
 * das Name-Eingabefeld
 */
@FXML 
public TextField field_last_name;
/**
 * das e-Mail-Eingabefeld
 */
@FXML 
public TextField field_email;
/**
 * das Passwort-Eingabefeld
 */
@FXML 
public PasswordField field_pass;
/**
 * Der Registrierungsknopf 
 */
@FXML
public Button btn_singup;
/**
 * Der Loginknopf 
 */
@FXML
public Button  btn_wind_login;
/**Senden der Registrierungsdaten an den Server
 * *@see Conrollers_function#find_sonder_zeichen(String)
 * @param e - gedruekt @see {@link #btn_singup}
 */
@FXML
public void mysql_registr (ActionEvent e) {
	String singup_nick=field_nick.getText().trim();
	String singup_pass=field_pass.getText().trim();
	String singup_first_name=field_first_name.getText().trim();
	String singup_last_name=field_last_name.getText().trim();
	String singup_email =field_email.getText().trim();
	if (find_sonder_zeichen(singup_nick)==true || find_sonder_zeichen(singup_pass)==true||find_sonder_zeichen(singup_first_name)==true
			||find_sonder_zeichen(singup_last_name)==true ||find_sonder_zeichen(singup_email)==true
			|| check_nick(singup_nick)==false) {
		lb_singup_message.setText("Invalid characters \\ \' \" ");
	}else {
		if (!singup_nick.equals("") && !singup_pass.equals("") && !singup_first_name.equals("")
				 && !singup_last_name.equals("") && !singup_email.equals("")) {
			Client client= new Client();
			try {
				String antwort=client.sendRequestSingup(field_nick.getText(), field_pass.getText(), field_first_name.getText(),
							field_last_name.getText(),field_email.getText());
				if (antwort.equals("Registration completed successfully")) {
					lb_singup_message.setText("Registration completed successfully");						
				}else lb_singup_message.setText("Nickname or e-Mail is already taken");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}else lb_singup_message.setText("Fields should not be empty");
	}


}
/**
 * aendert das aktuelle Fenster in das Loginfenster
 * @see Conrollers_function#setwindow(Button, String)
 * @param e - gedrueckt @see {@link #btn_wind_login}
 */
@FXML
public void to_wind_login (ActionEvent e) {
	setwindow(btn_wind_login,"Login.fxml");
}


}