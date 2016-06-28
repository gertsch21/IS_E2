/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import model.*;

/**
 * @author Gerhard
 *
 * Diese Klasse ist dafür zuständig, die Daten aus der Datenbank(MongoDB am Localhost) zu holen und diese Managementklassen zu übergeben
 */
public class MongoBenutzerDAO implements BenutzerDAO {
//Daten für DB Verbindung
	
	private String dbName;
	private String collectionName;
	private MongoClient mongoClient;
	private MongoDatabase db;
	private String mongoLocation;
	
	
	/**
	 * Im Defaultkonstruktor wird eine Verbindung mit der MongoDatenbank aufgebaut und auch mit der Datenbank verbunden
	 * Diese Parameter sind hier nur hardcodiert, aber für unsere Zwecke auch ausreichend 
	 */
	public MongoBenutzerDAO() {

		this.mongoLocation = "localhost";
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = "IS_E";
		this.db = mongoClient.getDatabase(dbName);
		this.collectionName = "User";
	}

	/**
	 * Es wird eine Verbindung mit der MongoDatenbank aufgebaut und auch mit der Datenbank verbunden
	 * 
	 * @param serverLocation Gibt an wo der Server liegt(zB: 10.0.0.10, oder localhost,...)
	 * @param dbName Gibt den Namen der Datenbank an
	 * @param collectionName Gibt den Namen der Collection an
	 */
	public MongoBenutzerDAO(String serverLocation, String dbName, String collectionName) {

		this.mongoLocation = serverLocation;
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = dbName;
		this.db = mongoClient.getDatabase(dbName);
		this.collectionName = collectionName;
	}
	
	
	
	@Override
	public boolean speichereKunde(Kunde k) {
		
		int id = k.getUsrID();
		String username = k.getuName();
		String firstname = k.getVorname();
		String surname = k.getNachname();
		String email = k.getEmail();
		String pwd = k.getPassword();
		String birthday = k.getBirthday();
		char sex = k.getSex();
		String country = k.getLand();
		int zip = k.getPlz();
		String city = k.getOrt();
		String street = k.getStrasse();
		int number = k.getHausNr();
		
		
		if(this.getBenutzerByUName(username)!=null){ // Username schon vergeben
			System.out.println("MongoBenutzerDAO: Username schon vergeben");
			return false;
		}
		
		try {

			Document kundenDaten = new Document()
				.append("birthday", birthday)
				.append("sex", sex);
			
			Document adressDaten = new Document()
				.append("country", country)
				.append("city", city)
				.append("zip", zip)
				.append("street", street)
				.append("number", number);
			
			Document neuerMitarbeiter = new Document()
				.append("_id", id)
				.append("uname", username)
				.append("firstname", firstname)
				.append("surname", surname)
				.append("email", email)
				.append("pwd", pwd)
				.append("kundenDaten", kundenDaten)
				.append("adresse", adressDaten);

				db.getCollection(collectionName).insertOne(neuerMitarbeiter);
			System.out.println("Kunde("+username+") wurde gespeichert");
			return true;
		} catch (Exception e) {
			System.err.println("MongoBenutzerDAO: speichereKunde: Fehler beim Einfügen eines neuen Kunden("+k.toString()+")!!!!");
			return false;
		}

	}

	
	@Override
	public boolean speichereMitarbeiter(Mitarbeiter m) {
		
		int id = m.getUsrID();
		String username = m.getuName();
		String firstname = m.getVorname();
		String surname = m.getNachname();
		String email = m.getEmail();
		String pwd = m.getPassword();
		int staffNo =m.getStaffNo();
		int sallary = m.getSallary();
		String country = m.getLand();
		int zip = m.getPlz();
		String city = m.getOrt();
		String street = m.getStrasse();
		int number = m.getHausNr();
		
		if(this.getBenutzerByUName(username) != null){//username schon vergeben
			System.out.println("MongoBenutzerDAO: Username schon vergeben");
			return false;
		}
		
		try {

			Document mitarbeiterDaten = new Document()
				.append("staffNo", staffNo)
				.append("sallary", sallary);
			
			Document adressDaten = new Document()
				.append("country", country)
				.append("city", city)
				.append("zip", zip)
				.append("street", street)
				.append("number", number);
			
			Document neuerMitarbeiter = new Document()
				.append("_id", id)
				.append("uname", username)
				.append("firstname", firstname)
				.append("surname", surname)
				.append("email", email)
				.append("pwd", pwd)
				.append("mitarbeiterDaten", mitarbeiterDaten)
				.append("adresse", adressDaten);

				db.getCollection(collectionName).insertOne(neuerMitarbeiter);
			System.out.println("Mitarbeiter("+username+") wurde gespeichert");
			return true;
		} catch (Exception e) {
			System.err.println("MongoBenutzerDAO: speichereMitarbeiter: Fehler beim Einfügen eines neuen Mitarbeiter("+m.toString()+")!!!!");
			return false;
		}

	}
	

