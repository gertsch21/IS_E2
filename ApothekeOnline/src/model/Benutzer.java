package model;


/**
 * @author Gerhard
 *
 */
public class Benutzer {
	private String uName;
	private int usrID;
	private String password;
	private String vorname;
	private String nachname;
	private String email;
	private String land;
	private int plz;
	private String ort;
	private String strasse;
	private int hausNr;
	
	
	
	/**
	 * @param uName Der eindeutige Username des jeweiligen Benutzers.
	 * @param usrID Die eindeutige Userid des jeweiligen Benutzers.
	 * @param password Das Passwort des jeweiligen Benutzers.
	 * @param vorname Der Vorname des jeweiligen Benutzers.
	 * @param nachname Der Nachname des jeweiligen Benutzers.
	 * @param email Die Email des jeweiligen Benutzers.
	 * @param land Das Land in dem der Benutzer gemeldet ist.
	 * @param plz Die Postleitzahl in der der Benutzer gemeldet ist.
	 * @param ort Der Ort/Stadt in der der Benutzer gemeldet ist.
	 * @param strasse Die Strass des Benutzers.
	 * @param hausNr Die Hausnummer des Kunden.
	 */
	public Benutzer(String uName, int usrID, String password, String vorname,
			String nachname, String email, String land, int plz, String ort, String strasse,
			int hausNr) {
		super();
		this.uName = uName;
		this.usrID = usrID;
		this.password = password;
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
		this.land = land;
		this.plz = plz;
		this.ort = ort;
		this.strasse = strasse;
		this.hausNr = hausNr;
	}
	
	/**
	 * Diese Methode überschreibt die Methode der Klasse Object und soll die wichtigsten Daten eines Benutzers zurückgeben
	 * @return Der String, welcher die wichtigsten Daten des Benutzers enthält.
	 */
	public String toString(){
		String s = "Uname: "+uName+", Name: "+vorname+" "+nachname+", email: "+email;
		return s;
	}
	
	
	
//getters	
	/**
	 * @return der Username
	 */
	public String getuName() {
		return uName;
	}
	/**
	 * @return die Userid
	 */
	public int getUsrID() {
		return usrID;
	}
	/**
	 * @return Das Passwort
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return der Vorname
	 */
	public String getVorname() {
		return vorname;
	}
	/**
	 * @return Der Nachname
	 */
	public String getNachname() {
		return nachname;
	}
	/**
	 * @return Die Email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return Das Land
	 */
	public String getLand() {
		return land;
	}
	/**
	 * @return Die plz
	 */
	public int getPlz() {
		return plz;
	}
	/**
	 * @return Der Ort
	 */
	public String getOrt() {
		return ort;
	}
	/**
	 * @return Die HausNr
	 */
	public int getHausNr() {
		return hausNr;
	}
	/**
	 * @return Die Strasse
	 */
	public String getStrasse() {
		return strasse;
	}

	

//setters

	/**
	 * @param uName Der zu speichernde uName
	 */
	public void setuName(String uName) {
		this.uName = uName;
	}
	/**
	 * @param Die zu speichernde ID
	 */
	public void setUsrID(int usrID) {
		this.usrID = usrID;
	}
	/**
	 * @param password Das zu speichernde Passwort
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param vorname Der zu speichernde Vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	/**
	 * @param nachname Der zu speichernde Nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	/**
	 * @param email Die zu speichernde Email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @param land Das zu speichernde Land
	 */
	public void setLand(String land) {
		this.land = land;
	}
	/**
	 * @param plz Die zu speichernde PLZ
	 */
	public void setPlz(int plz) {
		this.plz = plz;
	}
	/**
	 * @param ort Der zu speichernde Ort
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}
	/**
	 * @param hausNr Die zu speichernde Hausnummer
	 */
	public void setHausNr(int hausNr) {
		this.hausNr = hausNr;
	}
	/**
	 * @param strasse Die zu speichernde Strasse
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	
}
