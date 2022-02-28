package Server;

import java.sql.SQLException;
/**
 * Gemeinsamer Server zum Ausfuehren von Client-Anfragen und Empfangen von Daten von der Webseiten
 *@see Parser
 */
public class Many_Thread extends Parser  {
	/**
	 * Hauptserverkoerper
	 * @param args - Standardparameter
	 */
	public static void main(String[] args) {

		//create 3 table  users, dotamatchs, dotabaff
		try {
			create_table_all_users(DB_MySQL_const.USER_TABLE);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			create_table_dotabaff(DB_MySQL_const.DOTABUFF_TABLE);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			create_table_dotamatchs(DB_MySQL_const.DOTA_MATCH_TABLE);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Start");
		//---------------------------------------------
		Check_new_game new_game = new Check_new_game();
		new_game.start();
		Check_live_game live_game =new Check_live_game();
		live_game.start();
		Chek_complited_match complited_match= new Chek_complited_match();
		complited_match.start();
		Server server = new Server();
		try {
			server.serverWork();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
/**
 *Suche nach einem neuen bevorstehenden Spiel auf https://liquipedia.net/
 *Das gefundene Spiel erhaelt den Status 0.
 *@see Thread
 *@see Parser#getLiquipediaMatchs()
 */
private static class Check_new_game extends Thread{
	public boolean run=true;
	public void run () {
		while (run) {
			System.out.println("check_new_game");//status =0
			try {
				// парсим станицу
				//проверяем есть ли в таблице уже данная игра по времени, и названию команд 
				//если игры нет записать в базу все данные
				getLiquipediaMatchs();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(1000*3600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
/**
 * Ueberprurfung, ob das Spiel gestartet wurde. 
 * Wenn ja,den Status wird auf 1 geaendert und der Spielstand wird jede Minute ueberprueft.
 *@see Thread
 *@see Parser#live_match_result()
 */
private static class Check_live_game extends Thread{
	public boolean run=true;
	public void run () {
		while (run) {
		System.out.println("check_live_game"); // status =1
		//проверяем по дататиме началась ли игра, присваеваем статус 1
		// отслеживаем каждую минуту игру и вносим в случае изменения результата счет в таблицу
		//
		try {
			live_match_result();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(1000*60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
}
/**
 * Pruefung, ob das Spiel vorbei ist,ja - setzen den Status auf 2.
 * Pruefung, ob es die Prognose gab, ja- - setzen den Status auf 4,nein -5.
 * Wenn der Status 4 ist, die Punkte von Gewinner und Verlierer  berechnet werden.
 *@see Thread
 *@see Parser#chek_complited_match()
 *@see Parser#checkPrediktMatch()
 *@see Parser#payBets()
 */
private static class Chek_complited_match extends Thread{
	public boolean run=true;
	public void run () {
		while (run) {
			
			try {
				System.out.println("chek_complited_match Status 2");
				chek_complited_match();
				System.out.println("chek complited match Status 4/5 ");
				checkPrediktMatch();
				System.out.println("chek_complited_match pay bets");
				payBets();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
}
}
