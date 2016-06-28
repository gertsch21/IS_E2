package dao;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import model.Category;
import model.Produkt;

/**
 * @author Peter
 *
 * implements ProduktDAO for MongoDB
 */
public class MongoProduktDAO implements ProduktDAO {

	private String dbName;
	private String collectionName;
	private MongoClient mongoClient;
	private MongoDatabase db;
	private String mongoLocation;
	
	public MongoProduktDAO(){
		
		this.mongoLocation = "localhost";
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = "IS_E";
		this.db = mongoClient.getDatabase(dbName);
		this.collectionName = "Product";
	}
	
	/**
	 * Es wird eine Verbindung mit der MongoDatenbank aufgebaut und auch mit der Datenbank verbunden
	 * 
	 * @param serverLocation Gibt an wo der Server liegt(zB: 10.0.0.10, oder localhost,...)
	 * @param dbName Gibt den Namen der Datenbank an
	 * @param collectionName Gibt den Namen der Collection an
	 */
	public MongoProduktDAO(String serverLocation, String dbName, String collectionName) {

		this.mongoLocation = serverLocation;
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = dbName;
		this.db = mongoClient.getDatabase(dbName);
		this.collectionName = collectionName;
	}
	
	@Override
	public boolean speichereProdukt(Produkt p) {
		// TODO Auto-generated method stub
		int prodId = p.getprodID();
		String name = p.getprodName();
		double price = p.getprice();
		String description = p.getprodDescription();
		int catId = p.getcategoryID();
		
		try {
			db.getCollection(collectionName)
				.updateOne(new Document("_id", catId),
					new Document("$push", 
						new Document("product", 
							new Document("product_id", prodId)
							.append("name", name)
							.append("price", price)
							.append("description", description)
						)
					)
				);
			return true;
			
		} catch (Exception e) {
			System.err.println("MongoProduktDAO: speichereProdukt: Fehler beim Einfügen eines neuen Produktes("+p.getprodID()+")!");
			return false;
		}
	}

	@Override
	public boolean speichereCategory(Category c) {
		int id = c.getcategoryID();
		String name = c.getcategoryName();
		String description = c.getcategoryDescription();
		
		try {
			Document products = new Document();
			
			Document category = new Document() 
				.append("_id", id)
				.append("name", name)
				.append("description", description)
				.append("products", products);
			
			db.getCollection(collectionName).insertOne(category);
			System.out.println("Kategorie ("+name+") wurde gespeichert");
			return true;
			
		} catch (Exception e) {
			System.err.println("MongoProduktDAO: speichereKategorie: Fehler beim Einfügen einer neuen Kategorie("+c.getcategoryName()+")!");
			return false;
		}
	}

	@Override
	public List<Produkt> getProduktList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getCategoryList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produkt getProduktByProduktID(String prodID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategoryByCategoryID(String categoryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loescheProduktByProdID(String prodID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loescheCategoryByCategoryID(String categoryID) {
		// TODO Auto-generated method stub
		return false;
	}

}
