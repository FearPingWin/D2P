package application;
/**
 * Parameter zum Fuellen der Historytabelle
 */
public class ModelTableHistory {
	private int number;
	private String time;
	private int  match;
	private String description;
	private String change;	
	private int balance;	
	private int bet;
/**
 * Konstrukteur erstellt ein Tabellenmodellobjekt zum Auffuellen der Historytabelle
 * @param number - die Ereignisnummer
 * @param time - die Ereigniszeit
 * @param match - die Spielnummer
 * @param description - die Ereignisbeschreibung
 * @param balance - die Punkten zum Zeitpunkt des Ereignisses
 * @param bet - die Anzahl der verwendeten Punkte
 * @param change - die Aenderung der Punkte nach dem Ereignis
 */
public ModelTableHistory(int number,String time,int match, String description,  int balance,int bet,String change ) {
	this.number=number;
	this.time=time;
	this.match=match;
	this.description=description;
	this.change=change;
	this.balance=balance;
	this.bet=bet;
	}
/** die Ereignisnummer zu erhalten
 * @return - die Ereignisnummer
 */
public int getNumber() {
	return number;
}
/** die Ereignisnummer zu festlegen
 * @param number - die Ereignisnummer
 */
public void setNumber(int number) {
	this.number = number;
}
/** die Ereigniszeit zu erhalten
 * @return - die Ereigniszeit
 */
public String getTime() {
	return time;
}
/** die Ereigniszeit zu festlegen
 * @param time - die Ereigniszeit
 */
public void setTime(String time) {
	this.time = time;
}
/**  die Spielnummer zu erhalten
 * @return - die Spielnummer
 */
public int getMatch() {
	return match;
}
/**
 * die Spielnummer zu festlegen
 * @param match - Spielnummer
 */
public void setMatch(int match) {
	this.match = match;
}
/**  die Ereignisbeschreibung zu erhalten
 * @return - die Ereignisbeschreibung
 */
public String getDescription() {
	return description;
}
/**
 * die Ereignisbeschreibung zu festlegen
 * @param description - die Ereignisbeschreibung 
 */
public void setDescription(String description) {
	this.description = description;
}
/**  die Aenderung der Punkte nach dem Ereignis zu erhalten
 * @return -  die Aenderung der Punkte
 */
public String getChange() {
	return change;
}
/**
 *  die Aenderung der Punkte nach dem Ereignis zu festlegen
 * @param change - die Aenderung der Punkte
 */
public void setChange(String change) {
	this.change = change;
}
/**  die Punkten zum Zeitpunkt des Ereignisses zu erhalten
 * @return - die Punkten zum Zeitpunkt
 */
public int getBalance() {
	return balance;
}
/**
 * die Punkten zum Zeitpunkt des Ereignisses zu festlegen
 * @param balance - die Punkten zum Zeitpunkt des Ereignisses
 */
public void setBalance(int balance) {
	this.balance = balance;
}
/**
 * die Anzahl der verwendeten Punkte zu erhalten
 * @return - Anzahl der verwendeten Punkte
 */
public int getBet() {
	return bet;
}
/**
 * die Anzahl der verwendeten Punkte zu festlegen
 * @param bet - Anzahl der verwendeten Punkte
 */
public void setBet(int bet) {
	this.bet = bet;
}

}