/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import model.Bestellung;
import model.Position;


public class MongoBestellungDAO implements BestellungDAO {
	
	private String dbName;
	private String collectionBestellung;
	private String collectionPosition;
	private MongoClient mongoClient;
	private MongoDatabase db;
	private String mongoLocation;
	
	public MongoBestellungDAO(){
		this.mongoLocation = "localhost";
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = "IS_E";
		this.db = mongoClient.getDatabase(dbName);
		this.collectionBestellung = "Bestellung";
		this.collectionPosition = "Position";
	}
	
	public MongoBestellungDAO(String serverLocation, String dbName, String collectionName) {
		this.mongoLocation = serverLocation;
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = dbName;
		this.db = mongoClient.getDatabase(dbName);
		this.collectionBestellung = collectionName;
		this.collectionPosition = "Position";
	}
	

	@Override
	public boolean speicherePosition(Position p) {
		if(getPositionByID(p.getPositionsNr(), p.getorderID()) == null) { //Position noch nicht vorhanden
			try {
				System.out.println("MongoBestellungDAO: speicherePosition: " + p.getorderID() + ", " + p.getPositionsNr());
				DBCollection collection = (DBCollection) db.getCollection(collectionPosition);
				BasicDBObject document = new BasicDBObject();
				document.put("orderId", p.getorderID());
				document.put("itemID", p.getPositionsNr());
				document.put("quantity", p.getMenge());
				document.put("totalPrice", p.getGesamtpreis());
				document.put("productID", p.getProduktID());
				collection.insert(document);
				return true;
			}catch(NullPointerException e){
				System.out.println("MongoBestellungDAO: speicherePosition: �bergebene Position (Parameter) ist null!!! ("+e.getMessage()+")");
				return false;
			}catch (Exception e) {
				System.out.println("MongoBestellungDAO: speicherePosition: Fehler beim einfuegen der Position ("+e.getMessage()+")!!!");
				return false;
			}
		}
		System.out.println("MongoBestellungDAO: speicherePosition: Fehler beim speichern der Position (evtl. bereits vorhanden)!");
		return false;
	}

	@Override
	public boolean speichereBestellung(Bestellung b) {
		if(getBestellungByID(b.getOrderID()) == null) { //Bestellung nicht vorhanden
			try {
				System.out.println("MongoBestellungDAO: speichereBestellung: " + b.getOrderID());
				DBCollection collection = (DBCollection) db.getCollection(collectionBestellung);
				BasicDBObject document = new BasicDBObject();
				document.put("orderId", b.getOrderID());
				document.put("orderDate", b.getOrderDate());
				document.put("totalPrice", b.getGesamtpreis());
				document.put("usrID", b.getBenuterID());
				collection.insert(document);
				return true;
			}catch(NullPointerException e){
				System.out.println("MongoBestellungDAO: speichereBestellung: �bergebene Bestellung (Parameter) ist null!!! ("+e.getMessage()+")");
				return false;
			}catch (Exception e) {
				System.out.println("MongoBestellungDAO: speichereBestellung: Fehler beim einfuegen der Bestellung ("+e.getMessage()+")!!!");
				return false;
			}
		}
		System.out.println("MongoBestellungDAO: speichereBestellung: Fehler beim speichern der Bestellung - Bestellung evtl bereits vorhanden!");
		return false;
	}

	@Override
	public List<Position> getPositionListbyBestellung(String oID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bestellung> getBestellungList() {
		List<Bestellung> all = new ArrayList<>();
		DBCollection collection = (DBCollection) db.getCollection(collectionBestellung);
		List<DBObject> list = collection.find().toArray();
		for (DBObject o : list) {
			Bestellung b = new Bestellung((String)o.get("orderId"),(String) o.get("orderDate"),(double) o.get("totalPrice"), (int)o.get("usrID"));
			all.add(b);
		}
		return all;
	}

	@Override
	public Position getPositionByID(int pID, String oID) {
		DBCollection collection = (DBCollection) db.getCollection(collectionBestellung);
		BasicDBObject query = new BasicDBObject("_id", oID);
		DBObject object = collection.findOne(query);
		Position position = new Position((String)object.get("_id"),(int) object.get("itemID"),(int) object.get("quantity"), (int)object.get("totalPrice"), (int)object.get("productID"));
		return position;
	}

	@Override
	public Bestellung getBestellungByID(String oID) {
		DBCollection collection = (DBCollection) db.getCollection(collectionBestellung);
		BasicDBObject query = new BasicDBObject("_id", oID);
		DBObject object = collection.findOne(query);
		Bestellung bestellung = new Bestellung((String)object.get("orderId"),(String) object.get("orderDate"),(double) object.get("totalPrice"), (int)object.get("usrID"));
		return bestellung;
	}

	@Override
	public boolean loeschePosition(int pID, String oID) {
		if(oID == null || oID.equals("") || getPositionByID(pID,oID) == null)
			return false;
		DBCollection collection = (DBCollection) db.getCollection(collectionPosition);
		BasicDBObject query = new BasicDBObject("_id", oID);
		collection.remove(query);
		return true;
	}

	@Override
	public boolean loescheBestellung(String oID) {
		if(oID == null || oID.equals("") || getBestellungByID(oID) == null)
			return false;
		DBCollection collection = (DBCollection) db.getCollection(collectionBestellung);
		BasicDBObject query = new BasicDBObject("_id", oID);
		collection.remove(query);
		return true;
	}
	

}