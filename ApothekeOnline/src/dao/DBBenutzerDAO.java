/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;

/**
 * @author Gerhard
 *
 * Diese Klasse ist dafür zuständig, die Daten aus der Datenbank(Oracle) zu holen und diese Managementklassen zu übergeben
 */
public class DBBenutzerDAO implements BenutzerDAO {
//Daten für DB Verbindung
	private final String dbUrl = "jdbc:oracle:thin:@oracle-lab.cs.univie.ac.at:1521:lab";
	private final String user = "a1363772";
	private final String pwd = "PRise16";
	
//Statements um Daten aus db zu holen(über java.sql.*)
	private PreparedStatement saveKundeStmt;
	private PreparedStatement saveMitarStmt;
	private PreparedStatement saveUserStmt;
	private PreparedStatement loadKundeStmt;
	private PreparedStatement loadMitarStmt;
	private PreparedStatement loadUserStmt;
	private PreparedStatement loadUserStmtID;
	private PreparedStatement loadAllUserStmt;
	private PreparedStatement loadAllKundeStmt;
	private PreparedStatement loadAllMitarbeiterStmt;
	
	private PreparedStatement deleteKundeStmt;
	private PreparedStatement deleteMitarStmt;
	private PreparedStatement deleteUserStmt;
	
	

	/**
	 * Im Konstruktor wird die Verbindung zur Datenbank hergestellt
	 * Es werden auch die Statements definiert, mit welchen man die Daten aus der Datenbank bekommt
	 * Achtung, die User werden aus der Datenbank mit dem Usernamen herausgeholt, wohingegen es beim Kunden und Mitarbeiter die UserID ist
	 */
	public DBBenutzerDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(dbUrl, user, pwd);
			
			saveUserStmt = con
					.prepareStatement("INSERT INTO ISE_USR(UNAME,FIRSTNAME,SURNAME,EMAIL,PWD,STREET,CITY,COUNTRY,ZIP,NUMBR) VALUES(?,?,?,?,?,?,?,?,?,?)");
			saveKundeStmt = con
					.prepareStatement("INSERT INTO ISE_CUSTOMER(USRID,BIRTHDAY,SEX) VALUES (?, ?, ?)");
			saveMitarStmt = con
					.prepareStatement("INSERT INTO ISE_EMPLOYEE(usrID,STAFF_NO,SALARY) VALUES (?, ?, ?)");
			
			loadUserStmtID = con.prepareStatement("SELECT * FROM ISE_USR WHERE USRID=?");
			loadUserStmt = con.prepareStatement("SELECT * FROM ISE_USR WHERE UNAME=?");
			loadKundeStmt = con.prepareStatement("SELECT * FROM ISE_CUSTOMER WHERE USRID=?");
			loadMitarStmt = con.prepareStatement("SELECT * FROM ISE_EMPLOYEE WHERE USRID=?");
			
			loadAllUserStmt = con.prepareStatement("SELECT * FROM ISE_USR");
			loadAllKundeStmt = con.prepareStatement("SELECT * FROM ISE_CUSTOMER");
			loadAllMitarbeiterStmt = con.prepareStatement("SELECT * FROM ISE_EMPLOYEE");
			
