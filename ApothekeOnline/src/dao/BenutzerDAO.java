/**
 * 
 */
package dao;

import java.util.List;

import model.*;

/**
 * @author Gerhard
 * Mithilfe von diesem Interface soll man auf die jeweilige Datenbank zugreifen können. 
 * Die wichtigsten Funktionen werden hier definiert. Mithilfe des Managements wird dann mit diesen
 * "Low-Level" Funktionen umgegangen.
 * 
 * Es ist dabei egal, über welche Datenbank das BenutzerDAO realisiert wird, 
 * wichtig ist hierbei nur, dass diese Methoden implementiert werden.
 */
public interface BenutzerDAO {
	
	/**
	 * Mit dieser Methode kann ein Kunde im System gespeichert werden.
	 * @param k der zu speichernde Kunde
	 * @return falls der Vorgang erfolgreich war, true, ansonsten false
	 */
	public boolean speichereKunde(Kunde k);
	
	
	/**
	 * Mit dieser Methode kann ein Mitarbeiter im System gespeichert werden.
	 * @param m Der zu speichernde Mitarbeiter.
	 * @return falls der Vorgang erfolgreich war, true, ansonsten false
	 */
	public boolean speichereMitarbeiter(Mitarbeiter m);
	
	
	/**
	 * Diese Methode soll alle Kunden, welche im System registriert sind zurückgeben(als Liste)
	 * @return Eine Liste der Kunden(List<Benutzer>)
	 */
	public List<Benutzer> getKundenList();
	
	
	/**
	 * Diese Methode soll alle Mitarbeiter, welche im System registriert sind zurückgeben(als Liste)
	 * @return Eine Liste der Mitarbeiter(List<Benutzer>)
	 */
	public List<Benutzer> getMitarbeiterList();
	
	
	/**
	 * Diese Methode soll alle Benutzer, welche im System registriert sind zurückgeben(als Liste)
	 * @return Eine Liste der Benutzer(List<Benutzer>)
	 */
	public List<Benutzer> getBenutzerList();
	
	
	/**
	 * Mit dieser Methode soll man einen Benutzer anhand seines Usernames bekommen
	 * @param uName Der Username des zu suchenden Benutzers
	 * @return null, falls kein Benutzer mit passendem Username gefunden wurde, ansonsten das BenutzerObjekt
	 */
	public Benutzer getBenutzerByUName(String uName);
	
	
	/**
	 * Mit dieser Methode soll man einen Kunden anhand seines Usernames bekommen
	 * @param uName Der Username des zu suchenden Kunden
	 * @return null, falls kein Kunde mit passendem Username gefunden wurde, ansonsten das BenutzerObjekt
	 */
	public Kunde getKundeByUsername(String uName);
	
	
	/**
	 * Mit dieser Methode soll man einen Mitarbeiter anhand seines Usernames bekommen
	 * @param uName Der Username des zu suchenden Mitarbeiters
	 * @return null, falls kein Mitarbeiter mit passendem Username gefunden wurde, ansonsten das BenutzerObjekt
	 */
	public Mitarbeiter getMitarbeiterByUsername(String uName);

	
	/**
	 * Diese Methode löscht einen Kunden aus dem System. Die Suche erfolgt nach dem eindeutigen Usernamen.
	 * @param uName Der Username des zu suchenden Kunden
	 * @return true, falls der Vorgang erfolgreich war, ansonsten false
	 */
	public boolean loescheKundeByUname(String uName);
	
	
	/**
	 * Diese Methode löscht einen Mitarbeiter aus dem System. Die Suche erfolgt nach dem eindeutigen Usernamen.
	 * @param uName Der Username des zu suchenden Mitarbeiters
	 * @return true, falls der Vorgang erfolgreich war, ansonsten false
	 */
	public boolean loescheMitarbeiterByUname(String uName);		
	
}