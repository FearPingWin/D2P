package application;

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
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
/**
 * Controller fuer Matches. Spieltabelle.
 *@see Conrollers_function
 *@see Initializable
 */

public class Matches extends Conrollers_function implements Initializable{
	/**
	 * die Tabelle zeigt die verfuegbaren Spiele an
	 */
    @FXML
    public TableView<ModelTableMatches> tableMatches;
    /**
     * Rating-Knopf
     */
	@FXML
    public Button btnRating;
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
     *  Spalte zur Anzeige der Spielnummer
     */
    @FXML
    public TableColumn<ModelTableMatches, Integer> colID;
    /**
     *  Spalte zur Anzeige der Spielzeit
     */
    @FXML
    public TableColumn<ModelTableMatches, String> colTime;
    /**
     *  Spalte zur Anzeige der Turniername
     */
    @FXML
    public TableColumn<ModelTableMatches, String> colTourn;
    /**
     *  Spalte zur Anzeige der ersten Mannschaft
     */
    @FXML
    public TableColumn<ModelTableMatches, String> colTeam1;
    /**
     *  Spalte zur Anzeige der Anzahl der Karten, um das Spiel zu gewinnen
     */
    @FXML
    public TableColumn<ModelTableMatches, String> colVersus;
    /**
     *  Spalte zur Anzeige der zweiten Mannschaft
     */
    @FXML
    public TableColumn<ModelTableMatches, String> colTeam2;
    /**
     *  Spalte zur Anzeige der Knopfen, um die Prognose zu machen
     */
    @FXML
    public TableColumn<ModelTableMatches, Button> colButton;
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
	 * aendert das aktuelle Fenster in das Historyfenster
	 * @see Conrollers_function#setwindow(Button, String)
	 * @param event - gedrueckt @see {@link #btnHistory}
	 */
    @FXML
    public void setWindHistory(ActionEvent event) {
    	setwindow(btnHistory,"History.fxml");
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
     * @see ModelTableMatches

     */
    ObservableList<ModelTableMatches> oblist=FXCollections.observableArrayList();
	/**Initialisieren der Spieltabelle
	 * @see DataBaseHandler#getConnection()
	 * @see ModelTableMatches
	 */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nickname.setText(getUsername());
		balance.setText(Integer.toString(getPoint()));
		ResultSet rs =null;
		Connection con = null;
		try {
			con = DataBaseHandler.getConnection();
			rs = con.createStatement().executeQuery("SELECT * FROM "+ DB_MySQL_const.DOTA_MATCH_TABLE+ " WHERE "+ DB_MySQL_const.STATUS+"=0");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			int i=1;
			while (rs.next()==true) {
				
				oblist.add(new ModelTableMatches( 
							rs.getInt(DB_MySQL_const.ID), 
							rs.getString(DB_MySQL_const.TIME), 
							rs.getString(DB_MySQL_const.TOURNAMENT), 
							rs.getString(DB_MySQL_const.TEAM_1),
							rs.getString(DB_MySQL_const.VERSUS),
							rs.getString(DB_MySQL_const.TEAM_2),
							btnAccount
							));
				i++;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		colID.setCellValueFactory(new PropertyValueFactory<ModelTableMatches, Integer>("id"));
		colTime.setCellValueFactory(new PropertyValueFactory<ModelTableMatches, String>("time"));	
		colTourn.setCellValueFactory(new PropertyValueFactory<ModelTableMatches, String>("tournament"));
		colTeam1.setCellValueFactory(new PropertyValueFactory<ModelTableMatches, String>("team1"));
		colVersus.setCellValueFactory(new PropertyValueFactory<ModelTableMatches, String>("versus"));
		colTeam2.setCellValueFactory(new PropertyValueFactory<ModelTableMatches, String>("team2"));
		colButton.setCellValueFactory(new PropertyValueFactory<ModelTableMatches, Button>("button"));
		tableMatches.setItems(oblist);	
		
		for (int i=0;i<oblist.size();i++) {
			tableMatches.getItems().get(i).getButton().setOnAction(this::handleButtonOnAction );
		}
		/*
		System.out.println(tableMatches.getItems().get(0).getId());
		System.out.println(tableMatches.getItems().get(0).getButton());
		System.out.println(tableMatches.getItems().get(2).getButton());
		System.out.println(oblist.size());
		*/
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/** oeffnet ein neues Prognosefenster
 * @see DataBaseHandler#create_table_forecasteid(String)
 * @param e - gedrueckt Knopf
 */
public void handleButtonOnAction(ActionEvent e) {
	//cоздать табдицу в 
	for (int i =0;i<oblist.size();i++) {
		if (e.getSource() ==tableMatches.getItems().get(i).getButton() ) {
		
		setMatchID(tableMatches.getItems().get(i).getId());
		setTeam1(tableMatches.getItems().get(i).getTeam1());
		setTeam2(tableMatches.getItems().get(i).getTeam2());
		//создать таблицу при нажатии отправить запрос на сервер и потом выполнить это, но пока без сервера
			DataBaseHandler handler= new DataBaseHandler();
			try {
				handler.create_table_forecasteid("match"+tableMatches.getItems().get(i).getId());
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		openNewWindow (tableMatches.getItems().get(i).getButton(), "Forecast.fxml");
		handler.closeConnection();//17.08
		}
	}

}
}
