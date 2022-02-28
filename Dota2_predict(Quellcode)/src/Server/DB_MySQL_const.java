package Server;
/**
 *Konstanten fuer Tabellen- und Spaltennamen
 */
public class DB_MySQL_const {
	//DB dotabuff link--------------------------------------------------------------------------------
	/**
	 * die Tabellename fuer Daten aus https://dotabaff.com
	 */
	public static final String DOTABUFF_TABLE= "dotabuff";
	/**
	 * die Spaltename fuer die Teams aus https://liquipedia.net/
	 */
	public static final String TEAM_LIQ= "team";
	/**
	 * Teil vom Link aus https://dotabaff.com
	 */
	public static final String BUFF_LINK= "link";
	/**
team_dotabuff
	 * abgekuerzter Teamname aus https://dotabaff.com
	 */
	public static final String TEAM_BUFF= "team_dotabuff";
	//DB USERS---------------------------------------------------------------------------------------------
	/**
	 * die Tabellename fuer Benutzersdaten 
	 */
	public static final String USER_TABLE= "users";
	/**
	 * die Spalte  mit der Benutzernummer
	 */
	public static final String ID_TABLEUSER= "id";
	/**
	 * die Spalte  mit der Spitzname
	 */
	public static final String USER_NICK= "nickname";
	/**
	 * die Spalte  mit dem Passwort
	 */
	public static final String USER_PASS= "password";
	/**
	 * die Spalte  mit der Vorname
	 */
	public static final String USER_FIRSTNAME= "first_name";
	/**
	 * die Spalte  mit der Nachname
	 */
	public static final String USER_LASTNAME= "second_name";
	/**
	 * die Spalte  mit der e-Mail Adresse
	 */
	public static final String USER_EMAIL= "email";
	/**
	 * die Spalte  mit der Benutzerpunkte
	 */
	public static final String USER_SCORE= "score";
	/**
	 * die Spalte  mit der gesperrte Punkte
	 */
	public static final String USER_BLOCKSCORE= "blockscore";
	//DB dota_match---------------------------------------------------------------------------------------------
	/**
	 *  die Tabellename fuer Dota2-Spielen 
	 */
	public static final String DOTA_MATCH_TABLE= "dota_match";
	/**
	 * die Spalte  mit der Spielnummer
	 */
	public static final String ID= "id";
	/**
	 *  die Spalte  mit der Zeit
	 */
	public static final String TIME= "time";
	/**
	 *  die Spalte  mit der Turniername
	 */
	public static final String TOURNAMENT= "tournament";
	/**
	 * die Spalte  mit der ersten Mannschaft
	 */
	public static final String TEAM_1= "team1";
	/**
	 * die Spalte  mit der zweiten Mannschaft
	 */
	public static final String TEAM_2= "team2";
	/**
	 * die Spalte  mit der Anzahl der gewonnenen Karten von der ersten Mannschaft
	 */
	public static final String TEAM_1_SCORE= "team1_score";
	/**
	 * die Spalte  mit der Anzahl der gewonnenen Karten von der zweiten Mannschaft
	 */
	public static final String TEAM_2_SCORE= "team2_score";
	/**
	 * Anzahl der Karten zum Gewinnen
	 */
	public static final String VERSUS= "versus";
	/**
	 * 	die Spalte  mit der Zeit in Millisekunden
	 */
	public static final String DATETIME= "datatime";
	/**
	 * die Spalte  mit dem Teil vom Link fuer erste Mannschaft aus https://liquipedia.net/
	 */
	public static final String LINK1= "link1";
	/**
	 * die Spalte  mit dem Teil vom Link fuer zweite Mannschaft aus https://liquipedia.net/
	 */
	public static final String LINK2= "link2";
	/**
	 * die Spalte  mit dem Status des Spiels
	 */
	public static final String STATUS= "status";
	//DB History user---------------------------------------------------------------------------------------------
	/**
	 * die Spaltennamen aus der Benutzertabelle
	 */
	/**
	 * die Spalte  mit der Nummer des Ereignises
	 */
	public static final String ID_HISTORY= "id";
	/**
	 * die Spalte  mit der Zeit in Millisekunden
	 */
	public static final String TIME_HISTORY= "time";
	/**
	 * die Spalte  mit der Spielnummer
	 */
	public static final String MATCHID_HISTORY= "eventid";
	/**
	 * die Spalte  mit der Ereignisbeschreibung
	 */
	public static final String EVENT_HISTORY= "event";
	/**
	 * die Spalte  mit der Aenderung der Balance nach dem Ereignis
	 */
	public static final String DELTA_HISTORY= "delta";
	/**
	 * die Spalte  mit der Balance
	 */
	public static final String BALANCE_HISTORY= "balance";
	/**
	 * die Spalte  mit der Anzahl der Punkte, die benuzt wurde
	 */
	public static final String BET_HISTORY= "bet";
	//DB Predict Match ID---------------------------------------------------------------------------------------------
	/**
	 * die Spaltennamen aus der Prognosetabelle
	 */
	/**
	 * die Spalte  mit der Nummer der Prognose
	 */
	public static final String ID_MATCHID= "id";
	/**
	 * die Spalte  mit der Spitzname
	 */
	public static final String NICKNAME_MATCHID= "nickname";
	/**
	 * die Spalte  mit der gewaehlte Mannschaft
	 */
	public static final String WINER_MATCHID= "teamwin";
	/**
	 * die Spalte  mit der Anzahl der Punkte
	 */
	public static final String SUM_MATCHID= "sum";
	/**
	 * die Spalte  mit der Zeit in Millisekunden
	 */
	public static final String DATE_MATCHID= "time";
	
}
