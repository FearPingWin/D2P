package Server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Zusatzfunktionen fuer die Arbeit mit der Website und das Speichern von Daten in Tabellen.
 *@see DataBaseHandler
 */
public class Parser extends DataBaseHandler{
/** Webseite herunterladen
 * @see Jsoup#parse(String, String)
 * @param url - die Webseitenadressse
 * @return - die Website als Dokument
 * @throws MalformedURLException - die Webadresse mit unbekanntem Protokoll
 * @throws IOException - Fehler beim Lesen oder Speichern von Daten auf der Website
 */
public static Document getPage(String url) throws MalformedURLException, IOException {
	Document page=Jsoup.parse(new URL(url), 15000);
	return page;
}	
/**
 * Aufzeichnung neuer Spiele in einer Tabelle auf https://liquipedia.net
 * @see Parser#find_date_in_columm(String, String, String)
 * @see Parser#edit_cell(String, String, String, String)
 * @see Parser#save_dota_match(String, String, String, String, String, String, String, String)
 * @see DataBaseHandler#closeConnection()
 * @throws Exception - NumberFormatException - Falsche Konvertierung einer Zeichenkette in ein numerisches Format,
 * SQLException - Fehler beim Zugriff auf die Datenbank,
 * ClassNotFoundException - Fehler beim Verbinden mit der Datenbank, 
 * MalformedURLException - die Webadresse mit unbekanntem Protokoll,
 * IOException - Fehler beim Lesen oder Speichern von Daten auf der Website
 */
	public static void getLiquipediaMatchs () throws Exception {
		Document page=getPage("https://liquipedia.net/dota2/Liquipedia:Upcoming_and_ongoing_matches");
		Elements playview=page.select("table[class=wikitable wikitable-striped infobox_matches_content]");
		for (int i=0;i<35;i++) {//playview.size()//51 максимум на странице
			String time=playview.get(i).select("td[class=match-filler]").select("span[class=match-countdown]").toString();
			String timedate=getPatern(time,pattern_datatime);
			// умножить на 1000 однов секунлах другое в мили
			if( System.currentTimeMillis()<Long.valueOf(timedate).longValue()*1000) {
				String team1=playview.get(i).select("td[class=team-left]").text();
				String team2=playview.get(i).select("td[class=team-right]").text();
				String versus=playview.get(i).select("td[class=versus]").select("abbr[title]").text();
				String time_text=playview.get(i).select("td[class=match-filler]").select("span[class=match-countdown]").text();			
				String match=playview.get(i).select("td[class=match-filler]").text();
				String tournament = match.replace (time_text+" ", "");	
				String team2_link=playview.get(i).select("td[class=team-right]").select("span[class=team-template-text]").toString()
						.replace("<span class=\"team-template-text\"><a href=\"", "");
				String team1_link=playview.get(i).select("td[class=team-left]").select("span[class=team-template-text]").toString()
						.replace("<span class=\"team-template-text\"><a href=\"", "");
				String live_score=playview.get(i).select("td[class=versus]").toString();
					//вынести в другую функцию
			/*	if (System.currentTimeMillis()>(Long.valueOf(timedate).longValue()+900)*1000 ) {
					//System.out.println(System.currentTimeMillis());
					//System.out.println(Long.valueOf(timedate).longValue()*1000);
					live_score=getPatern(live_score, patternVersus_2);
					System.out.println(live_score);
				}	*/					
				if (versus.equals("Bo1")||versus.equals("Bo3")||versus.equals("Bo5")  ) {// versus				
				ArrayList <String> list = new ArrayList<>();
		        for (String retval : team2_link.split("\"")) {   
		            list.add(retval);
		        }
		        String team2_link_liqv=list.get(0);
		        ArrayList <String> list2 = new ArrayList<>();
		        for (String retval : team1_link.split("\"")) {   
		            list2.add(retval);
		        }
		        String team1_link_liqv=list2.get(0);
		        if (!team1.equals("TBD")&& (!team2.equals("TBD"))  ) {
			        if((team1_link_liqv.indexOf("redlink=1")==-1) && (team2_link_liqv.indexOf("redlink=1")==-1)  ) {// раньше было или //и без !30.06
			        	//System.out.println(team1_link_liqv +"  "+team2_link_liqv);
			        	if (team1_link_liqv.indexOf("redlink=1")!=-1) {
			        		team1_link_liqv="leer";
			        	}
			        	if (team2_link_liqv.indexOf("redlink=1")!=-1) {
			        		team2_link_liqv="leer";
			        	}
			        	DataBaseHandler dbHandler= new DataBaseHandler();
			        	ResultSet time_tab=find_date_in_columm(DB_MySQL_const.DOTA_MATCH_TABLE, DB_MySQL_const.DATETIME, timedate);
			        	if(time_tab!=null) {//15.08 подумать
				        	//запись команд в таблицу дл€ дотабафа
				        	ResultSet check_team1=find_date_in_columm(DB_MySQL_const.DOTABUFF_TABLE, DB_MySQL_const.TEAM_LIQ, team1);
				        	if (check_team1!=null) {
					        	if (check_team1.next()==false) {
					        		save_dota_team(team1);
					        	}
				        	}
				        	ResultSet check_team2=find_date_in_columm(DB_MySQL_const.DOTABUFF_TABLE, DB_MySQL_const.TEAM_LIQ, team2);
				        	if (check_team2!=null) {
					        	if (check_team2.next()==false) {
					        		save_dota_team(team2);
					        	}
				        	}
				        	// учесть перенос матчей на 30 мин 
				        	if (time_tab.next()==false) {			        		
				        		ResultSet status0=find_date_in_columm(DB_MySQL_const.DOTA_MATCH_TABLE, DB_MySQL_const.STATUS, "0");
				        		int count=-1;
				        		String id ="";
				        		String timeMatchTab ="";
				        		if (status0!=null) {
					        		while(status0.next()==true) {
					        			if(status0.getString(DB_MySQL_const.TEAM_1).equals(team1)
					        				&& status0.getString(DB_MySQL_const.TEAM_2).equals(team2)
					        				&& status0.getString(DB_MySQL_const.TOURNAMENT).equals(tournament)) {
					        				count=1;
					        				id=Integer.toString(status0.getInt(DB_MySQL_const.ID));
					        				timeMatchTab=Integer.toString(status0.getInt(DB_MySQL_const.DATETIME));
					        			}	
					        		}
				        		}
				        		if (count==1) {
				        			if (Integer.parseInt(timeMatchTab)<Integer.parseInt(timedate)) {
					        			System.out.println("vershiebung zeit match"+id);
										edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE, id, DB_MySQL_const.DATETIME, timedate);	
				        			}
				        		}else {
				        			System.out.println("save novi matc");
						        		dbHandler.save_dota_match(time_text, tournament, team1, versus, team2, timedate,team1_link_liqv, team2_link_liqv );
				        		}
				        	}			        	
				        	else {
			        			time_tab=find_date_in_columm(DB_MySQL_const.DOTA_MATCH_TABLE, DB_MySQL_const.DATETIME, timedate);
			        			int count =-1;
			        			if (time_tab!=null) {
				        			while(time_tab.next()==true) {			        			
							        	if ( (time_tab.getString(DB_MySQL_const.TEAM_1).equals(team1)==true) 	
								        	 &&(time_tab.getString(DB_MySQL_const.TEAM_2).equals(team2)==true))	{
						        			count =10;
						        			break;
								        	}	
					        		}
			        			}
			        			if (count<0) {	
				        			dbHandler.save_dota_match(time_text, tournament, team1, versus, team2, timedate,team1_link_liqv, team2_link_liqv );
				        			System.out.println("save new match same time");
			        			}
				        	}
			        	}//!=
			        //	dbHandler.closeConnection();
			        }//redlink
		        }//TBD
			}
		}
		}//versus		
	}
	/**
	 * ein Muster fuer die Suche nach einem Ergebnis des Spiels
	 */
	private static Pattern patternVersus =Pattern.compile("\\d{1}\\s:\s\\d{1}");
	/**
	 * ein Muster fuer die Suche nach einem Ergebnis des Spiels
	 */
	private static Pattern patternVersus_2 =Pattern.compile("\\d{1}:\\d{1}");
	/**
	 * ein Muster fuer die Suche nach der Spielzeit
	 */
	private static Pattern pattern_datatime =Pattern.compile("\\d{10}");
