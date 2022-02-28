package application;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
/**
 * Controller fuer Rating. die Ratingstabelle
 *@see Conrollers_function
 *@see Initializable
 */

public class Rating  extends Conrollers_function implements Initializable{
	/**
	 * Matches-Knopf
	 */
    @FXML
    public Button btnMatches;
    /**
     * Konto-Knopf
     */
    @FXML
    public Button btnAccount;
    /**
     * History-Knopf
     */
    @FXML
    public Button btnHistory;
    /**
     * Spitznamen zu anzeigen
     */
    @FXML
    public Label nickname;
    /**
     * Balance zu anzeigen
     */
    @FXML
    public Label balance;
    /**
     * Knopf um Spielpunkte zu bekommen
     */
    @FXML
    public Button btn_getPoint;
    /**
     * Tabelle, die das Rating widerspiegelt
     */
	@FXML
    public TableView<ModelTableRating> table_rating;
	/**
	 * Spalte zur Anzeige ordinalem Platz
	 */
	@FXML
	public TableColumn<ModelTableRating, String> col_place;
	/**
	 * Spalte zur Anzeige der Spitzname
	 */
	@FXML
	public TableColumn<ModelTableRating, String> col_nickname;
	/**
	 * Spalte zur Anzeige der Anzahl der Punkten
	 */
	@FXML
	public TableColumn<ModelTableRating, String> col_score;
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
     * Tabellenanzeige
     * @see ModelTableRating
     */
    ObservableList<ModelTableRating> oblist=FXCollections.observableArrayList();
    /** Initialisieren der Datentabelle
     * @see DataBaseHandler#getConnection()
     * @see ModelTableRating
     */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nickname.setText(getUsername());
		balance.setText(Integer.toString(getPoint()));
    		Connection con = null;
		try {
			con = DataBaseHandler.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
		//	ResultSet rs_newtable=con.createStatement().executeQuery("CREATE TABLE Bon (itemid int(11), itemname varchar(50))");
			rs = con.createStatement().executeQuery("SELECT * FROM "+DB_MySQL_const.USER_TABLE+" ORDER BY "+DB_MySQL_const.USER_SCORE+" DESC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			int i=1;
			while (rs.next()==true) {
				oblist.add(new ModelTableRating(Integer.toString(i), 
							rs.getString(DB_MySQL_const.USER_NICK), 
							rs.getString(DB_MySQL_const.USER_SCORE)
							));
				i++;	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	col_nickname.setCellValueFactory(new PropertyValueFactory<ModelTableRating, String>("nickname"));
		col_place.setCellValueFactory(new PropertyValueFactory<ModelTableRating, String>("place"));	
		col_score.setCellValueFactory(new PropertyValueFactory<ModelTableRating, String>("score"));
		table_rating.setItems(oblist);
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
    
    
}