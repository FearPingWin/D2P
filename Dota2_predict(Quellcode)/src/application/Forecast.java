package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import Server.DB_MySQL_const;
import Server.DataBaseHandler;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
/**
 * Controller fuer das Prognosefenster
 *@see Conrollers_function
 *@see Initializable
 */

public class Forecast extends Conrollers_function implements Initializable{
    /**
     * zeigt Meldungen in diesem Fenster an
     */
	@FXML
    public Label lb_forecast_message;
	/**
	 * zeigt den Namen des Spiels mit den spielenden Teams an
	 */
    @FXML
    public Label lbMatchTitel;
    /**
     * Knopf zur Vorhersage fuer 1 Team
     */
    @FXML
    public Button btnTeam1;
    /**
     * Knopf zur Vorhersage fuer 2 Team
     */
    @FXML
    public Button btnTeam2; 
    /**
     * Knopf um Informationen zu aktualisieren
     */
    @FXML
    public Button btnRefrech;
    /**
     * zeigt den ersten Teamnamen an
     */
    @FXML
    public Label lbWinTeam1;
    /**
     * zeigt den zweiten Teamnamen an
     */
    @FXML
    public Label lbWinTeam2;
    /**
     * zeigt die Gesamtzahl der gespielten Punkte an
     */
    @FXML
    public Label lbTotalPoint;
    /**
     * zeigt die Gesamtzahl der Punkte an, die fuer die erste Mannschaft vergeben wurden
     */
    @FXML
    public Text textPointTeam1;
    /**
     * zeigt die Gesamtzahl der Punkte an, die fuer die zweite Mannschaft vergeben wurden
     */
    @FXML
    public Text textPointTeam2;
    /**
     * prozentualer Anteil der Punkte der ersten Mannschaft an der Gesamtzahl
     */
    @FXML
    public Text textTeam1Prozent;
    /**
     * prozentualer Anteil der Punkte der zweiten Mannschaft an der Gesamtzahl
     */
    @FXML
    public Text textTeam2Prozent;
    /**
     * Koeffizient fuer die erste Mannschaft
     */
    @FXML
    public Text textTeam1Koef;
    /**
     * Koeffizient fuer die zweite Mannschaft
     */
    @FXML
    public Text textTeam2Koef;
    /**
     * Feld zur Eingabe der Punkte, die auf die erste Mannschaft gesetzt wird
     */
    @FXML
    public TextField tfPointTeam1;
    /**
     * Feld zur Eingabe der Punkte, die auf die zweite Mannschaft gesetzt wird
     */
    @FXML
    public TextField tfPointTeam2;

    /**
     * Punkte auf die erste Mannschaft setzen
     * @param event - gedruekt @see {@link #btnTeam1}
     * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
     * @throws SQLException - Fehler beim Zugriff auf die Datenbank
     */
    
