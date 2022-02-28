package application;

import java.net.URL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import Server.DB_MySQL_const;
import Server.DataBaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * Controller fuer History. Ereignisverlaufstabelle im Konto.
 *@see Conrollers_function
 *@see Initializable
 */

public class History extends Conrollers_function implements Initializable {
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
     * Konto-Knopf
     */
    @FXML
    public  Button btnAccount;
    /**
     * Spitznamen zu anzeigen
     */
    @FXML
    public  Label nickname;
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
     * Ereignisverlaufstabelle im Konto
     */
    @FXML
    public  TableView<ModelTableHistory> tableHistory;
    /**
     * Spalte zur Anzeige der Ereignisnummer
     */
    @FXML
    public  TableColumn<ModelTableHistory, Integer> colID;
    /**
     * Spalte zur Anzeige der Ereigniszeit
     */
    @FXML
    public  TableColumn<ModelTableHistory, String> colTime;
    /**
     * Spalte zur Anzeige der Spielnummer
     */
    @FXML
    public  TableColumn<ModelTableHistory, Integer> colMatchID;
    /**
     * Spalte zur Anzeige der Ereignisbeschreibung
     */
    @FXML
    public  TableColumn<ModelTableHistory, String> colDescript;
    /**
     * Spalte, um die Aenderung der Punkte nach dem Ereignis anzuzeigen
     */
    @FXML
    public  TableColumn<ModelTableHistory, String> colChange;
    /**   
    *Spalte zur Anzeige von Punkten zum Zeitpunkt des Ereignisses
    */
    @FXML
    public   TableColumn<ModelTableHistory, Integer> colBalance;
    /**
     * Spalte zur Anzeige der Anzahl der verwendeten Punkte
     */
    @FXML
    public TableColumn<ModelTableHistory, Integer> colBet;
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
     * aendert das aktuelle Fenster in das Kontofenster
     * @see Conrollers_function#setwindow(Button, String)
     * @param event - gedrueckt @see {@link #btnAccount}
     */
    @FXML
    public void setWindAccount(ActionEvent event) {
    	setwindow(btnAccount,"Account.fxml");
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
     * Tabellenanzeige
     * @see ModelTableHistory
     */
    ObservableList<ModelTableHistory> oblist=FXCollections.observableArrayList();
	@Override
	/** Initialisieren der Datentabelle
	 * @see DataBaseHandler#getConnection()
	 * @see ModelTableHistory
	 * @exception SQLException -Fehler beim Zugriff auf die Datenbank
	 * @exception ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
	 */
	public void initialize(URL arg0, ResourceBundle arg1){
		nickname.setText(getUsername());
		balance.setText(Integer.toString(getPoint()));
		Connection con = null;
		try {
			con = DataBaseHandler.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		try {			
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM "+getUsername());
			while (rs.next()==true) {
				// перевод миллисек в дату
				long timebase=Long.valueOf(rs.getString(DB_MySQL_const.TIME_HISTORY)).longValue();
				SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(timebase);
				
				oblist.add(new ModelTableHistory(
							rs.getInt(DB_MySQL_const.ID_HISTORY), 
							time.format(calendar.getTime()),
							rs.getInt(DB_MySQL_const.MATCHID_HISTORY), 
							rs.getString(DB_MySQL_const.EVENT_HISTORY), 
							rs.getInt(DB_MySQL_const.BALANCE_HISTORY), 
							rs.getInt(DB_MySQL_const.BET_HISTORY), 
							rs.getString(DB_MySQL_const.DELTA_HISTORY)
							));		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		colID.setCellValueFactory(new PropertyValueFactory<ModelTableHistory, Integer>("number"));
		colTime.setCellValueFactory(new PropertyValueFactory<ModelTableHistory, String>("time"));
		colMatchID.setCellValueFactory(new PropertyValueFactory<ModelTableHistory, Integer>("match"));
		colDescript.setCellValueFactory(new PropertyValueFactory<ModelTableHistory, String>("description"));
		colBalance.setCellValueFactory(new PropertyValueFactory<ModelTableHistory, Integer>("balance"));
		colChange.setCellValueFactory(new PropertyValueFactory<ModelTableHistory, String>("change"));
		colBet.setCellValueFactory(new PropertyValueFactory<ModelTableHistory, Integer>("balance"));
		colBet.setCellValueFactory(new PropertyValueFactory<ModelTableHistory, Integer>("bet"));
		tableHistory.setItems(oblist);
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
