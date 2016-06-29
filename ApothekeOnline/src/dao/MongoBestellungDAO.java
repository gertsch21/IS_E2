/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
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
				MongoCollection<Document> collection = db.getCollection(collectionPosition);
				Document document = new Document()
						.append("orderID", p.getorderID())
						.append("itemID", p.getPositionsNr())
						.append("quantity", p.getMenge())
						.append("totalPrice", p.getGesamtpreis())
						.append("productID", p.getProduktID());
				collection.insertOne(document);
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
				MongoCollection<Document> collection = db.getCollection(collectionBestellung);
				Document document = new Document()
						.append("orderID", b.getOrderID())
						.append("orderDate", b.getOrderDate())
						.append("totalPrice", b.getGesamtpreis())
						.append("usrID", b.getBenuterID());
				collection.insertOne(document);
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
		MongoCollection<Document> collection = db.getCollection(collectionBestellung);
		Document query = new Document("orderID", oID);
		FindIterable<Document> iterable = collection.find(query);
		List<Position> liste = new ArrayList<Position>();
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document doc) {
				String orID = (String) doc.get("orderID");
				int posNr = (int) doc.get("itemID");
				int menge = (int) doc.get("quantity");
				double gesamtpreis = (double) doc.get("totalPrice");
				int prID = (int) doc.get("productID");
				liste.add(new Position(orID, posNr, menge, gesamtpreis, prID));
			}
		});
		
		System.out.println("MongoBestellungDAO: getPositionListbyBestellung: Anzahl Positionen: " + liste.size());
		return liste;
	}

	@Override
	public List<Bestellung> getBestellungList() {
		List<Bestellung> all = new ArrayList<>();
		MongoCollection<Document> collection = db.getCollection(collectionBestellung);
		FindIterable<Document> iterable = collection.find();
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document doc) {
		    	Bestellung b = new Bestellung((String)doc.get("orderId"),(String) doc.get("orderDate"),(double) doc.get("totalPrice"), (int)doc.get("usrID"));
				all.add(b);
			}
		});
		return all;
	}

	@Override
	public Position getPositionByID(int pID, String oID) {
		MongoCollection<Document> collection =  db.getCollection(collectionBestellung);
		Document query = new Document("orderID", oID).append("itemID", pID);
		Document doc = collection.find(query).first();
		if (doc == null) {
			return null;
		}
		Position position = new Position((String)doc.get("orderID"),(int) doc.get("itemID"),(int) doc.get("quantity"), (int)doc.get("totalPrice"), (int)doc.get("productID"));
		return position;
	}

	@Override
	public Bestellung getBestellungByID(String oID) {
		MongoCollection<Document> collection = db.getCollection(collectionBestellung);
		Document query = new Document("orderID", oID);
		FindIterable<Document> result = collection.find(query);
		Document doc = result.first();
		if (doc == null) {
			return null;
		}
		Bestellung bestellung = new Bestellung((String)doc.get("orderID"),(String) doc.get("orderDate"),(double) doc.get("totalPrice"), (int)doc.get("usrID"));
		return bestellung;
	}

	@Override
	public boolean loeschePosition(int pID, String oID) {
		if(oID == null || oID.equals("") || getPositionByID(pID,oID) == null)
			return false;
		MongoCollection<Document> collection = db.getCollection(collectionPosition);
		Document query = new Document("orderID", oID)
				.append("itemID", pID);
		collection.deleteOne(query);
		return true;
	}

	@Override
	public boolean loescheBestellung(String oID) {
		if(oID == null || oID.equals("") || getBestellungByID(oID) == null)
			return false;
		try{
			List<Position> list = getPositionListbyBestellung(oID);
			if(!list.isEmpty()){
				for(int i=0; i<list.size(); i++){
					int pos = list.get(i).getPositionsNr();
					loeschePosition(pos,oID);
				}
			}
			MongoCollection<Document> collection = db.getCollection(collectionBestellung);
			Document query = new Document("orderID", oID);
			collection.deleteOne(query);
		}catch(Exception e){
			System.out.println("MongoBenutzerDAO: LoeschePosition: "+e.getMessage());
			return false;
		}
		return true;
	}
	

}