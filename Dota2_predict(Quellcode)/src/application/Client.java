package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 *  
 *Client verbindet sich mit Server
 *
 */
public class Client {
/**	
*{@value} IP_SERVER  - IP Adress des Servers
*{@value} PORT- port Adress des Servers
 */
	private static final String IP_SERVER="185.204.3.242";
	private static final int PORT=62298;
/**
 * Anfrage zur Teilnahme am Spiel
 * @param login - Benutzerlogin
 * @param pass- Benutzerpasswort 
 * @return -gibt die Serverantwort zurueck, ob Benutzername und Passwort korrekt sind
 * @throws UnknownHostException - Socket konnte nicht erstellt werden
 * @throws IOException - Fehler bei Verarbeitung der Streams
 */
	public String sendRequestLogin (String login,String pass) throws UnknownHostException, IOException {
		String antwort=null;
		Socket server= new Socket(IP_SERVER,PORT);
		BufferedReader empfang= new BufferedReader(new InputStreamReader(server.getInputStream()));
		PrintWriter senden = new PrintWriter(server.getOutputStream());
			String zeile="login,"+login+","+pass;
			senden.println(zeile);
			senden.flush();
			antwort=empfang.readLine().toString();
			//antwort=empfang.readLine();
			//System.out.println(antwort +" -klient becommt von server");	
		server.close();
		
		return antwort;
	}
/**
 * 	Neue Benutzerregistrierung
 * @param nickname- Benutzerspitznamen
 * @param pass- Benutzerpassword
 * @param firstname- Vorname der Benutzers 
 * @param lastname- Name der Benutzers
 * @param email- e-mail Adress der Benutzers
 * @return gibt die Serverantwort zurueck, ob Benutzer erfolgreich erstellt oder nicht
 * @throws UnknownHostException - Socket konnte nicht erstellt werden
 * @throws IOException -Fehler bei Verarbeitung der Streams
 */
	public String sendRequestSingup (String nickname,String pass, String firstname, String lastname,String email) throws UnknownHostException, IOException {
		String antwort=null;
		Socket server= new Socket(IP_SERVER,PORT);
		BufferedReader empfang= new BufferedReader(new InputStreamReader(server.getInputStream()));
		PrintWriter senden = new PrintWriter(server.getOutputStream());
			String zeile="singup,"+nickname+","+pass+","+firstname+","+lastname+","+email;
			senden.println(zeile);
			senden.flush();
			antwort=empfang.readLine().toString();
			//antwort=empfang.readLine();
			//System.out.println(antwort +" -klient becommt von server");	
		server.close();
		
		return antwort;
	}



}

/*	public static void main(String[] args) throws IOException {
//Socket server= new Socket("127.0.0.1",62297);
Socket server= new Socket("185.204.3.242",62298);
BufferedReader empfang= new BufferedReader(new InputStreamReader(server.getInputStream()));
PrintWriter senden = new PrintWriter(server.getOutputStream());
BufferedReader tastatur= new BufferedReader(new InputStreamReader(System.in));
boolean beenden=true;
while (beenden) {
	//
	String zeile= tastatur.readLine();
	senden.println(zeile);
	senden.flush();
	System.out.println(empfang.readLine() +" -klient becommt von server");
	if (zeile.equals("beenden")) {
		beenden=false;
	}
	
}

server.close();
}*/