/**
 * Suche nach einem Muster in einer angegebenen Zeichenfolge
 * @param string - gegebene Zeichenfolge
 * @param patern - voreingestelltes Muster
 * @return - gefundenes Muster
 * @throws Exception - Zeichenkette kann nicht extrahiert werden 
 */
	public static String getPatern (String string, Pattern patern) throws Exception {
		Matcher matcher=patern.matcher(string);
		if (matcher.find()) {
			return matcher.group();
		}
		throw new Exception ("Cann`t extrate string ") ;	
	}	
/**
 * Pruefung, ob das Spiel zu Ende ist und der Spielstand,
 * speichert die Daten in einer Spieltabelle.
 * @see Parser#find_date_in_columm(String, String, String)
 * @see DataBaseHandler#getConnection()
 * @see DataBaseHandler#closeConnection()
 * @throws Exception - SQLException - Fehler beim Zugriff auf die Datenbank,
 * ClassNotFoundException - Fehler beim Verbinden mit der Datenbank, 
 * MalformedURLException - die Webadresse mit unbekanntem Protokoll,
 * IOException - Fehler beim Lesen oder Speichern von Daten auf der Website
 */
	// result vergangen Match --------------------------------------------------------------------------------------	
	public static void chek_complited_match() throws Exception {
    	//DataBaseHandler dbHandler= new DataBaseHandler();
    	//dbHandler.getConnection();
    	ResultSet time_tab=find_date_in_columm(DB_MySQL_const.DOTA_MATCH_TABLE, DB_MySQL_const.STATUS, "1");
    	if (time_tab!=null) {
        	while (time_tab.next()==true) {
        		ResultSet buff_tab=find_date_in_columm(DB_MySQL_const.DOTABUFF_TABLE, DB_MySQL_const.TEAM_LIQ, time_tab.getString(DB_MySQL_const.TEAM_1));
        		ResultSet buff_tab2=find_date_in_columm(DB_MySQL_const.DOTABUFF_TABLE, DB_MySQL_const.TEAM_LIQ, time_tab.getString(DB_MySQL_const.TEAM_2));
        		if (buff_tab.next()==true &&  buff_tab2.next()==true 
        				&& buff_tab!=null && buff_tab2!=null ) {//нуэна ли эта проверка 12.08
        			String link=buff_tab.getString(DB_MySQL_const.BUFF_LINK);
        			String link2=buff_tab2.getString(DB_MySQL_const.BUFF_LINK);
            		String match_result=result_match_dotabuf(link);
            		String match_result2=result_match_dotabuf(link2);
            		if (match_result.equals(match_result2)) {
                		//System.out.println(buff_tab.getString(DB_MySQL_const.TEAM_BUFF)+ "==="+ buff_tab2.getString(DB_MySQL_const.TEAM_BUFF));
                		//кака€ команда победила
                		//int dead_heat=match_result.indexOf("Tied");       		       		
                		if (match_result.equals("TBA")==false) {
                			String new_score_1 = null,new_score_2 = null;
                    		int win_team1=match_result.indexOf(buff_tab.getString(DB_MySQL_const.TEAM_BUFF));
                    		int win_team2=match_result.indexOf(buff_tab2.getString(DB_MySQL_const.TEAM_BUFF));
                    		if (match_result.indexOf("Tied")>-1) {                			
                   			 	new_score_1=String.valueOf("1") ;
                   			 	new_score_2=String.valueOf("1") ;
                    		}
                    		if (win_team1>-1 ||win_team2>-1) {
                        		// проверить наличие \тих 2 команд в последней игре на дота бафф
        	
                        		if (win_team1>-1) {
                        			 new_score_1=String.valueOf(match_result.charAt(0)) ;
                            		 new_score_2=String.valueOf(match_result.charAt(4)) ;	
                        		}else {
                        			new_score_2=String.valueOf(match_result.charAt(0)) ;
                           		 	new_score_1=String.valueOf(match_result.charAt(4)) ;	
                        		}
                    		}
                    		//мен€ем счет, ставим значение статус 2  
                    		if (new_score_1!=null && new_score_2!=null) { // 06.08.21
                        		edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,time_tab.getString("id"),DB_MySQL_const.TEAM_1_SCORE,new_score_1);
                        		edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,time_tab.getString("id"),DB_MySQL_const.TEAM_2_SCORE,new_score_2);
                        		edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,time_tab.getString("id"),DB_MySQL_const.STATUS,"2");	
                    		}           		
                		}	
            		}
        	}
    	}
    	}	
    	//dbHandler.closeConnection();   	
}	
/**
 * Ueberpruefung des Ergebnises des Spiels aus dotabuff.com
 * @param url - Teil vom Link der Mannschaft aus  dotabuff.com
 * @return - das Ergebnises des Spiels
 * @throws Exception - MalformedURLException - die Webadresse mit unbekanntem Protokoll,
 * IOException - Fehler beim Lesen oder Speichern von Daten auf der Website
 */
	// result vergangen Match --------------------------------------------------------------------------------------
	public static String result_match_dotabuf (String url ) throws Exception {
		String result="TBA";
		if (!url.equals("leer")) {
			String url_part1="https://www.dotabuff.com/esports/teams/";
			String full_url=url_part1+url;
			Document page=getPage(full_url);
			Element playview=page.select("td[class=winner series-winner]").first();
			Elements playview2=page.select("td[class=winner series-winner]");	
			//System.out.println(playview2.get(1).text());
			//<td class="r-none-mobile series-status-2"><div>Completed</div><div><small><time datetime="2021-05-01T05:44:58+00:00" title="Sat, 01 May 2021 05:44:58 +0000" data-time-ago="2021-05-01T05:44:58+00:00">3 days ago</time></small></div><div><small></small></div></td>
			//Element time=page.select("td[class=r-none-mobile series-status-2]").first();
			//System.out.println(playview.text());
			result=playview.text();	
		}
		return result;
	}
