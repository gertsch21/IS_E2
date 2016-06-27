import java.util.List;

import model.*;
import dao.*;


public class Datenmigration {

	public static void main(String[] args) {
		BenutzerDAO oracleDB = new DBBenutzerDAO();
		BenutzerDAO mongoDB = new MongoBenutzerDAO();
		
		List<Benutzer> kundenliste = oracleDB.getKundenList();
		List<Benutzer> mitarbeiterliste = oracleDB.getMitarbeiterList();
		
		
//		for(Benutzer k  : kundenliste)
//			if(k instanceof Kunde)
//				mongoDB.speichereKunde((Kunde)k);
		
//		int zaehler = 0;
//		for(Benutzer m : mitarbeiterliste){
//			if(m instanceof Mitarbeiter){
//				mongoDB.speichereMitarbeiter((Mitarbeiter)m);
//				System.out.println(++zaehler+") "+m.getuName());
//			}
//			
//		}
		
		int i = 0;
		for(Benutzer m : mongoDB.getMitarbeiterList()){
			System.out.println(++i);
		}
		
		
		
		
//		
//	
//		for(Benutzer b : benutzerliste){
//			if(b instanceof Mitarbeiter){
//				if(mongoDB.speichereMitarbeiter((Mitarbeiter)b))
//					System.out.println("Einfuegen von " + b.getuName() + " erfolgreich");
//				else
//					System.out.println("Einfuegen von " + b.getuName() + " nicht möglich");
//			}
//			else if(b instanceof Kunde){
//				if(mongoDB.speichereKunde((Kunde)b))
//					System.out.println("Einfuegen von " + b.getuName() + " erfolgreich");
//				else
//					System.out.println("Einfuegen von " + b.getuName() + " nicht möglich");
//			}
//		}

	}

}
