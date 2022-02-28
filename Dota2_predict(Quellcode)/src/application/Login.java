package application;

import java.io.IOException;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * Controller fuer Login. Anmeldefenster.
 *@see Conrollers_function
 *@see Initializable
 */
public class Login extends Conrollers_function{
	/**
	 * Login-Knopf
	 */
	@FXML
	public Button butt_login;
	/**
	 * Anmeldung-Knopf
	 */
	@FXML
	public Button btn_registr;
	/**
	 * Passwortfeld
	 */
	@FXML
	public PasswordField field_pass;
	/**
	 * Spitznamefeld
	 */
	@FXML
	public TextField field_username;
	/**
	 * zeigt Nachrichten
	 */
	@FXML
	public Label lb_message_login;
	/**Versuch anzumelden.
	 * Bei dem Erfolg wird ein neues Fenster geoefnet, sonst die Meldung
	 * @see Conrollers_function#find_sonder_zeichen(String)
	 * @see Client#sendRequestLogin(String, String)
	 * @param e - @see {@link #butt_login}
	 */
	@FXML
	public void log_in(ActionEvent e) {
		String login_text=field_username.getText().trim();

		String login_pass=field_pass.getText().trim();
		if (find_sonder_zeichen(login_pass)==true|| find_sonder_zeichen(login_text)==true) {
			lb_message_login.setText("Invalid characters \\ \' \" ");
		}else {			
			if (!login_text.equals("") && !login_pass.equals("")) {
				//отправить запрос на сервер
				Client client= new Client();
				try {
					setUsername(login_text);
					String ant =client.sendRequestLogin(login_text, login_pass);
					lb_message_login.setText(ant);
					if (ant.equals("Access")) {						
						setwindow(btn_registr,"Home.fxml");	
					}	
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else {
				lb_message_login.setText("Login or pass is empty");				
			}
		}
	}
    /**
     * aendert das aktuelle Fenster in das Regestrierungsfenster
     * @see Conrollers_function#setwindow(Button, String)
     * @param e - gedrueckt @see {@link #btn_registr}
     */
	@FXML
	public void wind_registr(ActionEvent e) {
		setwindow(btn_registr,"singUp.fxml");
	}

}
