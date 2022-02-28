package Server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Methode zum Arbeiten mit der Datenbank
 * @see Configs_server
 */
public class DataBaseHandler extends Configs_server{
	static Connection db_connection;
	// сделать по нормальному через трай кач
	/**
	 * Datenbankverbindung
	 * @return - Datenbankverbindung
	 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
	 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
	 */
	public static Connection getConnection () throws ClassNotFoundException, SQLException{
	String string_connection ="jdbc:mysql://"+dbHost+":"+dbPort +"/"+ dbName;	
	db_connection= DriverManager.getConnection(string_connection,dbName,dbPass);	
	return db_connection;	
	}
	/**
	 * schliesst die Datenbankverbindung
	 */
	public void closeConnection () {
		try {
			db_connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//запись в базу
	/**
	 * Hinzufuegen neuer Benutzerdaten zur Tabelle
	 * @param nickname- die Spitzname
	 * @param pass - das Passwort
	 * @param firstname - die Vorname
	 * @param lastname - die Nachname
	 * @param email - e-Mail Adresse
	 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
	 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
	 */
	public void singUpUser(String nickname, String pass, String firstname,String lastname,String email  ) throws ClassNotFoundException, SQLException {
		String insert ="INSERT INTO " + DB_MySQL_const.USER_TABLE 
		+"("+DB_MySQL_const.USER_NICK+ ","+ DB_MySQL_const.USER_PASS+","+ DB_MySQL_const.USER_FIRSTNAME 
			+","+DB_MySQL_const.USER_LASTNAME+","+DB_MySQL_const.USER_EMAIL
			+","+DB_MySQL_const.USER_SCORE+","+DB_MySQL_const.USER_BLOCKSCORE
		+")" +"VALUES (?,?,?,?,?,?,?)";
		PreparedStatement prSt=null;
		try {
		prSt =getConnection().prepareStatement(insert);
		prSt.setString(1, nickname);
		prSt.setString(2, pass);
		prSt.setString(3, firstname);
		prSt.setString(4, lastname);
		prSt.setString(5, email);
		prSt.setInt(6, 0);
		prSt.setInt(7, 0);
		prSt.executeUpdate();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
        if(prSt!=null){
        	prSt.close();
        }
    }		
	}
	//UPDATE `dota_match` SET `status` = '2' WHERE `dota_match`.`id` = 3;
	//поменять одну ячейку в базе по id
	/**
	 * Aendern eines Feldes in der Tabelle nach Zeilennummer
	 * @param name_DB - die Name der Tabelle
	 * @param id - Zeilennummer 
	 * @param collum - die Name der Spalte
	 * @param info - neue Information
	 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
	 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
	 */
	public static void edit_cell (String name_DB,String id, String collum, String info) throws ClassNotFoundException, SQLException {
		String insert =	"UPDATE "+name_DB+" SET "+collum+" = "+info+ 
				" WHERE "+ name_DB+"."+"id"+" = "+ id;
		//System.out.println(insert);
		//UPDATE `users` SET `eMail` = 'dasdsa@dasd.com' WHERE `users`.`id` = 7;
		PreparedStatement prSt=null;
		try {
			prSt = getConnection().prepareStatement(insert);
			prSt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            if(prSt!=null){
            	prSt.close();
            }
        }
	}
	//запрос в базе выдача найденых строк
	/**
	 * prueft das Vorhandensein eines Benutzers und eines Passworts in der Benutzerstabelle
	 * @param nickname - die Spitzname
	 * @param pass - das Passwort
	 * @return -  eine Zeile, die das Passwort und den Benutzernamen enthaelt
	 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
	 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
	 */
	public ResultSet getUser(String nickname, String pass) throws ClassNotFoundException, SQLException {
		ResultSet resSet = null;
		String selekt= "SELECT * FROM "+ DB_MySQL_const.USER_TABLE+ " WHERE BINARY " + DB_MySQL_const.USER_NICK+ "=? AND BINARY "
				+ DB_MySQL_const.USER_PASS + "=?";
		PreparedStatement prSt=null;
		try {
			prSt =getConnection().prepareStatement(selekt);
			prSt.setString(1, nickname);
			prSt.setString(2, pass);
			resSet=prSt.executeQuery();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*finally {
            if(prSt!=null){
            	prSt.close();
            }
        }*/
		return resSet;
	}	
	/**
	 * Suche nach einem Wert anhand einer bestimmten Spalte in der Tabelle
	 * @param name_DB - die Tabellename
	 * @param collum - die Spaltename
	 * @param date - das Wert
	 * @return - die Zeile, die das Wert erhaelt
	 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
	 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
	 */
		public static ResultSet find_date_in_columm(String name_DB,String collum, String date) throws ClassNotFoundException, SQLException {
			ResultSet resSet = null;
			String selekt= "SELECT * FROM "+ name_DB+ " WHERE "+ collum + "=?"; //+ DB_MySQL_const.USER_NICK+ "=? AND "
			PreparedStatement prSt=null;
			try {
				prSt =getConnection().prepareStatement(selekt);
				prSt.setString(1, date);
				resSet=prSt.executeQuery();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}/*finally {
	            if(prSt!=null){
	            	prSt.close();
	            }
	        }*/
			return resSet;		
	}
	/**
	 * 	Speichern der Spielparameter in der Tabelle
	 * @param time - der Anstosszeitpunkt
	 * @param tournament - die Turniername
	 * @param team1 - erste Mannschaft
	 * @param versus - die Anzahl der Karten, um das Spiel zu gewinnen
	 * @param team2 - zweite Mannschaft
	 * @param datetime - der Anstosszeitpunkt in Millisekunden
	 * @param link1 - der Link von der Website https://liquipedia.net/ zur ersten Mannschaft
	 * @param link2 - der Link von der Website https://liquipedia.net/ zur zweiten Mannschaft
	 */
	public void save_dota_match(String time, String tournament, String team1, String versus, String team2, String datetime, String link1, String link2) {
		String insert ="INSERT INTO " + DB_MySQL_const.DOTA_MATCH_TABLE 
		+ "(" +DB_MySQL_const.TIME 
		+","+ DB_MySQL_const.TOURNAMENT+","+ DB_MySQL_const.TEAM_1 +","+DB_MySQL_const.VERSUS+ ","+ DB_MySQL_const.TEAM_2
		+","+ DB_MySQL_const.TEAM_1_SCORE+","+  DB_MySQL_const.TEAM_2_SCORE 
		+","+ DB_MySQL_const.DATETIME+","+  DB_MySQL_const.LINK1 +","+  DB_MySQL_const.LINK2+","+  DB_MySQL_const.STATUS
		+ ")" +"VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement prSt=null;
	try {
		prSt =getConnection().prepareStatement(insert);
		prSt.setString(1, time);
		prSt.setString(2, tournament);
		prSt.setString(3, team1);
		prSt.setString(4, versus);
		prSt.setString(5, team2);
		prSt.setString(6, "0");
		prSt.setString(7, "0");
		prSt.setString(8, datetime);
		prSt.setString(9, link1);
		prSt.setString(10, link2);
		prSt.setString(11, "0");
		prSt.executeUpdate();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
        if(prSt!=null){
        	try {
				prSt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }		
	}
/**Speichern der Mannschaft in einer Tabelle zum manuellen Fuellen von Daten aus https://dotabuff.com
 * @param team - die Mannschaftname aus https://liquipedia.net/dota2/
 */
//---------------сохранить команду в таблицу для ручного заполнения	
	public static void save_dota_team(String team) {
		String insert ="INSERT INTO " + DB_MySQL_const.DOTABUFF_TABLE 
		+ "(" +DB_MySQL_const.TEAM_LIQ 
		+","+ DB_MySQL_const.BUFF_LINK+","+ DB_MySQL_const.TEAM_BUFF 
		+ ")" +"VALUES (?,?,?)";
		PreparedStatement prSt=null;
	try {
		prSt =getConnection().prepareStatement(insert);
		prSt.setString(1, team);
		prSt.setString(2, "leer");
		prSt.setString(3, "leer");
		prSt.executeUpdate();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
        if(prSt!=null){
        	try {
				prSt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	
}
/**
 * Erstellen einer Tabelle fuer einen neuen Benutzer
 * @param user - die Benutzername
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
 */
//---------------создать табл новый пользователь
	public static void create_table_user(String user) throws ClassNotFoundException, SQLException {	
		String SQL = "CREATE TABLE "+user+" " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " time VARCHAR(50), " +
                " eventid INTEGER not NULL, " +
                " event VARCHAR (50), " +
                " bet INTEGER not NULL, " +
                " delta VARCHAR (50), " +
                " balance INTEGER not NULL, " +
                " PRIMARY KEY (id))";
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate(SQL);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            if(statement!=null){
                statement.close();
            }
        }

	}
/**
 * Erstellen einer Tabelle zum Ausfuellen von Vorhersagen fuer Match
 * @param matchid - die Spielnummer
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
 */
//---------------создать табл matchforcast
		public static void create_table_forecasteid(String matchid) throws ClassNotFoundException, SQLException {	
			String SQL = "CREATE TABLE IF NOT EXISTS "+matchid+" " +
	                "(id INTEGER not NULL AUTO_INCREMENT, " +
	                " nickname VARCHAR(50), " +
	                " teamwin VARCHAR (50), " +
	                " sum INTEGER not NULL, " +
	                " time VARCHAR(50), " +
	                " PRIMARY KEY (id))";
			Statement statement = null;
			try {
				statement = getConnection().createStatement();
				statement.executeUpdate(SQL);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
	            if(statement!=null){
	                statement.close();
	            }
	        }

		}
/**
 * Speichern der Prognose in einer Tabelle
 * @param tableMatchID - die Name der Spieltabelle
 * @param nickname - die Spitzname
 * @param team - ausgewaehlte Mannschaft
 * @param sum - die Anzahl der Punkte
 * @param dateNow  - die Vorhersagezeit, das Datum in Millisekunden
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
 */
//---------------сохранить ставку в таблтцу матча
		public static void save_predict(String tableMatchID,String nickname, String team, int sum, long dateNow) throws ClassNotFoundException, SQLException {
			String insert ="INSERT INTO " + tableMatchID 
			+ "(" +DB_MySQL_const.NICKNAME_MATCHID 
			+","+ DB_MySQL_const.WINER_MATCHID+","+ DB_MySQL_const.SUM_MATCHID 
			+","+ DB_MySQL_const.DATE_MATCHID 
			+ ")" +"VALUES (?,?,?,?)";
			PreparedStatement prSt=null;
		try {
			prSt =getConnection().prepareStatement(insert);
			prSt.setString(1, nickname);
			prSt.setString(2, team);
			prSt.setLong(3, sum);
			prSt.setLong(4, dateNow);
			prSt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            if(prSt!=null){
            	prSt.close();
            }
        }		
	}	
/**
 * das Speichern der Prognose in der Benutzerereignistabelle		
 * @param tableUSerID - die Name der Benutzertabelle
 * @param time - die Vorhersagezeit, das Datum in Millisekunden
 * @param eventid - die Spielname 
 * @param event - die Ereignisbeschreibung
 * @param bet - die Anzahl der Punkte
 * @param delta - Veraenderung der Balance nach der Prognose
 * @param balance - die Balance
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
 */
//---------------сохранить ставку в историю пользователю
				public static void save_predict_History(String tableUSerID, long time, int eventid, String event, int bet, String delta,int balance) 
						throws ClassNotFoundException, SQLException {		
					String insert ="INSERT INTO " + tableUSerID 
					+ "(" +DB_MySQL_const.TIME_HISTORY 
					+","+ DB_MySQL_const.MATCHID_HISTORY+","+ DB_MySQL_const.EVENT_HISTORY +","+ DB_MySQL_const.BET_HISTORY
					+","+ DB_MySQL_const.DELTA_HISTORY+","+ DB_MySQL_const.BALANCE_HISTORY
					
					+ ")" +"VALUES (?,?,?,?,?,?)";
					PreparedStatement prSt=null;
				try {
					prSt =getConnection().prepareStatement(insert);
					prSt.setLong(1, time);
					prSt.setLong(2, eventid);
					prSt.setString(3, event);
					prSt.setLong(4, bet);
					prSt.setString(5,  delta);
					prSt.setLong(6,  balance);
					prSt.executeUpdate();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
		            if(prSt!=null){
		            	prSt.close();
		            }
		        }		
			}
/**
 * Abrufen der Anzahl der Benutzerpunkte aus der allgemeinen Benutzertabelle		
 * @param nickname - die Spitzname
 * @return - die Anzahl der Benutzerpunkte
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
 */
//запрос в базе найти очки пользователя по нику
public static int getScore(String nickname) throws ClassNotFoundException, SQLException {
	ResultSet resSet = null;
	int score = 0;
	//SELECT * FROM `users` WHERE `nickname` LIKE 'Bon'
	//SELECT * FROM `users` WHERE `nickname` = 'Bon'
	String selekt= "SELECT * FROM "+ DB_MySQL_const.USER_TABLE; 	
	//System.out.println(selekt);
	PreparedStatement prSt=null;
	try {
		prSt =getConnection().prepareStatement(selekt);
		resSet=prSt.executeQuery();
		while(resSet.next()==true) {
			if (resSet.getString(DB_MySQL_const.USER_NICK).equals(nickname)) {
				score=Integer.parseInt(resSet.getString(DB_MySQL_const.USER_SCORE));
			}	
		}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}/*finally {
				if(prSt!=null){
					prSt.close();
				}
			}*/					
	return score;		
	}
/**
 * Abrufen des ID-Benutzers aus der gemeinsamen Benutzertabelle
 * @param nickname - Spitzname des Benutzers 
 * @return - die Nummer in der Tabelle
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Zugriff auf die Datenbank
 */
public static String getUserIDDB(String nickname) throws ClassNotFoundException, SQLException {
		ResultSet resSet = null;
		String id = "";
		//SELECT * FROM `users` WHERE `nickname` LIKE 'Bon'
		//SELECT * FROM `users` WHERE `nickname` = 'Bon'
		String selekt= "SELECT * FROM "+ DB_MySQL_const.USER_TABLE;
		PreparedStatement prSt=null;
		try {
			prSt =getConnection().prepareStatement(selekt);
			resSet=prSt.executeQuery();
			while(resSet.next()==true) {
				if (resSet.getString(DB_MySQL_const.USER_NICK).equals(nickname)) {
					id=resSet.getString(DB_MySQL_const.ID_TABLEUSER);
				}	
			}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}/*finally {
					if(prSt!=null){
						prSt.close();
					}
				}*/					
		return id;
}
/**
 * Abrufen der gesperrte Punkte von Benutzer aus der gemeinsamen Benutzertabelle
 * @param nickname - Spitzname des Benutzers 
 * @return - gesperrte Punkte des Benutzers
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Verbinden mit der Datenbank
 */
public static int getBlockScore(String nickname) throws ClassNotFoundException, SQLException {
	ResultSet resSet = null;
	int score = 0;
	//SELECT * FROM `users` WHERE `nickname` LIKE 'Bon'
	//SELECT * FROM `users` WHERE `nickname` = 'Bon'
	String selekt= "SELECT * FROM "+ DB_MySQL_const.USER_TABLE; 	
	//System.out.println(selekt);
	PreparedStatement prSt=null;
	try {
		prSt =getConnection().prepareStatement(selekt);
		resSet=prSt.executeQuery();
		while(resSet.next()==true) {
			if (resSet.getString(DB_MySQL_const.USER_NICK).equals(nickname)) {
				score=Integer.parseInt(resSet.getString(DB_MySQL_const.USER_BLOCKSCORE));
			}	
		}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}/*finally {
				if(prSt!=null){
					prSt.close();
				}
			}*/					
	return score;		
	}
/**
 * Suche in der ausgewaehlten Tabelle nach der ausgewaehlten Spalte mit dem entsprechenden Wert in der Zeile.
 * ein Wert der gewuenschten Spalte erhalten.
 * @param name_DB - die Tabellename
 * @param collum_id - ausgewaehlte Spalte
 * @param id - entsprechender Wert
 * @param desired_column - gewuenschten Spalte 
 * @return - Wert der gewuenschten Spalte
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Verbinden mit der Datenbank
 */
//запрос в таблицу по 1 столбцу и id строки выдача строки ячейки
public static String find_info_id_collum(String name_DB,String collum_id,String id, String desired_column) throws ClassNotFoundException, SQLException {
	ResultSet resSet = null;
	String result = null;;
	String selekt= "SELECT * FROM "+ name_DB+ " WHERE "+ collum_id + "=?"; //+ DB_MySQL_const.USER_NICK+ "=? AND "
	PreparedStatement prSt=null;		
	try {
		prSt=getConnection().prepareStatement(selekt);
		prSt.setString(1, id);
		resSet=prSt.executeQuery();
		while (resSet.next()==true) {
			if (resSet.getString(collum_id).equals(id)) {
				result=resSet.getString(desired_column);
			}
		}
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}/*finally {
      if(prSt!=null){
    	  prSt.close();
      }
  }*/
	
	return result;		
}
/**
 * Aendern eines Wertes in einem ausgewaehlten Feld
 * @param table -  die Tabellename
 * @param collum - ausgewaehlte Spalte
 * @param idCollum - die Schluesselspalte
 * @param id - der Schluesselwert
 * @param info - neuer Wert
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Verbinden mit der Datenbank
 */
public static void update_cell(String table, String collum,String idCollum, String id ,String info) throws ClassNotFoundException, SQLException {
	Connection con = null;
	try {
		con = DataBaseHandler.getConnection();
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		//System.out.print("UPDATE `"+table+"` SET `"+collum+"` = '"+info+"' WHERE `"+table+"`.`"+idCollum+"` = "+id);
		int rs = con.createStatement().executeUpdate(
				"UPDATE `"+table+"` SET `"+collum+"` = '"+info+"' WHERE `"+table+"`.`"+idCollum+"` = "+id);
		      //"UPDATE `"+"users"+"` SET `"+"eMail"+"` = '"+"11@ya.com"+"' WHERE `"+"users"+"`.`"+"id"+"` = "+"7");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
	      if(con!=null){
	    	  con.close();
	        }
	    }
}
/**
 * Erstellen einer gemeinsamen Tabelle fuer Benutzerdaten
 * @param users - die Tabellename
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Verbinden mit der Datenbank
 */
//---------------создать таблицу для пользователей и очков
public static void create_table_all_users(String users) throws ClassNotFoundException, SQLException {	
	String SQL = "CREATE TABLE IF NOT EXISTS "+users+" " +
          "(id INTEGER not NULL AUTO_INCREMENT, " +
          " nickname VARCHAR(30), " +
          " password VARCHAR(20), " +
          " first_name VARCHAR(30), " +
          " second_name VARCHAR(30), " +
          " email VARCHAR(40), " +
          " score INTEGER(11), " +
          " blockscore INTEGER(11), " +
          " PRIMARY KEY (id))";
	Statement statement = null;
	try {
		statement = getConnection().createStatement();
		statement.executeUpdate(SQL);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
      if(statement!=null){
          statement.close();
      }
  }
}
/**
 * Erstellen einer Tabelle zur manuellen Dateneingabe aus https://dotabuff.com
 * @param dotabaff - die Tabellename
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Verbinden mit der Datenbank
 */
//---------------создать таблица для пользователей и очков
	public static void create_table_dotabaff(String dotabaff) throws ClassNotFoundException, SQLException {	
		String SQL = "CREATE TABLE IF NOT EXISTS "+dotabaff+" " +
				  "(id INTEGER not NULL AUTO_INCREMENT, " +
	              " team VARCHAR(50), " +
	              " link VARCHAR(20), " +
	              " team_dotabuff VARCHAR(30), " +	              
	              " PRIMARY KEY (id))";
			Statement statement = null;
			try {
				statement = getConnection().createStatement();
				statement.executeUpdate(SQL);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
	          if(statement!=null){
	              statement.close();
	          }
	      }
	}
/**
 * Erstellen einer Tabelle zum Speichern von Spielparametern
 * @param dota_match - die Tabellename 
 * @throws ClassNotFoundException - Fehler beim Verbinden mit der Datenbank
 * @throws SQLException - Fehler beim Verbinden mit der Datenbank
 */
//---------------создать таблица для пользователей и очков
public static void create_table_dotamatchs(String dota_match) throws ClassNotFoundException, SQLException {	
		String SQL = "CREATE TABLE IF NOT EXISTS "+dota_match+" " +
              "(id INTEGER not NULL AUTO_INCREMENT, " +
              " time VARCHAR(55), " +
              " tournament VARCHAR(55), " +
              " team1 VARCHAR(55), " +
              " team1_score INTEGER(1), " +
              " versus VARCHAR(5), " +
              " team2 VARCHAR(55), " +
              " team2_score INTEGER(1), " +
              " datatime VARCHAR(15), " +
              " link1 VARCHAR(70), " +
              " link2 VARCHAR(70), " +
              " status INTEGER(1), " +
              " PRIMARY KEY (id))";
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate(SQL);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
          if(statement!=null){
              statement.close();
          }
      }
}
}