    public void putTeam1(ActionEvent event) throws ClassNotFoundException, SQLException {
    	int team1 = Integer.parseInt(tfPointTeam1.getText());
    	DataBaseHandler handler =new DataBaseHandler();
    	//проверить истекло ли время на ставку.
    	ResultSet rs =null;
    	Connection con = null;
    	con = DataBaseHandler.getConnection();
    	rs = con.createStatement().executeQuery("SELECT * FROM "+ DB_MySQL_const.DOTA_MATCH_TABLE+ " WHERE "+ DB_MySQL_const.ID+"="+getMatchID());
		if (rs.next()) {
	    	if (rs.getInt(DB_MySQL_const.STATUS) == 0 ) {
	    		//System.out.println("можно еще ставить");
	        	// проверить наличие очков для ставки очки минус замороженые
	        	int block=handler.getBlockScore(getUsername());
	        	int score=handler.getScore(getUsername());
	        	if (team1<score -block && team1>0 ) {
	            	
	            	long dateNow = new Date(System.currentTimeMillis()).getTime();
	            	handler.save_predict("match"+getMatchID(),getUsername(), getTeam1(), team1, dateNow);
	            	handler.save_predict_History(getUsername(), dateNow, getMatchID(), getTeam1()+ " vs "+getTeam2()+": Win "+getTeam1(), team1,"expect", score);
	            	handler.edit_cell(DB_MySQL_const.USER_TABLE, handler.getUserIDDB(getUsername()), DB_MySQL_const.USER_BLOCKSCORE, Integer.toString (block+team1));// морозить очки при ставке
	            	refresh();
	            	lb_forecast_message.setText("You have successfully placed "+team1+" points - "+ getTeam1());
	        	}else {
	        		  lb_forecast_message.setText("Not enough points or wait until the end of the predictions");
	        	}
	    	} else {
	    		lb_forecast_message.setText("Reception of predictions for this match is over");
	    	}
		}
    	if (con!=null) {
    		con.close();
    	} 
    	
    }
    /**
     * Punkte auf die zweite Mannschaft setzen
     * @param event - gedruekt @see {@link #btnTeam2}
     * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
     * @throws SQLException - Fehler beim Zugriff auf die Datenbank
     */
    @FXML
    public void putTeam2(ActionEvent event) throws ClassNotFoundException, SQLException {
    	int team2 = Integer.parseInt(tfPointTeam2.getText());
    	DataBaseHandler handler =new DataBaseHandler();
    	//проверить истекло ли время на ставку.
    	ResultSet rs =null;
    	Connection con = null;
    	con = DataBaseHandler.getConnection();
		rs = con.createStatement().executeQuery("SELECT * FROM "+ DB_MySQL_const.DOTA_MATCH_TABLE+ " WHERE "+ DB_MySQL_const.ID+"="+getMatchID());
		if (rs.next()) {
	    	if (rs.getInt(DB_MySQL_const.STATUS) == 0 ) {
	    		//System.out.println("можно еще ставить");
	        	// проверить наличие очков для ставки очки минус замороженые
	        	int block=handler.getBlockScore(getUsername());
	        	int score=handler.getScore(getUsername());
	        	if (team2<score -block && team2>0) {	            	
	            	long dateNow = new Date(System.currentTimeMillis()).getTime();
	            	handler.save_predict("match"+getMatchID(),getUsername(), getTeam2(), team2,dateNow);
	            	handler.save_predict_History(getUsername(), dateNow, getMatchID(), getTeam1()+ " vs "+getTeam2()+": Win "+getTeam2(), team2,"expect", score);
	            	handler.edit_cell(DB_MySQL_const.USER_TABLE, handler.getUserIDDB(getUsername()), DB_MySQL_const.USER_BLOCKSCORE, Integer.toString (block+team2));// морозить очки при ставке
	            	refresh();
	            	lb_forecast_message.setText("You have successfully placed "+team2+" points - "+ getTeam2());
	        	}else {
	        		  lb_forecast_message.setText("Not enough points or wait until the end of the predictions");
	        	}
	    	} else {
	    		lb_forecast_message.setText("Reception of predictions for this match is over");
	    	}
		}
    	if (con!=null) {
    		con.close();
    	}
    }
    /**
     * Aktualisierung von Informationen zu Punkten und Koeffizienten
     * @param event - gedruekt @see {@link #btnRefrech}
     * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
     * @throws SQLException - Fehler beim Zugriff auf die Datenbank
     */
    public void refreshPoints(ActionEvent event) throws ClassNotFoundException, SQLException {
    	refresh();
    }
    /**
     * Aktualisierung von Informationen zu Punkten und Koeffizienten
     * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
     * @throws SQLException - Fehler beim Zugriff auf die Datenbank
     */
	public void refresh() throws ClassNotFoundException, SQLException {
		Connection con = DataBaseHandler.getConnection();
		ResultSet rs = con.createStatement().executeQuery("SELECT * FROM match"+ getMatchID());
		int Sum=0;
		int Sum_team1=0;
		while (rs.next()==true) {
			Sum=Sum + rs.getInt(DB_MySQL_const.SUM_MATCHID);
			if (rs.getString(DB_MySQL_const.WINER_MATCHID).equals(getTeam1())) {
				Sum_team1=Sum_team1+rs.getInt(DB_MySQL_const.SUM_MATCHID);
			}
		}
		lbTotalPoint.setText(String.valueOf(Sum) );
		textPointTeam1.setText(String.valueOf(Sum_team1));
		textPointTeam2.setText(String.valueOf(Sum-Sum_team1));
		lb_forecast_message.setText("Data has been updated");
		double proz1=(double)Sum_team1/Sum*100;
		double proz2=(double)(Sum-Sum_team1)/Sum*100;
		if (Sum!=0 && Sum_team1!=0) {
		    textTeam1Prozent.setText(String.format("%.2f", proz1)+" %");
		    textTeam2Prozent.setText(String.format("%.2f", proz2)+" %");
		    textTeam1Koef.setText("1:"+String.format("%.2f", 100/proz1));
		    textTeam2Koef.setText("1:"+String.format("%.2f", 100/proz2));
		    if (Sum==Sum_team1) {
		    	textTeam2Koef.setText("0");
		    }
		}
    	if (con!=null) {
    		con.close();
    	}
	}
	/**
	 * Initialisierung der Daten in dem Fenster
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbMatchTitel.setText("Match id "+getMatchID()+" "+getTeam1()+" vs "+getTeam2());
		btnTeam1.setText(getTeam1());
		btnTeam2.setText(getTeam2());
		lbWinTeam1.setText("Win "+getTeam1());
		lbWinTeam2.setText("Win "+getTeam2());
		lbTotalPoint.setText(String.valueOf(0) );
		textPointTeam1.setText(String.valueOf(0));
		textPointTeam2.setText(String.valueOf(0));
	    textTeam1Prozent.setText("0");
	    textTeam1Koef.setText("0");
	    textTeam2Prozent.setText("0");
	    textTeam2Koef.setText("0");
	    try {
			refresh();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // ввод только чисел
		UnaryOperator<javafx.scene.control.TextFormatter.Change> integerFilter = change -> {
		    String input = change.getText();
		    if (input.matches("[0-9]*")) { 
		        return change;
		    }
		    return null;
		};
		tfPointTeam1.setTextFormatter(new TextFormatter<String>(integerFilter));
		tfPointTeam2.setTextFormatter(new TextFormatter<String>(integerFilter));
	}
}
