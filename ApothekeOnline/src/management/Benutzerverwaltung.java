/**
 * 
 */
package management;

import java.util.Date;
import java.util.List;
import java.util.Random;

import model.Benutzer;
import model.Kunde;
import model.Mitarbeiter;
import dao.BenutzerDAO;
import dao.DBBenutzerDAO;
import dao.MongoBenutzerDAO;

/**
 * @author Gerhard
 *
 */
public class Benutzerverwaltung {
	private static Benutzerverwaltung benutzerverwaltungInstance=null;
	private BenutzerDAO dao;
	
	/**
	 * Konstruktor der Benutzerverwaltung, hier wird die private Variable vom Typ PersonDAO angelegt.
	 * 
	 */
	private Benutzerverwaltung() {
		//dao = new DBBenutzerDAO();
		dao = new MongoBenutzerDAO();
	}
	
	/**
	 *  Singleton Design-pattern
	 *  
	 * @return Referenz auf das Benutzerverwaltungsobjekt
	 */
	public static Benutzerverwaltung getInstance(){
		if(benutzerverwaltungInstance == null) benutzerverwaltungInstance = new Benutzerverwaltung();
		return benutzerverwaltungInstance;
	}

//zum testen
/*
	public boolean benutzerAnlegen(String vorname, String nachname, String email, String land, int plz, String wohnort, String strasse, int hausNr, String username, String password){//Verbesserungswürdig!!!
		UUID id = UUID.randomUUID();
		System.out.println("Benutzerverwaltung:benutzerAnlegen: "+id+", "+vorname+" "+nachname+", "+email+", "+land+" "+plz+" "+" "+wohnort+" "+strasse+", "+username+" "+password+", anlegen!");
		return dao.speichereBenutzer(new Benutzer(username,id,password,vorname,nachname,email,land,plz,wohnort,strasse,hausNr));
	}
*/
	
	public boolean kundeAnlegen(String vorname, String nachname, String email, String land, int plz, String wohnort, String strasse, int hausNr, String username, String password, String birthday, String sex){//Verbesserungswürdig!!!
		Random randomGenerator = new Random();
		
		String datum = birthday;
		try{
			String[] datumarray = birthday.split("-");
			if(datumarray.length ==1 )datumarray = birthday.split("\\.");
			int jahr = Integer.parseInt(datumarray[0]);
			int monat = Integer.parseInt(datumarray[1]);
			int tag = Integer.parseInt(datumarray[2]);
			
			Date heute = new Date();
			
			if(jahr>2016 || monat>12 || tag>31) throw new Exception();
			
			datum = tag+"-"+monat+"-"+jahr;
			
		}catch(Exception e){
			System.out.println("Benutzerverwaltung: KundeAnlegen: Fehler beim datum");
			return false;
		}
		
		int id = randomGenerator.nextInt(Integer.MAX_VALUE);//nur positive, aber bis maximum
		System.out.println("Benutzerverwaltung:kundeAnlegen: "+id+", "+vorname+" "+nachname+", "+email+", "+land+" "+plz+" "+" "+wohnort+" "+strasse+", "+username+" "+password+",  "+datum+", "+sex+", anlegen!");
		return dao.speichereKunde(new Kunde(username,id,password,vorname,nachname,email,land,plz,wohnort,strasse,hausNr,birthday,sex));
	}
	
	public boolean mitarbeiterAnlegen(String vorname, String nachname, String email, String land, int plz, String wohnort, String strasse, int hausNr, String username, String password, int staffNo, int salary){//Verbesserungswürdig!!!
		Random randomGenerator = new Random();
		int id = randomGenerator.nextInt(Integer.MAX_VALUE);
		System.out.println("Benutzerverwaltung:mitarbeiterAnlegen: "+id+", "+vorname+" "+nachname+", "+email+", "+land+" "+plz+" "+" "+wohnort+" "+strasse+", "+username+" "+password+",  "+staffNo+", "+salary+", anlegen!");
		return dao.speichereMitarbeiter(new Mitarbeiter(username,id,password,vorname,nachname,email,land,plz,wohnort,strasse,hausNr,staffNo,salary));
	}
	
	public boolean pruefeLogin(String username,String password){
		
		try{
			Benutzer p = dao.getBenutzerByUName(username);
		
			System.out.println("Prüfe login von: "+username+", korrektes pwd: "+p.getPassword());
		
			
			if(p.getPassword().equals(password)){
					return true;
			}
			return false;//falls kombi nicht passt
		}catch(NullPointerException e){
			System.out.println("Benutzerverwaltung: Benutzer mit dem Username '"+username+"' nicht gefunden");
			return false;//null, da p null ist und p.getPassword aufgerufen wird(es gibt den benutzer nicht)
		}
	}
	

// nur zum testen
/*
	public boolean benutzerloeschen(String username){//Achtung person nicht benutzer wird gelöscht!!!
		return this.dao.loescheBenutzer(username);
	}
*/

	public List<Benutzer> getAlleBenutzer(){
		return dao.getBenutzerList();
	}
	
	public List<Benutzer> getAlleKunden(){
		return dao.getKundenList();
	}
	
	
	
	public Benutzer getBenutzerByUname(String uName){
		return dao.getBenutzerByUName(uName);
	}
	
	public Kunde getCustomerByUname(String uName){
		return dao.getKundeByUsername(uName);
	}
	
	public Mitarbeiter getEmployeeByUname(String uName){
		return dao.getMitarbeiterByUsername(uName);
	}

	
	
	public boolean loescheKunden(String uName){
		return dao.loescheKundeByUname(uName);
	}
	
	public boolean loescheMitarbeiter(String uName){
		return dao.loescheMitarbeiterByUname(uName);
	}

}