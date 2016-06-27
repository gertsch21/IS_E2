/**
 * 
 */
package model;


/**
 * 
 * @author Gerhard
 * Diese Klasse symbolisiert die Mitarbeiter, welche aber auch Benutzer vom System sind.
 */
public class Mitarbeiter extends Benutzer {

	private int staffNo;
	private int sallary; //in cent
	
	
	/**
	 * 
	 * @param uName Der eindeutige Username des jeweiligen Mitarbeiters.
	 * @param usrID Die eindeutige Userid des jeweiligen Mitarbeiters.
	 * @param password Das Passwort des jeweiligen Mitarbeiters.
	 * @param vorname Der Vorname des jeweiligen Mitarbeiters.
	 * @param nachname Der Nachname des jeweiligen Mitarbeiters.
	 * @param email Die Email des jeweiligen Mitarbeiters.
	 * @param land Das Land in dem der Mitarbeiter gemeldet ist.
	 * @param plz Die Postleitzahl in der der Mitarbeiter gemeldet ist.
	 * @param ort Der Ort/Stadt in der der Mitarbeiter gemeldet ist.
	 * @param strasse Die Strass des Mitarbeiters.
	 * @param hausNr Die Hausnummer des Mitarbeiters.
	 * @param staffNo Die Mitarbeiternummer des Mitarbeiters.
	 * @param sallary Das Gehalt, welches der Kunde bekommt.(in Cent gespeichert)
	 */
	public Mitarbeiter(String uName, int usrID, String password,
			String vorname, String nachname, String email, String land,
			int plz, String ort, String strasse, int hausNr, int staffNo, int sallary) {
		super(uName, usrID, password, vorname, nachname, email, land, plz, ort,
				strasse, hausNr);
		
		this.staffNo = staffNo;
		this.sallary = sallary;
		
	}

	
//getters
	/**
	 * @return Die Kundennummer
	 */
	public int getStaffNo() {
		return staffNo;
	}

	/**
	 * @return Das Gehalt
	 */
	public int getSallary() {
		return sallary;
	}

	
//setters
	/**
	 * @param staffNo Die zu speichernde Kundennummer
	 */
	public void setStaffNo(int staffNo) {
		this.staffNo = staffNo;
	}

	/**
	 * @param sallary Das zu speichernde Gehalt
	 */
	public void setSallary(int sallary) {
		this.sallary = sallary;
	}

	/**
	 * Diese Methode überschreibt die Methode der Klasse Object und soll die wichtigsten Daten eines Mitarbeiters zurückgeben
	 * @return Der String, welcher die wichtigsten Daten des Mitarbeiters enthält.
	 */
	public String toString(){
		String s = super.toString();
		return s+", sallary: "+sallary+", StaffNo: "+staffNo;
	}
	
}
