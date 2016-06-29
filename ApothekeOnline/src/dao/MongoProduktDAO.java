package dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import model.Benutzer;
import model.Category;
import model.Kunde;
import model.Mitarbeiter;
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
						new Document("products", 
							new Document("product_id", prodId)
							.append("name", name)
							.append("price", price)
							.append("description", description)
						)
					)
				);
			System.out.println("Produkt ("+prodId+") wurde gespeichert");
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
				.append("description", description);
				
			
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
		List<Produkt> prodList = new ArrayList<Produkt>();
		
		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
		try{
			for(Document d : documents) {
				int catId = d.getInteger("_id").intValue();
				
				List<Document> products = (List<Document>)d.get("products");
				if(products != null) {	
					for(Document product : products) {
						prodList.add( new Produkt(
								product.getInteger("product_id").intValue(),
								product.getString("name"),
								product.getDouble("price"),
								product.getString("description"),
								catId
								)
						);
					}
				}
			}
			return prodList;
			
		}catch (Exception e) {
			System.out.println("MongoProduktDAO: getProduktList - Error");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Category> getCategoryList() {
		List<Category> catList = new ArrayList<Category>();
		
		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
		try{
			for(Document d : documents) {
				catList.add( new Category(		
						d.getInteger("_id").intValue(),
						d.getString("name"),
						d.getString("description")
				));
			}
			return catList;
			
		}catch (Exception e) {
			System.out.println("MongoProduktDAO: getCategoryList - Error");
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public Produkt getProduktByProduktID(String prodID) {
		
		FindIterable<Document> documents = db.getCollection(collectionName).find();
		Produkt result = null;
		try{
			for(Document d : documents) {
				int catId = d.getInteger("_id").intValue();
				
				List<Document> products = (List<Document>)d.get("products");
				if(products != null) {
					for(Document product : products) {
						if (product.getInteger("product_id").intValue() == Integer.parseInt(prodID))
						{result = new Produkt(
								product.getInteger("product_id").intValue(),
								product.getString("name"),
								product.getDouble("price"),
								product.getString("description"),
								catId
								);
						return result;
						}
					}
				}
				
			}
			return result;
			
		}catch (Exception e) {
			System.out.println("MongoProduktDAO: getProduktByProduktID: Error");
			e.printStackTrace();
			return null;
		}
	}
		

	@Override
	public Category getCategoryByCategoryID(String categoryID) {
		try {
			FindIterable<Document> documents = db.getCollection(collectionName)
					.find(new Document("_id", Integer.parseInt(categoryID) ));
			
			Category c = null; 
			
			for(Document d : documents){
					c = new Category(
							d.getInteger("_id").intValue(),
							d.getString("name"),
							d.getString("description")
							);
			}

			return c;
		
		} catch (Exception e) {
			System.out.println("MongoProduktDAO: getCategoryByCategoryID: Error");
			return null;
		}
	}

	@Override
	public boolean loescheProduktByProdID(String prodID) {
		
		try {
			Produkt prod = this.getProduktByProduktID(prodID);
			int cat_id = prod.getcategoryID();
			
			DBCollection collection = (DBCollection) db.getCollection(collectionName); // Hier hat's was das funkt nicht!
			System.out.println("ich bin da!");
			BasicDBObject query = new BasicDBObject("_id", cat_id);

			BasicDBObject update = new BasicDBObject("products", new BasicDBObject("product_id", prod.getprodID()));
			collection.update(query, new BasicDBObject("$pull",update));
			
			return true;
			
		} catch (Exception e) {
			System.out.println("MongoProduktDAO: loescheProduktByProdID: Error");
			return false;
		}
		
		
	}

	@Override
	public boolean loescheCategoryByCategoryID(String categoryID) {
		DeleteResult dr = db.getCollection(collectionName).deleteOne(
				(new Document("_id", Integer.parseInt(categoryID))));
		if (dr.getDeletedCount() > 0)
			return true;
		else
			return false;
	}

}
