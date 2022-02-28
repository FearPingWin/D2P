package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *Server mit Anmelde- und Registrierungsanfragen
 */
public class Server {
	/**
	 * {@value} PORT- port Adress des Servers
	 */
	private static final int PORT=62298;
	/**
	 * Startet einen Server, der auf Anmelde- und Registrierungsanfragen antwortet
	 * @throws ClassNotFoundException - - Fehler beim Verbinden mit der Datenbank
	 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
	 */
	public void serverWork() throws ClassNotFoundException, SQLException {
		//System.out.println("Start");
		try {
			ServerSocket server = new ServerSocket(PORT);
			boolean beenden =true;
			while (beenden) {
				//http://185.204.3.242:62298/
			Socket client = server.accept();
			System.out.println("Client connected");
				try {
					BufferedReader empfang = new BufferedReader( new InputStreamReader(client.getInputStream()) );					
					PrintWriter senden = new PrintWriter(client.getOutputStream());
					String zeile = null;
					while ((zeile=empfang.readLine())!=null) {
						System.out.println(zeile +" -server becommt von client");
						if (zeile.equals("beenden")) {
							beenden=false;
						}
						ArrayList <String> list = new ArrayList<>();
				        for (String retval : zeile.split(",")) {    
				            list.add(retval);
				        }
						if (list.get(0).equals("login")) {
							if(loginUser(list.get(1),list.get(2))>=1) {
							zeile = "Access";
							senden.println(zeile);
							senden.flush();	
							System.out.println(zeile);
							}else {
								zeile = "Wrong login or pass";
								senden.println(zeile);
								senden.flush();	
								System.out.println(zeile);
							}	
						}
						if (list.get(0).equals("singup")) {
							if (singupUser(list.get(1), list.get(2), list.get(3), list.get(4), list.get(5))) {
								zeile = "Registration completed successfully";
								senden.println(zeile);
								senden.flush();	
								System.out.println(zeile);
							}
							else {
							zeile = "Nickname or e-Mail is already taken";
							senden.println(zeile);
							senden.flush();	
							System.out.println(zeile);
							}
						}
					}
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("Server beendet"); 
			server.close();			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Anmeldung des Benutzers
	 * @param login_name - die Spitzname
	 * @param login_pass - das Passwort
	 * @return - 1, wenn der Benutzer in der Datenbank ist. 0, wenn er nicht gefunden wurde.
	 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
	 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
	 */
	private static int loginUser(String login_name, String login_pass) throws ClassNotFoundException, SQLException {
		DataBaseHandler dbHandler= new DataBaseHandler();
		ResultSet result =dbHandler.getUser(login_name, login_pass);
		int count=0;
		try {
			while (result.next()) {
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}	
	/**
	 * Registrierung des Benutzers
	 * @param nickname - die Spitzname
	 * @param pass - das Passwort
	 * @param firstname - die Vorname
	 * @param lastname - die Nachname
	 * @param email - die e-Mail Adresse
	 * @return - true erfolgreich registriert, false - nicht
	 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
	 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
	 */
	private static boolean singupUser(String nickname,String pass, String firstname, String lastname,String email) throws ClassNotFoundException, SQLException  {	
		boolean result =false;
		DataBaseHandler dbHandler= new DataBaseHandler();
		ResultSet find_name =dbHandler.find_date_in_columm(DB_MySQL_const.USER_TABLE, DB_MySQL_const.USER_NICK, nickname);
		ResultSet find_mail =dbHandler.find_date_in_columm(DB_MySQL_const.USER_TABLE, DB_MySQL_const.USER_EMAIL, nickname);
		try {
			if (find_name.next()==true || find_mail.next()==true) {
				return false;
			}else {
				dbHandler.singUpUser(nickname, pass, firstname, lastname, email);
				//создать таблицу для пользователя
				dbHandler.create_table_user(nickname);
				result =true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;
	}
}
