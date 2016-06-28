/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Benutzer;
import model.Bestellung;
import model.Position;

/**
 * @author Katrin, Peter
 *
 */
public class DBBestellungDAO implements BestellungDAO {
	//Daten für DB Verbindung
	private final String dbUrl = "jdbc:oracle:thin:@oracle-lab.cs.univie.ac.at:1521:lab";
	private final String user = "a1363772";
	private final String pwd = "PRise16";
	
	//Statements um Daten aus db zu holen(über SQL)
	private PreparedStatement savePositionStmt;
	private PreparedStatement saveBestellungStmt;
	
	private PreparedStatement loadPositionStmt;
	private PreparedStatement loadBestellungStmt;
	private PreparedStatement loadAllPositionStmtB;
	private PreparedStatement loadAllBestellungenStmt;
	
	private PreparedStatement deletePositionStmt;
	private PreparedStatement deleteBestellungStmt;

	public DBBestellungDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(dbUrl, user, pwd);
			
			savePositionStmt = con
					.prepareStatement("INSERT INTO ISE_ITEM(orderID,itemID,quantity,totalPrice,productID) VALUES(?,?,?,?,?)");
			saveBestellungStmt = con
					.prepareStatement("INSERT INTO ISE_ShoppingCart(orderID,orderDate,totalPrice,usrID) VALUES (?, TO_DATE(?, 'mm/dd/yyyy'), ?, ?)");
			
			loadPositionStmt = con.prepareStatement("SELECT * FROM ISE_Item WHERE orderID=? AND itemID=?");
			loadBestellungStmt = con.prepareStatement("SELECT * FROM ISE_ShoppingCart WHERE orderID=?");
			loadAllPositionStmtB = con.prepareStatement("SELECT * FROM ISE_ITEM WHERE orderID=?");
			loadAllBestellungenStmt = con.prepareStatement("SELECT * FROM ISE_ShoppingCart");
			
