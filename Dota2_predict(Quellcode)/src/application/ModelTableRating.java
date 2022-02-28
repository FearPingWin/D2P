package application;

import javafx.scene.control.Button;

/**
 * Parameter zum Fuellen der Ratingstabelle
 */
public class ModelTableRating {
	private String place;
	private String nickname; 
	private String score;
/**
 * Konstrukteur erstellt ein Tabellenmodellobjekt zum Auffuellen der Ratingstabelle		
 * @param place - ordinaler Platz
 * @param nickname - die Spitzname
 * @param score - die Anzahl der Punkten
 */
public ModelTableRating (String place,String nickname, String score/*, Button button*/ ) {
	this.place=place;
	this.nickname= nickname;
	this.score= score;
}
/**
 * ordinaler Platz zu festlegen
 * @param place - ordinaler Platz
 */
public void setPlace (String place) {
	this.place=place;
}
/**
 * ordinaler Platz zu erhalten
 * @return - ordinaler Platz
 */
public String getPlace() {
	return place;
}
/**
 * die Spitzname zu festlegen
 * @param nickname - die Spitzname
 */
public void setNickname(String nickname) {
	this.nickname=nickname;
}
/**
 * die Spitzname zu erhalten
 * @return - die Spitzname
 */
public String getNickname() {
	return nickname;
}
/**
 * die Anzahl der Punkten zu festlegen
 * @param score - die Anzahl der Punkten
 */
public void setScore(String score) {
	this.score=score;
}
/**
 * die Anzahl der Punkten zu erhalten
 * @return - die Anzahl der Punkten
 */
public String getScore() {
	return score;
}

}