/** Beobachtung der Ergebnisse der laufenden Spiele
 * @see Parser#find_date_in_columm(String, String, String)
 * @see Parser#edit_cell(String, String, String, String)
 * @see DataBaseHandler#getConnection()
 * @see DataBaseHandler#closeConnection()
 * @throws Exception -
 * NumberFormatException - Falsche Konvertierung einer Zeichenkette in ein numerisches Format,
 * SQLException - Fehler beim Zugriff auf die Datenbank,
 * ClassNotFoundException - Fehler beim Verbinden mit der Datenbank, 
 * MalformedURLException - die Webadresse mit unbekanntem Protokoll,
 * IOException - Fehler beim Lesen oder Speichern von Daten auf der Website
 */
	// result live Match --------------------------------------------------------------------------------------	
	public static void live_match_result() throws Exception {
    	//DataBaseHandler dbHandler= new DataBaseHandler();22.08
    	//dbHandler.getConnection();
		ResultSet time_start=find_date_in_columm(DB_MySQL_const.DOTA_MATCH_TABLE, DB_MySQL_const.STATUS, "0");
		if (time_start!=null) {
			while(time_start.next()) {			
				String timedate_start=time_start.getString(DB_MySQL_const.DATETIME);
				long time=(Long.valueOf(timedate_start).longValue()+900); 
				if (time<System.currentTimeMillis() /1000 ) {
					edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,time_start.getString("id"),DB_MySQL_const.STATUS,"1");
				}
			}
		}
		try {
			Document page=getPage("https://liquipedia.net/dota2/Liquipedia:Upcoming_and_ongoing_matches");
			Elements playview=page.select("table[class=wikitable wikitable-striped infobox_matches_content]");
			for (int i=0;i<10;i++) {//playview.size()//10 максимум на странице
				String time=playview.get(i).select("td[class=match-filler]").select("span[class=match-countdown]").toString();
				String timedate=getPatern(time,pattern_datatime);
				if(System.currentTimeMillis()>(Long.valueOf(timedate).longValue()+900)*1000 ) {
					//System.out.println("live match");
					String team1=playview.get(i).select("td[class=team-left]").text();
					String team2=playview.get(i).select("td[class=team-right]").text();
					String live_score=playview.get(i).select("td[class=versus]").toString();
						//System.out.println(System.currentTimeMillis());
						//System.out.println(Long.valueOf(timedate).longValue()*1000);
					live_score=getPatern(live_score, patternVersus_2);
					//System.out.println(live_score);
					//int summ=	Character.getNumericValue(live_score.charAt(0))+Character.getNumericValue(live_score.charAt(2));
					//не обновл€ет матч после первой карты подумать вз€ть 0 и 1 возможно ли сложить 2 результат???
					
				    ResultSet time_tab=find_date_in_columm(DB_MySQL_const.DOTA_MATCH_TABLE, DB_MySQL_const.STATUS, "1");
				    if (time_tab!=null) {
					    while (time_tab.next()==true) {
					    	if (time_tab.getString(DB_MySQL_const.TEAM_1).equals(team1)==true && time_tab.getString(DB_MySQL_const.TEAM_2).equals(team2)==true){
						    	if (time_tab.getString(DB_MySQL_const.TEAM_1_SCORE).equals(live_score.charAt(0))==false) {
						    		String new_score_1=String.valueOf(live_score.charAt(0)) ;
						    		edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,time_tab.getString("id"),DB_MySQL_const.TEAM_1_SCORE,new_score_1);
						    	}
						    	if ( time_tab.getString(DB_MySQL_const.TEAM_2_SCORE).equals(live_score.charAt(2))==false) {
						    		String new_score_2=String.valueOf(live_score.charAt(2)) ;
						    		edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,time_tab.getString("id"),DB_MySQL_const.TEAM_2_SCORE,new_score_2);
						    	}	
						    	// мен€ем статус на 1, последн€€ карта возможно
						    /*	 if (summ>=1) {
					    			 edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,time_tab.getString("id"),DB_MySQL_const.STATUS,"1");
					    		 }*/
					    	}
					    }
				    }     					        
				}
			}			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	dbHandler.closeConnection(); 22.08	
	}