			deleteUserStmt = con.prepareStatement("DELETE FROM ISE_USR WHERE USRID=?");
			deleteKundeStmt = con.prepareStatement("DELETE FROM ISE_CUSTOMER WHERE USRID=?");
			deleteMitarStmt = con.prepareStatement("DELETE FROM ISE_EMPLOYEE WHERE USRID=?");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Verbindungsaufbau zur DB nicht möglich!! ("+e.getMessage()+")");
		}

	}

	/**
	 * 
	 * zum speichern in der Tabelle Benutzer, benötigt, um Kunden und Mitarbeiter zu speichern
	 */
	private boolean speichereBenutzer(Benutzer b) {
		try {
			System.out.println("DBBenutzerDB:speichereBenutzer: " + b.getuName() + ", " + b.getNachname());

			//saveUserStmt.setInt(1, b.getUsrID());
			saveUserStmt.setString(1, b.getuName());
			saveUserStmt.setString(2, b.getVorname());
			saveUserStmt.setString(3, b.getNachname());
			saveUserStmt.setString(4, b.getEmail());
			saveUserStmt.setString(5, b.getPassword());
			saveUserStmt.setString(6, b.getStrasse());
			saveUserStmt.setString(7, b.getOrt());
			saveUserStmt.setString(8, b.getLand());
			saveUserStmt.setInt(9, b.getPlz());
			saveUserStmt.setInt(10, b.getHausNr());
			
			
			saveUserStmt.executeUpdate();
			return true;
		}catch(NullPointerException e){
			System.out.println("DBBenutzerDB:speichereBenutzer: Übergebener Benutzer(Parameter) ist null!!! ("+e.getMessage()+")");
			return false;
		}catch (Exception e) {
			System.out.println("DBBenutzerDB:speichereBenutzer: Fehler beim einfuegen des Benutzers zBSchon vorhanden,...("+e.getMessage()+")!!!");
			return false;
		}
	}

	@Override
	public boolean speichereKunde(Kunde k) {
		if(!speichereBenutzer(k))//Kunde bereits eingefügt, oder null übergeben,....
			return false;
		
		try {
			//Automatisch generierte UID von DB holen
			loadUserStmt.setString(1,k.getuName());
			ResultSet rs = loadUserStmt.executeQuery();
			if (!rs.next()){
				System.out.println("DBBenutzerDAO: getBenutzerByUName: Kein Benutzer gefunden!");
				return false;
			}
			int usrID = rs.getInt("USRID");
		
	
			
			System.out.println("DBBenutzerDB:speichereKunden: ID:" + usrID
					+ ", " + rs.getString("UNAME") + ", " + k.getNachname()+", Number: "+rs.getString("USRID"));

			saveKundeStmt.setInt(1, usrID);
			saveKundeStmt.setString(2, k.getBirthday());
			saveKundeStmt.setString(3, String.valueOf(k.getSex()));
			
			
			saveKundeStmt.executeUpdate();
			return true;
		}catch(NullPointerException e){
			System.out.println("DBBenutzerDB:speichereBenutzer: Übergebener Benutzer(Parameter) ist null!!!");
			return false;
		}catch (Exception e) {
			System.out.println("DBBenutzerDB:speichereBenutzer: Fehler beim einfuegen des Benutzers zBSchon vorhanden,...("+e.getMessage()+")!!!");
			return false;
		}

	}

	@Override
	public boolean speichereMitarbeiter(Mitarbeiter m) {
		if(!speichereBenutzer(m))
			return false;
		
		try {
			//Automatisch generierte UID von DB holen
			loadUserStmt.setString(1,m.getuName());
			ResultSet rs = loadUserStmt.executeQuery();
			if (!rs.next()){
				System.out.println("DBBenutzerDAO: getBenutzerByUName: Kein Benutzer gefunden!");
				return false;
			}
			int usrID = rs.getInt("USRID");
		
			System.out.println("DBBenutzerDB: speichereMitarbeiter: " + usrID
					+ ", " + m.getuName() + ", " + m.getNachname());

			saveMitarStmt.setInt(1, usrID);
			saveMitarStmt.setString(2, String.valueOf(m.getStaffNo()) );
			saveMitarStmt.setString(3, String.valueOf(m.getSallary()) );
			
			
			saveMitarStmt.executeUpdate();
			return true;
		}catch(NullPointerException e){
			System.out.println("DBBenutzerDB:speichereMitarbeiter: Übergebener Benutzer(Parameter) ist null!!!");
			return false;
		}catch (Exception e) {
			System.out.println("DBBenutzerDB:speichereMitarbeiter: Fehler beim einfuegen des Benutzers zBSchon vorhanden,...("+e.getMessage()+")!!!");
			return false;
		}
	}
	

	@Override
	public List<Benutzer> getBenutzerList() {
		List<Benutzer> liste = new ArrayList<Benutzer>();
		try {
			ResultSet result = loadAllUserStmt.executeQuery();
			int anzBenutzer = 0;
			
			while(result.next()){
				String uName = result.getString("UNAME");
				String firstname = result.getString("FIRSTNAME");
				String surname = result.getString("SURNAME");
				String email = result.getString("EMAIL");
				String pwd = result.getString("PWD");
				String country = result.getString("COUNTRY");
				String street = result.getString("STREET");
				String city = result.getString("CITY");
				try {
					int uID = Integer.parseInt(result.getString("USRID"));
					int zip = Integer.parseInt(result.getString("ZIP"));
					int num = Integer.parseInt(result.getString("NUMBR"));
					
					liste.add(new Benutzer(uName, uID, pwd, firstname, surname, email,
							country, zip, city, street, num));
					anzBenutzer++;
				} catch (NumberFormatException e) {
					System.out.println("DBBenutzerDao: getBenutzerList: Error beim Parsen des Strings in der DB in int wert");
					return null;
				}catch(Exception e){
					System.out.println("DBBenutzerDao: getBenutzerList: Error beim Parsen: "+e.getMessage());
					return null;
				}	
			}
			
			System.out.println("DBBenutzerDao: getBenutzerList: Anzahl Benutzer: " + anzBenutzer);

			return liste;
			
		} catch (Exception e) {
			System.out.println("DBBenutzerDAO: getBenutzerListe: Error");
			return null;
		}
	}

	
	
	
	@Override
	public List<Benutzer> getKundenList() {
		List<String> alleUsrIDKunden = new ArrayList<String>();
		List<Benutzer> benutzerAlsKundeListe = new ArrayList<Benutzer>();
		List<String> alleUsrNamesKunden = new ArrayList<String>();
		
		try {
			ResultSet result = loadAllKundeStmt.executeQuery();
			
			while(result.next()){
				String usrID = result.getString("USRID");
				alleUsrIDKunden.add(usrID);
			}
			
			
			for(String usrID : alleUsrIDKunden){
				alleUsrNamesKunden.add(getBenutzerByUsrID(usrID).getuName() );
				
			}
			
			for(String uName : alleUsrNamesKunden){
				benutzerAlsKundeListe.add(getKundeByUsername(uName));
			}
			
			return benutzerAlsKundeListe;
			
		} catch (Exception e) {
			System.out.println("DBBenutzerDAO: getKundenList: Error");
			return null;
		}
	}
	
	@Override
	public List<Benutzer> getMitarbeiterList() {
		List<String> alleUsrIDMitarbeiter = new ArrayList<String>();
		List<Benutzer> benutzerAlsMitarbeiterListe = new ArrayList<Benutzer>();
		List<String> alleUsrNamesMitarbeiter = new ArrayList<String>();
		
		try {
			ResultSet result = loadAllMitarbeiterStmt.executeQuery();
			
			while(result.next()){
				String usrID = result.getString("USRID");
				alleUsrIDMitarbeiter.add(usrID);
			}
			
			
			for(String usrID : alleUsrIDMitarbeiter){
				alleUsrNamesMitarbeiter.add(getBenutzerByUsrID(usrID).getuName() );
				
			}
			
			for(String uName : alleUsrNamesMitarbeiter){
				benutzerAlsMitarbeiterListe.add(getMitarbeiterByUsername(uName));
			}
			
			return benutzerAlsMitarbeiterListe;
			
		} catch (Exception e) {
			System.out.println("DBBenutzerDAO: getKundenList: Error");
			return null;
		}
	}
	
	@Override
	public Benutzer getBenutzerByUName(String uName) {
		try {
			loadUserStmt.setString(1, uName);
			ResultSet result = loadUserStmt.executeQuery();
			if (!result.next()){
				System.out.println("DBBenutzerDAO: getBenutzerByUName: Kein Benutzer gefunden!");
				return null;
			}
			
			int usrID = result.getInt("USRID");
			String firstname = result.getString("FIRSTNAME");
			String surname = result.getString("SURNAME");
			String email = result.getString("EMAIL");
			String pwd = result.getString("PWD");
			String country = result.getString("COUNTRY");
			String street = result.getString("STREET");
			String city = result.getString("CITY");
			try {
				int zip = Integer.parseInt(result.getString("ZIP"));
				int num = Integer.parseInt(result.getString("NUMBR"));

				return new Benutzer(uName, usrID, pwd, firstname, surname, email,
						country, zip, city, street, num);
			} catch (NumberFormatException e) {
				System.out.println("DBBenutzerDAO: getBenutzerByUName: achtung, zip oder number sind in db keine Integer!!");
				return new Benutzer(uName, usrID, pwd, firstname, surname, email,
						country, 0, city, street, 0);
			}
		} catch (Exception e) {
			System.out.println("DBBenutzerDAO: getBenutzerByUName: Error");
			return null;
		}
	}

	/**
	 * Mit dieser Methode kann man auch einen Benutzer anhand seiner eindeutigen UserID bekommen
	 * @param usrID die Userid des zu suchenden Users 
	 * @return null, falls kein Benutzer gefunden wurde, ansonsten das Benutzerobjekt
	 */
	private Benutzer getBenutzerByUsrID(String usrID) {
		try {
			loadUserStmtID.setString(1, usrID);
			ResultSet result = loadUserStmtID.executeQuery();
			if (!result.next()){
				System.out.println("DBBenutzerDAO: getBenutzerByUName: Kein Benutzer gefunden!");
				return null;
			}
			
			
			int uID = result.getInt("USRID");
			String uName = result.getString("UNAME");
			String firstname = result.getString("FIRSTNAME");
			String surname = result.getString("SURNAME");
			String email = result.getString("EMAIL");
			String pwd = result.getString("PWD");
			String country = result.getString("COUNTRY");
			String street = result.getString("STREET");
			String city = result.getString("CITY");
			try {
				int zip = Integer.parseInt(result.getString("ZIP"));
				int num = Integer.parseInt(result.getString("NUMBR"));

				return new Benutzer(uName, uID, pwd, firstname, surname, email,
						country, zip, city, street, num);
			} catch (NumberFormatException e) {
				System.out.println("DBBenutzerDAO: getBenutzerByUName: achtung, zip oder number sind in db keine Integer!!");
				return new Benutzer(uName, uID, pwd, firstname, surname, email,
						country, 0, city, street, 0);
			}
		} catch (Exception e) {
			System.out.println("DBBenutzerDAO: getBenutzerByUName: Error");
			return null;
		}
	}
	
	

	@Override
	public Kunde getKundeByUsername(String uName){
		Benutzer b = this.getBenutzerByUName(uName);//Kunde als Benutzer(in DB gespeichert)
		if(b == null){
			System.out.println("DBBenutzerDAO: getKundeByUsername: kunde nicht als benutzer gespeichert --> nicht im System, oder Error beim Kundensuchen");
			return null;
		}
		
		try{
			loadKundeStmt.setString(1, String.valueOf(b.getUsrID()));
			ResultSet result = loadKundeStmt.executeQuery();
			
			if (!result.next()){
				System.out.println("DBBenutzerDAO: getKundeByUsername: bei dem uName handelt es sich um keinen Kunden");
				return null; 
			}

//Achtung hier muss noch das richtige Datum umgewandelt werden!!!!
			String birthday = result.getString("birthday");			
			String sex = result.getString("sex");
			
			return new Kunde(b.getuName(),b.getUsrID(),b.getPassword(),b.getVorname(),b.getNachname(),b.getEmail(),b.getLand(),b.getPlz(),b.getOrt(),b.getStrasse(),b.getHausNr(),birthday,sex);
		} catch (Exception e) {
			System.out.println("DBBenutzerDAO: getKundeByUName: Error");
			return null;
		}
	}

	

	@Override
	public Mitarbeiter getMitarbeiterByUsername(String uName){
		Benutzer b = this.getBenutzerByUName(uName);//Kunde als Benutzer(in DB gespeichert)
		if(b == null){
			System.out.println("DBBenutzerDAO: getKundeByUsername: kunde nicht als benutzer gespeichert --> nicht im System");
			return null;
		}
		
		try{
			loadMitarStmt.setString( 1, String.valueOf(b.getUsrID()) );
			ResultSet result = loadMitarStmt.executeQuery();
			
			if (!result.next()){
				System.out.println("DBBenutzerDAO: getKundeByUsername: bei dem uName handelt es sich um keinen Mitarbeiter");
				return null; 
			}
			
			int sallary=0;
			int staffNo=0;
			
			try{
				staffNo = Integer.parseInt(result.getString("STAFF_NO"));
				sallary = Integer.parseInt(result.getString("SALARY"));
			}catch(NumberFormatException e){
				System.out.println("DBBenutzerDAO: getBenutzerByUName: achtung, zip oder number sind in db keine Integer!!");
			}
			
			
			return new Mitarbeiter(b.getuName(),b.getUsrID(),b.getPassword(),b.getVorname(),b.getNachname(),b.getEmail(),b.getLand(),b.getPlz(),b.getOrt(),b.getStrasse(),b.getHausNr(),staffNo,sallary);
		} catch (Exception e) {
			System.out.println("DBBenutzerDAO: getMitarbeiterByUName: Error");
			return null;
		}
	}
	
	/**
	 * Ist benötigt, um Einträge aus der Tabelle Benutzer zu löschen
	 * Wird benötigt um Kunden und Mitarbeiter zu löschen
	 * 
	*/
	private boolean loescheBenutzer(String usrID) {
		if(usrID.equals(""))
			return false; //kein Benutzer mit diesem usernamen
		
		try{
			deleteUserStmt.setString(1, usrID);
			deleteUserStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DBBenutzerDAO: LoescheBenutzer: "+e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean loescheKundeByUname(String uName) {
		String usrID = getUsrIDFromUName(uName);
		if(usrID.equals(""))
			return false; //kein Benutzer mit diesem usernamen
		
		try{
			deleteKundeStmt.setString(1, usrID);
			deleteKundeStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DBBenutzerDAO: loescheKundeByUname:" +e.getMessage());
			return false;
		}
		
		if(!loescheBenutzer(usrID))
			return false;
		
		return true;
	}

	@Override
	public boolean loescheMitarbeiterByUname(String uName) {
		String usrID = getUsrIDFromUName(uName);
		if(usrID.equals(""))
			return false; //kein Benutzer mit diesem usernamen
		
		try{
			deleteMitarStmt.setString(1, usrID);
			deleteMitarStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DBBenutzerDAO: loescheMitarbeiterByUname:" +e.getMessage());
			return false;
		}
		
		if(!loescheBenutzer(usrID))
			return false;
		
		return true;
	}

	/**
	 * Mit dieser Methode kann man anhand vom Usernamen eines Benutzers die ID bekommen.
	 * @param uName Der Username des zu suchenden Benutzers
	 * @return Die UserID
	 */
	private String getUsrIDFromUName(String uName){
		
		Benutzer b = getBenutzerByUName(uName);
		
		String usrID="";
		
		try{
			usrID =String.valueOf(b.getUsrID());
		}catch(NullPointerException e){
			return "";
		}
		
		return usrID;
	}
}