			deletePositionStmt = con.prepareStatement("DELETE FROM ISE_ITEM WHERE itemID=? AND orderID=?");
			deleteBestellungStmt = con.prepareStatement("DELETE FROM ISE_ShoppingCart WHERE orderID=?");			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Verbindungsaufbau zur DB nicht möglich!! ("+e.getMessage()+")");
		}
	}
	
	/* (non-Javadoc)
	 * @see dao.BestellungDAO#speicherePosition(model.Position)
	 */
	@Override
	public boolean speicherePosition(Position p) {
		if(getPositionByID(p.getPositionsNr(), p.getorderID()) == null) { //Position noch nicht vorhanden
			try {
				System.out.println("DBBestellungDAO: speicherePosition: " + p.getorderID() + ", " + p.getPositionsNr());
	
				savePositionStmt.setString(1, p.getorderID());
				savePositionStmt.setInt(2, p.getPositionsNr());
				savePositionStmt.setInt(3, p.getMenge());
				savePositionStmt.setDouble(4, p.getGesamtpreis());
				savePositionStmt.setInt(5, p.getProduktID());			
				
				savePositionStmt.executeUpdate();
				return true;
			}catch(NullPointerException e){
				System.out.println("DBBestellungDAO: speicherePosition: Übergebene Position (Parameter) ist null!!! ("+e.getMessage()+")");
				return false;
			}catch (Exception e) {
				System.out.println("DBBestellungDAO: speicherePosition: Fehler beim einfuegen der Position ("+e.getMessage()+")!!!");
				return false;
			}
		}
		System.out.println("DBBestellungDAO: speicherePosition: Fehler beim speichern der Position (evtl. bereits vorhanden)!");
		return false;
	}

	/* (non-Javadoc)
	 * @see dao.BestellungDAO#speichereBestellung(model.Bestellung)
	 */
	@Override
	public boolean speichereBestellung(Bestellung b) {
		if(getBestellungByID(b.getOrderID()) == null) { //Bestellung nicht vorhanden
			try {
				System.out.println("DBBestellungDAO: speichereBestellung: " + b.getOrderID());
	
				saveBestellungStmt.setString(1, b.getOrderID());
				saveBestellungStmt.setString(2, b.getOrderDate());
				saveBestellungStmt.setDouble(3, b.getGesamtpreis());
				saveBestellungStmt.setInt(4, b.getBenuterID());		
				
				saveBestellungStmt.executeUpdate();
				return true;
			}catch(NullPointerException e){
				System.out.println("DBBestellungDAO: speichereBestellung: Übergebene Bestellung (Parameter) ist null!!! ("+e.getMessage()+")");
				return false;
			}catch (Exception e) {
				System.out.println("DBBestellungDAO: speichereBestellung: Fehler beim einfuegen der Bestellung ("+e.getMessage()+")!!!");
				return false;
			}
		}
		System.out.println("DBBestellungDAO: speichereBestellung: Fehler beim speichern der Bestellung - Bestellung evtl bereits vorhanden!");
		return false;
	}

	/* (non-Javadoc)
	 * @see dao.BestellungDAO#getPositionListbyBestellung(java.lang.String)
	 */
	@Override
	public List<Position> getPositionListbyBestellung(String oID) {
		List<Position> liste = new ArrayList<Position>();
		try {
			loadAllPositionStmtB.setString(1, oID);
			ResultSet result = loadAllPositionStmtB.executeQuery();
			int anzPos = 0;
			
			while(result.next()){
				String orID = result.getString("orderID");
				int posNr = result.getInt("itemID");
				int menge = result.getInt("quantity");
				double gesamtpreis = result.getDouble("totalPrice");
				int prID = result.getInt("productID");

				liste.add(new Position(orID, posNr, menge, gesamtpreis, prID));
				anzPos++;
			}
			
			System.out.println("DBBestellungDAO: getPositionListbyBestellung: Anzahl Positionen: " + anzPos);
			return liste;
		} catch (Exception e) {
			System.out.println("DBBestellungDAO: getPositionListbyBestellung: Error ("+e.getMessage()+")!");
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see dao.BestellungDAO#getBestellungList()
	 */
	@Override
	public List<Bestellung> getBestellungList() {
		List<Bestellung> liste = new ArrayList<Bestellung>();
		try {
			ResultSet result = loadAllBestellungenStmt.executeQuery();
			int anzBestellungen = 0;
			
			while(result.next()){
				String orID = result.getString("orderID");
				String date = result.getString("orderDate");
				double gesamtpreis = result.getDouble("totalPrice");
				int pID = result.getInt("usrID");

				liste.add(new Bestellung(orID, date, gesamtpreis, pID));
				anzBestellungen++;
			}
			
			System.out.println("DBBestellungDAO: getBestellungList: Anzahl Bestellungen: " + anzBestellungen);
			return liste;
		} catch (Exception e) {
			System.out.println("DBBestellungDAO: getBestellungList: Error ("+e.getMessage()+")!");
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see dao.BestellungDAO#getPositionByID(int, java.lang.String)
	 */
	@Override
	public Position getPositionByID(int pID, String oID) {
		try {
			loadPositionStmt.setString(1, oID);
			loadPositionStmt.setInt(2, pID);
			
			ResultSet result = loadPositionStmt.executeQuery();
			
			if (!result.next()){
				System.out.println("DBBestellungDAO: getPositionByID: Keine Position gefunden!");
				return null;
			}
			
			String orID = result.getString("orderID");
			int posNr = result.getInt("itemID");
			int menge = result.getInt("quantity");
			double gesamtpreis = result.getDouble("totalPrice");
			int prID = result.getInt("productID");

			return new Position(orID, posNr, menge, gesamtpreis, prID);
		} catch (Exception e) {
			System.out.println("DBBestellungDAO: getPositionByID: Fehler ("+e.getMessage()+")!");
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see dao.BestellungDAO#getBestellungByID(java.lang.String)
	 */
	@Override
	public Bestellung getBestellungByID(String oID) {
		try {
			loadBestellungStmt.setString(1, oID);
			ResultSet result = loadBestellungStmt.executeQuery();
			
			if (!result.next()){
				System.out.println("DBBestellungDAO: getBestellungByID: Keine Bestellung gefunden!");
				return null;
			}
			
			String orID = result.getString("orderID");
			///String date = result.getString("orderDate");
			
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Timestamp timestamp = result.getTimestamp("orderDate");
			String date = format.format(timestamp);
			
			double gesamtpreis = result.getDouble("totalPrice");
			int pID = result.getInt("usrID");

			return new Bestellung(orID, date, gesamtpreis, pID);
		} catch (Exception e) {
			System.out.println("DBBestellungDAO: getBestellungByID: Fehler ("+e.getMessage()+")!");
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see dao.BestellungDAO#loeschePosition(int, java.lang.String)
	 */
	@Override
	public boolean loeschePosition(int pID, String oID) {
		if(oID.equals("") || getPositionByID(pID,oID) == null)
			return false;
		try{
			deletePositionStmt.setInt(1, pID);
			deletePositionStmt.setString(2, oID);
			deletePositionStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DBBenutzerDAO: LoeschePosition: "+e.getMessage());
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see dao.BestellungDAO#loescheBestellung(java.lang.String)
	 */
	@Override
	public boolean loescheBestellung(String oID) {
		if(oID.equals("") || getBestellungByID(oID) == null)
			return false;
		try{
			List<Position> list = getPositionListbyBestellung(oID);
			if(!list.isEmpty()){
				for(int i=0; i<list.size(); i++){
					int pos = list.get(i).getPositionsNr();
					loeschePosition(pos,oID);
				}
			}
			deleteBestellungStmt.setString(1, oID);
			deleteBestellungStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DBBenutzerDAO: LoeschePosition: "+e.getMessage());
			return false;
		}
		return true;
	}
}