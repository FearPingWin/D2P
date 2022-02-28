package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Server.DataBaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import Server.DB_MySQL_const;
import Server.DataBaseHandler;
/**
 * Controller fuer Home. Eine kurze Beschreibung der Spielfunktionen.
 *@see Conrollers_function
 *@see Initializable
 */
public class Home extends Conrollers_function implements Initializable{
    /**
     * Konto-Knopf
     */
	@FXML
	public Button btnAccount;
	 /**
     * Balance zu anzeigen
     */
	@FXML
	public Label balance;
    /**
     * Spitznamen zu anzeigen
     */
	@FXML
	public Label nickname;
    /**
     * Knopf um Spielpunkte zu bekommen
     */
    @FXML
    public Button btn_getPoint;
	/**
	 * Matches-Knopf
	 */
    @FXML
    public Button btnMatches;
    /**
     * Rating-Knopf
     */
    @FXML
    public Button btnRating;
    /**
     * History-Knopf
     */
    @FXML
    public Button btnHistory;
    /**
     * aendert das aktuelle Fenster in das Kontofenster
     * @see Conrollers_function#setwindow(Button, String)
     * @param e - gedrueckt @see {@link #btnAccount}
     */
	@FXML
	public void setWindAccount(ActionEvent e) {
		setwindow(btnAccount,"Account.fxml");	
	}
    /**
     * aendert das aktuelle Fenster in das Matchesfenster
     * @see Conrollers_function#setwindow(Button, String)
     * @param e - gedrueckt @see {@link #btnMatches}
     */
	@FXML
	public void setWindMatches(ActionEvent e) {
		setwindow(btnMatches,"Matches.fxml");		
	}
    /**
     * aendert das aktuelle Fenster in das Ratingfenster
     * @see Conrollers_function#setwindow(Button, String)
     * @param e - gedrueckt @see {@link #btnRating}
     */
	@FXML
	public void setWindRating(ActionEvent e) {
		setwindow(btnRating,"Rating.fxml");	
	}
	/**
	 * aendert das aktuelle Fenster in das Historyfenster
	 * @see Conrollers_function#setwindow(Button, String)
	 * @param e - gedrueckt @see {@link #btnHistory}
	 */
	@FXML
	public void setWindHistory(ActionEvent e) {
		setwindow(btnHistory,"History.fxml");
	}
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
    /**Initialisieren der Daten des Fensters
	 * @see DataBaseHandler
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//setUsername("aa");//test
		//setPoint(0);//test
		DataBaseHandler handler= new DataBaseHandler();
		try {
			setPoint(handler.getScore(getUsername()));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.closeConnection();//17.08
		nickname.setText(getUsername());
		balance.setText(Integer.toString(getPoint()));	
	}
}