/**
 * Pruefung, ob es die Prognose gab, ja- setzen den Status auf 4, nein -5.	
 * @see DataBaseHandler#getConnection()
 * @see DataBaseHandler#find_date_in_columm(String, String, String)
 * @see DataBaseHandler#edit_cell(String, String, String, String)
 */
// матч получает статус 5 который завершилс€ и не было ставок --------------------------------------------------------------------------------------	
public static void checkPrediktMatch() {
	try {
		ResultSet status2=find_date_in_columm(DB_MySQL_const.DOTA_MATCH_TABLE, DB_MySQL_const.STATUS, "2");
		if (status2!=null) {
			ResultSet rs = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM information_schema.tables where TABLE_TYPE = 'BASE TABLE'");
			while(status2.next()) {
			String id= status2.getString(DB_MySQL_const.ID);
			rs.beforeFirst();
			int S=0;
			while (rs.next()==true) {
				if (rs.getString("TABLE_NAME").equals("match"+status2.getString(DB_MySQL_const.ID))) {
					S=1;					
				}	
			}
			//если матч окончен и нет предиктов значение в статусе мен€ем на 5 если есть то статус 4
			if (S==0) {
				edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,status2.getString("id"),DB_MySQL_const.STATUS,"5");	
			}
			if (S==1) {
				edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,status2.getString("id"),DB_MySQL_const.STATUS,"4");	
			}
		}	
		}

	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}
}
/**
 * Wenn der Status 4 ist, die Punkte von Gewinner und Verlierer  berechnet werden.
 * @see DataBaseHandler#getConnection()
 * @see DataBaseHandler#find_date_in_columm(String, String, String)
 * @see DataBaseHandler#edit_cell(String, String, String, String)
 */
	// если статус 4 то провер€м таблицу со ставками и оплачиваем каждый бет в аккаунт и общую табдицу мен€ем заиороженные очки и общие
