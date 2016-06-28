/**
 * 
 */
package model;


/**
 * @author Gerhard
 *
 */
public class Kunde extends Benutzer {

	private String birthday;
	private char sex;
	
	/**
	 * @param uName Der eindeutige Username des jeweiligen Kunden.
	 * @param usrID Die eindeutige Userid des jeweiligen Kunden.
	 * @param password Das Passwort des jeweiligen Kunden.
	 * @param vorname Der Vorname des jeweiligen Kunden.
	 * @param nachname Der Nachname des jeweiligen Kunden.
	 * @param email Die Email des jeweiligen Kunden.
	 * @param land Das Land in dem der Kunde gemeldet ist.
	 * @param plz Die Postleitzahl in der der Kunde gemeldet ist.
	 * @param ort Der Ort/Stadt in der der Kunde gemeldet ist.
	 * @param strasse Die Strass des Kunden.
	 * @param hausNr Die Hausnummer des Kunden.
	 * @param birthday Der zu speichernde Geburtstag
	 * @param sex Das zu speichernde Geschlecht.
	 */
	public Kunde(String uName, int usrID, String password, String vorname,
			String nachname, String email, String land, int plz, String ort, String strasse,
			int hausNr, String birthday, String sex) {
		super(uName, usrID, password, vorname, nachname, email, land, plz, ort,
				strasse,hausNr);
		this.birthday = birthday;
		this.sex = sex.charAt(0);
		
	}

	
//getters
	/**
	 * @return Der Geburtstag des Kunden
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @return Das Geschlecht des Kunden
	 */
	public char getSex() {
		return sex;
	}

	
//setters
	/**
	 * @param birthday Der zu speichernde Geburtstag
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @param sex Das zu speichernde Geschlecht
	 */
	public void setSex(char sex) {
		this.sex = sex;
	}
	
	/**
	 * Diese Methode überschreibt die Methode der Klasse Object und soll die wichtigsten Daten eines Kunden zurückgeben
	 * @return Der String, welcher die wichtigsten Daten des Kunden enthält.
	 */
	public String toString(){
		String s = super.toString();
		return s+", birthday: "+birthday+", Geschlecht: "+sex;
	}

}
