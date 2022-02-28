package application;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
/**
 * Parameter zum Fuellen der Spieltabelle
 */
public class ModelTableMatches {

	private int id;
	private String time; 
	private String tournament;
	private String team1;
	private String versus;
	private String team2;
	private Button button;
/**
 * Konstrukteur erstellt ein Tabellenmodellobjekt zum Auffuellen der Matchestabelle		
 * @param id - die Spielnummer
 * @param time - die Spielzeit
 * @param tournament - die Turniername
 * @param team1 - erste Mannschaft
 * @param versus - die Anzahl der Karten, um das Spiel zu gewinnen
 * @param team2 - zweite Mannschaft
 * @param button - Knopfen, um die Prognose zu machen
 */
public ModelTableMatches(int id,String time,String tournament,String team1,String versus,String team2,Button button ){
	this.id=id;
	this.time=time;
	this.tournament=tournament;
	this.team1=team1;
	this.versus=versus;
	this.team2=team2;
	this.button= new Button ("make a forecast");
}
/**
 * die Spielnummer zu erhalten
 * @return - die Spielnummer
 */
public int getId() {
	return id;
}
/**
 * die Spielnummer zu festlegen
 * @param id - Spielnummer
 */
public void setId(int id) {
	this.id = id;
}
/**
 * die Spielzeit zu erhalten
 * @return - Spielzeit
 */
public String getTime() {
	return time;
}
/**
 * die Spielzeit zu festlegen
 * @param time - die Spielzeit
 */
public void setTime(String time) {
	this.time = time;
}
/**
 * die Turniername zu erhalten
 * @return - die Turniername
 */
public String getTournament() {
	return tournament;
}
/**
 * die Turniername zu festlegen
 * @param tournament -die Turniername
 */
public void setTournament(String tournament) {
	this.tournament = tournament;
}
/**
 * erste Mannschaft zu erhalten
 * @return - erste Mannschaft
 */
public String getTeam1() {
	return team1;
}
/**
 * erste Mannschaft zu festlegen
 * @param team1 - erste Mannschaft
 */
public void setTeam1(String team1) {
	this.team1 = team1;
}
/**
 * die Anzahl der Karten fuer Speilgewinn zu erhalten
 * @return - die Anzahl der Karten fuer Speilgewinn
 */
public String getVersus() {
	return versus;
}
/**
 * die Anzahl der Karten fuer Speilgewinn zu festlegen
 * @param versus - die Anzahl der Karten fuer Speilgewinn
 */
public void setVersus(String versus) {
	this.versus = versus;
}
/**
 * zweite Mannschaft zu erhalten
 * @return - zweite Mannschaft
 */
public String getTeam2() {
	return team2;
}
/**
 * zweite Mannschaft zu festlegen
 * @param team2 - zweite Mannschaft
 */
public void setTeam2(String team2) {
	this.team2 = team2;
}
/**
 * der Knopf zu erhalten
 * @return - der Knopf
 */
public Button getButton() {
	return button;
}
/**
 * der Knopf zu festlegen
 * @param button- der Knopf
 */
public void setButton(Button button) {
	this.button = button;
}	

}