	@Override
	public List<Benutzer> getBenutzerList() {
		List<Benutzer> liste = new ArrayList<Benutzer>();

		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
		try{
			for(Document d : documents){
				if(d.get("mitarbeiterDaten") != null){
					liste.add( new Mitarbeiter(
							d.getString("uname"),
							d.getInteger("_id").intValue(),
							d.getString("pwd"),
							d.getString("firstname"),
							d.getString("surname"),
							d.getString("email"),
							((Document)d.get("adresse")).getString("country"),
							((Document)d.get("adresse")).getInteger("zip").intValue(),
							((Document)d.get("adresse")).getString("city"),
							((Document)d.get("adresse")).getString("street"),
							((Document)d.get("adresse")).getInteger("number").intValue(),
							((Document)d.get("mitarbeiterDaten")).getInteger("staffNo").intValue(),
							((Document)d.get("mitarbeiterDaten")).getInteger("sallary").intValue()));
				}
				else if(d.get("kundenDaten") != null){
					liste.add( new Kunde(
							d.getString("uname"),
							d.getInteger("_id").intValue(),
							d.getString("pwd"),
							d.getString("firstname"),
							d.getString("surname"),
							d.getString("email"),
							((Document)d.get("adresse")).getString("country"),
							((Document)d.get("adresse")).getInteger("zip").intValue(),
							((Document)d.get("adresse")).getString("city"),
							((Document)d.get("adresse")).getString("street"),
							((Document)d.get("adresse")).getInteger("number").intValue(),
							((Document)d.get("kundenDaten")).getString("birthday"),
							((Document)d.get("kundenDaten")).getString("sex")));
				}
			}
			return liste;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public List<Benutzer> getKundenList() {
		List<Benutzer> liste = new ArrayList<Benutzer>();

		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
		try{
			for(Document d : documents){
				if(d.get("kundenDaten") != null){
					liste.add( new Kunde(
							d.getString("uname"),
							d.getInteger("_id").intValue(),
							d.getString("pwd"),
							d.getString("firstname"),
							d.getString("surname"),
							d.getString("email"),
							((Document)d.get("adresse")).getString("country"),
							((Document)d.get("adresse")).getInteger("zip").intValue(),
							((Document)d.get("adresse")).getString("city"),
							((Document)d.get("adresse")).getString("street"),
							((Document)d.get("adresse")).getInteger("number").intValue(),
							((Document)d.get("kundenDaten")).getString("birthday"),
							((Document)d.get("kundenDaten")).getString("sex")));
				}
			}
			return liste;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Benutzer> getMitarbeiterList() {
		List<Benutzer> liste = new ArrayList<Benutzer>();

		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
		try{
			for(Document d : documents){
				if(d.get("mitarbeiterDaten") != null){
					liste.add( new Mitarbeiter(
							d.getString("uname"),
							d.getInteger("_id").intValue(),
							d.getString("pwd"),
							d.getString("firstname"),
							d.getString("surname"),
							d.getString("email"),
							((Document)d.get("adresse")).getString("country"),
							((Document)d.get("adresse")).getInteger("zip").intValue(),
							((Document)d.get("adresse")).getString("city"),
							((Document)d.get("adresse")).getString("street"),
							((Document)d.get("adresse")).getInteger("number").intValue(),
							((Document)d.get("mitarbeiterDaten")).getInteger("staffNo"),
							((Document)d.get("mitarbeiterDaten")).getInteger("sallary")));
				}
			}
			return liste;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Benutzer getBenutzerByUName(String uName) {
		try {
			FindIterable<Document> documents = db.getCollection(collectionName)
					.find(new Document("uname", uName));
			
			
			Benutzer b = null; 
			
			for(Document d : documents){//wird sowieso nur einmal durchgeführt
				if(d.get("mitarbeiterDaten") != null){
					b = new Mitarbeiter(
							d.getString("uname"),
							d.getInteger("_id").intValue(),
							d.getString("pwd"),
							d.getString("firstname"),
							d.getString("surname"),
							d.getString("email"),
							((Document)d.get("adresse")).getString("country"),
							((Document)d.get("adresse")).getInteger("zip").intValue(),
							((Document)d.get("adresse")).getString("city"),
							((Document)d.get("adresse")).getString("street"),
							((Document)d.get("adresse")).getInteger("number").intValue(),
							((Document)d.get("mitarbeiterDaten")).getInteger("staffNo").intValue(),
							((Document)d.get("mitarbeiterDaten")).getInteger("sallary").intValue());
				}
				else if(d.get("kundenDaten") != null){
					b = new Kunde(
							d.getString("uname"),
							d.getInteger("_id").intValue(),
							d.getString("pwd"),
							d.getString("firstname"),
							d.getString("surname"),
							d.getString("email"),
							((Document)d.get("adresse")).getString("country"),
							((Document)d.get("adresse")).getInteger("zip").intValue(),
							((Document)d.get("adresse")).getString("city"),
							((Document)d.get("adresse")).getString("street"),
							((Document)d.get("adresse")).getInteger("number").intValue(),
							((Document)d.get("kundenDaten")).getString("birthday"),
							((Document)d.get("kundenDaten")).getString("sex"));
				}
			}
			
			
			return b;
		
		} catch (Exception e) {
			System.out.println("DBBenutzerDAO: getBenutzerByUName: Error");
			return null;
		}
	}
	

	/**
	 * Mit dieser Methode kann man auch einen Benutzer anhand seiner eindeutigen UserID bekommen(Rein zum Testen!!!)
	 * @param usrID die Userid des zu suchenden Users(Achtung: Als String)
	 * @return null, falls kein Benutzer gefunden wurde, ansonsten das Benutzerobjekt
	 */
	private Benutzer getBenutzerByUsrID(String usrID) {
		try {
			FindIterable<Document> documents = db.getCollection(collectionName)
					.find(new Document("_id", Integer.parseInt(usrID) ));
			
			
			Benutzer b = null; 
			
			for(Document d : documents){//wird sowieso nur einmal durchgeführt
				if(d.get("mitarbeiterDaten") != null){
					b = new Mitarbeiter(
							d.getString("uname"),
							d.getInteger("_id").intValue(),
							d.getString("pwd"),
							d.getString("firstname"),
							d.getString("surname"),
							d.getString("email"),
							((Document)d.get("adresse")).getString("country"),
							((Document)d.get("adresse")).getInteger("zip").intValue(),
							((Document)d.get("adresse")).getString("city"),
							((Document)d.get("adresse")).getString("street"),
							((Document)d.get("adresse")).getInteger("number").intValue(),
							((Document)d.get("mitarbeiterDaten")).getInteger("staffNo").intValue(),
							((Document)d.get("mitarbeiterDaten")).getInteger("sallary").intValue());
				}
				else if(d.get("kundenDaten") != null){
					b = new Kunde(
							d.getString("uname"),
							d.getInteger("_id").intValue(),
							d.getString("pwd"),
							d.getString("firstname"),
							d.getString("surname"),
							d.getString("email"),
							((Document)d.get("adresse")).getString("country"),
							((Document)d.get("adresse")).getInteger("zip").intValue(),
							((Document)d.get("adresse")).getString("city"),
							((Document)d.get("adresse")).getString("street"),
							((Document)d.get("adresse")).getInteger("number").intValue(),
							((Document)d.get("kundenDaten")).getString("birthday"),
							((Document)d.get("kundenDaten")).getString("sex"));
				}
			}
			
			
			return b;
		
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
		
		if(b instanceof Kunde)
			return (Kunde)b;
		else 
			return null;
	}

	

	@Override
	public Mitarbeiter getMitarbeiterByUsername(String uName){
		Benutzer b = this.getBenutzerByUName(uName);//Kunde als Benutzer(in DB gespeichert)
		if(b == null){
			System.out.println("DBBenutzerDAO: getKundeByUsername: kunde nicht als benutzer gespeichert --> nicht im System, oder Error beim Kundensuchen");
			return null;
		}
		
		if(b instanceof Mitarbeiter)
			return (Mitarbeiter)b;
		else 
			return null;
	}
	
	
	/**
	 * Egal ob es sich um einen Kunden oder Mitarbeiter handelt, das Dokument aus der Collection User wird gelöscht
	 *
	 * @param uName Der eindeutige uName des zu löschenden Benutzers
	 * @return True falls löschen erfolgreich, sonst false
	 */
	private boolean loescheBenutzer(String uName) {
		DeleteResult dr = db.getCollection(collectionName).deleteOne(
				(new Document("uname", uName)));
		if (dr.getDeletedCount() > 0)
			return true;
		else
			return false;
	}
	

	@Override
	public boolean loescheKundeByUname(String uName) {
		return loescheBenutzer(uName);
	}

	
	@Override
	public boolean loescheMitarbeiterByUname(String uName) {
		return loescheBenutzer(uName);
	}

	
	/**
	 * Mit dieser Methode kann man anhand vom Usernamen eines Benutzers die ID bekommen.(reine Testzwecke!)
	 * @param uName Der Username des zu suchenden Benutzers
	 * @return Die UserID(als String!)
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