public static void payBets() {
	try {
		ResultSet status4=find_date_in_columm(DB_MySQL_const.DOTA_MATCH_TABLE, DB_MySQL_const.STATUS, "4");
		if (status4!=null) {
			while(status4.next()==true) {
				int id=status4.getInt(DB_MySQL_const.ID);
				String teamwiner="draw";
				if (status4.getInt(DB_MySQL_const.TEAM_1_SCORE)>status4.getInt(DB_MySQL_const.TEAM_2_SCORE)) {
					teamwiner=status4.getString(DB_MySQL_const.TEAM_1);
				}
				if (status4.getInt(DB_MySQL_const.TEAM_1_SCORE)<status4.getInt(DB_MySQL_const.TEAM_2_SCORE)) {
					teamwiner=status4.getString(DB_MySQL_const.TEAM_2);
				}
				ResultSet tableForcast=	getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
						.executeQuery("SELECT * FROM match"+id);
				//расчет коэффициента
				double Sum=0;
				double Sum_team1=0;
				while (tableForcast.next()==true) {
					Sum=Sum + tableForcast.getInt(DB_MySQL_const.SUM_MATCHID);
					if (tableForcast.getString(DB_MySQL_const.WINER_MATCHID).equals(status4.getString(DB_MySQL_const.TEAM_1))) {
						Sum_team1=Sum_team1+tableForcast.getInt(DB_MySQL_const.SUM_MATCHID);
					}
				}
				//==============================================================
				tableForcast.beforeFirst();
				while (tableForcast.next()==true) {
					int info=0;
					int bet=tableForcast.getInt(DB_MySQL_const.SUM_MATCHID);
					String nick=tableForcast.getString(DB_MySQL_const.NICKNAME_MATCHID);
					String time=tableForcast.getString(DB_MySQL_const.DATE_MATCHID);
					String winPredickt=tableForcast.getString(DB_MySQL_const.WINER_MATCHID);

					if(teamwiner.equals(winPredickt)&& teamwiner.equals(status4.getString(DB_MySQL_const.TEAM_1)) ) {
						if (Sum_team1!=0) {
							info=(int) Math.round(bet*(Sum/Sum_team1-1));
						}
					}
					if(!teamwiner.equals(winPredickt)&& teamwiner.equals(status4.getString(DB_MySQL_const.TEAM_1)) ) {
						info=bet*(-1);//(int) Math.round(bet*(Sum/Sum_team1-1))*(-1);
						//проишрыш - бет
					}
					if(teamwiner.equals(winPredickt)&& teamwiner.equals(status4.getString(DB_MySQL_const.TEAM_2)) ) {
						if ((Sum-Sum_team1)!=0) {
							info=(int) Math.round(bet*(Sum/(Sum-Sum_team1)-1));
						}					
					}
					if(!teamwiner.equals(winPredickt)&& teamwiner.equals(status4.getString(DB_MySQL_const.TEAM_2)) ) {
						info=bet*(-1);//(int) Math.round(bet*(Sum/(Sum-Sum_team1)-1))*(-1);
						//проишрыш - бет
					}
					//SELECT * FROM `aa` WHERE `time` LIKE '1624350427812'
					ResultSet tableUser=getConnection().createStatement().executeQuery
							("SELECT * FROM "+nick+" WHERE `"+DB_MySQL_const.DATE_MATCHID+"` LIKE '"+time+"'");
					tableUser.next();
					int id_collum=tableUser.getInt(DB_MySQL_const.ID_HISTORY);
							// мен€ем €чейки 1. делта, 2 №аланс 3 блок поинтс
					if (tableUser.getString(DB_MySQL_const.DELTA_HISTORY).equals("expect")) {
						// протестить 1
						String info_delta="";
						if (info>0) {
							info_delta="+"+Integer.toString(info);
						}else {
							info_delta=Integer.toString(info);
						}
						update_cell(nick, DB_MySQL_const.DELTA_HISTORY,DB_MySQL_const.ID_HISTORY, Integer.toString(id_collum) ,info_delta);
						String UserIDDB=getUserIDDB(nick);
						edit_cell(DB_MySQL_const.USER_TABLE, UserIDDB, DB_MySQL_const.USER_BLOCKSCORE, 
								Integer.toString (getBlockScore(nick)-bet));				
						edit_cell(DB_MySQL_const.USER_TABLE, UserIDDB, DB_MySQL_const.USER_SCORE, 
								Integer.toString (getScore(nick)+info));
					}	
				}
			edit_cell(DB_MySQL_const.DOTA_MATCH_TABLE,Integer.toString(id),DB_MySQL_const.STATUS,"5");	
			} 
		}
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}
}	
}
