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
 * @author Peter
 *
 * 
 */

public class DBProduktDAO1 implements ProduktDAO {
//Daten für DB Verbindung
	private final String dbUrl = "jdbc:oracle:thin:@oracle-lab.cs.univie.ac.at:1521:lab";
	private final String user = "a1363772";
	private final String pwd = "PRise16";
	
//Statements um Daten aus db zu holen(über SQL)
	private PreparedStatement saveProdStmt;
	private PreparedStatement saveCatStmt;
	private PreparedStatement loadAllProdStmt;
	private PreparedStatement loadAllCatStmt;
	private PreparedStatement loadProductByIdStmt;
	private PreparedStatement loadCategoryByIdStmt;
	private PreparedStatement deleteProdStmt;
	private PreparedStatement deleteCatStmt;
	
	

	/**
	 * Im Konstruktor wird die Verbindung zur Datenbank hergestellt
	 * Es werden auch die Statements definiert, mit welchen man die Daten aus der Datenbank bekommt
	 * Achtung, die User werden aus der Datenbank mit dem Usernamen herausgeholt, wohingegen es beim Kunden und Mitarbeiter die UserID ist
	 */
	public DBProduktDAO1() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(dbUrl, user, pwd);
			
			saveProdStmt = con
					.prepareStatement("INSERT INTO ISE_PRODUCT(PRODUCTID,NAME,PRICE,DESCRIPTION,CATEGORYID) VALUES(?,?,?,?,?)");
			saveCatStmt = con
					.prepareStatement("INSERT INTO ISE_CATEGORY(CATEGORYID,NAME,DESCRIPTION) VALUES(?,?,?)");
			
			loadAllProdStmt = con.prepareStatement("SELECT * FROM ISE_PRODUCT");
			loadAllCatStmt = con.prepareStatement("SELECT * FROM ISE_CATEGORY");
			
			loadProductByIdStmt = con.prepareStatement("SELECT * FROM ISE_PRODUCT WHERE PRODUCTID=?");
			loadCategoryByIdStmt = con.prepareStatement("SELECT * FROM ISE_CATEGORY WHERE CATEGORYID=?");
			
			deleteProdStmt = con.prepareStatement("DELETE FROM ISE_PRODUCT WHERE PRODUCTID=?");
			deleteCatStmt = con.prepareStatement("DELETE FROM ISE_CATEGORY WHERE CATEGORYID=?");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Verbindungsaufbau zur DB nicht möglich!! ("+e.getMessage()+")");
		}

	}

	/**
	 * 
	 * save Products in database - table ISE_Product
	 */
	@Override
	public boolean speichereProdukt(Produkt p) {
		try {
			System.out.println("DBBenutzerDAO1:speichereProdukt: " + p.getprodID() + ", " + p.getprodName());

			
			saveProdStmt.setInt(1, p.getprodID());
			saveProdStmt.setString(2, p.getprodName());
			saveProdStmt.setDouble(3, p.getprice());
			saveProdStmt.setString(4, p.getprodDescription());
			saveProdStmt.setInt(5, p.getcategoryID());
			
			saveProdStmt.executeUpdate();
			return true;
		}catch(NullPointerException e){
			System.out.println("DBProduktDAO1:speichereProdukt: Übergebenes Produkt(Parameter) ist null!!! ("+e.getMessage()+")");
			return false;
		}catch (Exception e) {
			System.out.println("DBProduktDAO1:speichereProdukt: Fehler beim einfuegen des Produkts zBSchon vorhanden,...("+e.getMessage()+")!!!");
			return false;
		}
	}
	
	/**
	 * 
	 * save Category in database - table ISE_Category
	 */
	@Override
	public boolean speichereCategory(Category c) {
		try {
			System.out.println("DBProduktDAO1:speichereCategory: " + c.getcategoryID() + ", " + c.getcategoryName());

			saveCatStmt.setInt(1, c.getcategoryID());
			saveCatStmt.setString(2, c.getcategoryName());
			saveCatStmt.setString(3, c.getcategoryDescription());

			saveCatStmt.executeUpdate();
			return true;
		}catch(NullPointerException e){
			System.out.println("DBProduktDAO1:speichereCategory: Übergebene Kategorie(Parameter) ist null!!! ("+e.getMessage()+")");
			return false;
		}catch (Exception e) {
			System.out.println("DBProduktDAO1:speichereCategory: Fehler beim einfuegen der Kategorie zBSchon vorhanden,...("+e.getMessage()+")!!!");
			return false;
		}
	}
	
	/**
	 * 
	 * query list of all products from database
	 */
	@Override
	public List<Produkt> getProduktList() {
		List<Produkt> liste = new ArrayList<Produkt>();
		try {
			ResultSet result = loadAllProdStmt.executeQuery();
			int anzProdukte = 0;
			
			while(result.next()){
			    String pName = result.getString("NAME");
			    String pDescr = result.getString("DESCRIPTION");

				try {
					int pID = Integer.parseInt(result.getString("PRODUCTID"));
					double pPrice = Double.parseDouble(result.getString("PRICE"));
					int pCatID = Integer.parseInt(result.getString("CATEGORYID"));
					
					liste.add(new Produkt(pID, pName, pPrice, pDescr, pCatID));
					anzProdukte++;
				} catch (NumberFormatException e) {
					System.out.println("DBProduktDao: getProduktList: Error beim Parsen des Strings in der DB in int wert");
					return null;
				}catch(Exception e){
					System.out.println("DBProduktDao: getProduktList: Error beim Parsen: "+e.getMessage());
					return null;
				}	
			}
			
			System.out.println("DBProduktDao: getProduktList: Anzahl Produkte: " + anzProdukte);

			return liste;
			
		} catch (Exception e) {
			System.out.println("DBProduktDAO: getProduktListe: Error");
			return null;
		}
	}
	
	/**
	 * 
	 * query list of all products from database
	 */
	@Override
	public List<Category> getCategoryList() {
		List<Category> liste = new ArrayList<Category>();
		try {
			ResultSet result = loadAllCatStmt.executeQuery();
			int anzCategory = 0;
			
			while(result.next()){
			    String cName = result.getString("NAME");
			    String cDescr = result.getString("DESCRIPTION");

				try {
					int cID = Integer.parseInt(result.getString("CATEGORYID"));
					
					
					liste.add(new Category(cID, cName, cDescr));
					anzCategory++;
				} catch (NumberFormatException e) {
					System.out.println("DBProduktDao: getCategoryList: Error beim Parsen des Strings in der DB in int wert");
					return null;
				}catch(Exception e){
					System.out.println("DBProduktDao: getCategoryList: Error beim Parsen: "+e.getMessage());
					return null;
				}	
			}
			
			System.out.println("DBProduktDao: getCategoryList: Anzahl Categories: " + anzCategory);

			return liste;
			
		} catch (Exception e) {
			System.out.println("DBProduktDAO: getCategoryListe: Error");
			return null;
		}
	}

	/**
	 * 
	 * query product from DB by productID
	 */
	@Override
	public Produkt getProduktByProduktID(String prodID) {
		
		try {
			loadProductByIdStmt.setInt(1, Integer.parseInt(prodID));
			ResultSet result = loadProductByIdStmt.executeQuery();
			if (!result.next()){
				System.out.println("DBProduktDAO: getProduktByProduktID: Kein Produkt gefunden!");
				return null;
			}
			
			
			String pName = result.getString("NAME");
		    String pDescr = result.getString("DESCRIPTION");

			try {
				int pID = Integer.parseInt(result.getString("PRODUCTID"));
				double pPrice = Double.parseDouble(result.getString("PRICE"));
				int pCatID = Integer.parseInt(result.getString("CATEGORYID"));
			
				return new Produkt(pID, pName, pPrice, pDescr, pCatID);
				
			} catch (NumberFormatException e) {
				System.out.println("DBProduktDao: getProduktByProduktID: Error beim Parsen des Strings in der DB in int wert");
				return null;
			}catch(Exception e){
				System.out.println("DBProduktDao: getProduktByProduktID: Error beim Parsen: "+e.getMessage());
				return null;
			}	
			

		} catch (Exception e) {
			System.out.println("DBProduktDAO: getProduktByProduktID: Error");
			return null;
		}
	}


	/**
	 * 
	 * query category from DB by productID
	 */
	@Override
	public  Category getCategoryByCategoryID(String categoryID) {
		
		try {
			loadCategoryByIdStmt.setInt(1, Integer.parseInt(categoryID));
			ResultSet result = loadCategoryByIdStmt.executeQuery();
			if (!result.next()){
				System.out.println("DBProduktDAO: getCategoryByCategoryID: Keine Kategorie gefunden!");
				return null;
			}
			
			
			String cName = result.getString("NAME");
		    String cDescr = result.getString("DESCRIPTION");

			try {
				int cID = Integer.parseInt(result.getString("CATEGORYID"));
			
				return new Category(cID, cName, cDescr);
				
			} catch (NumberFormatException e) {
				System.out.println("DBProduktDao: getCategoryByCategoryID: Error beim Parsen des Strings in der DB in int wert");
				return null;
			}catch(Exception e){
				System.out.println("DBProduktDao: getCategoryByCategoryID: Error beim Parsen: "+e.getMessage());
				return null;
			}	
			

		} catch (Exception e) {
			System.out.println("DBProduktDAO: getCategoryByCategoryID: Error");
			return null;
		}
	}
	
	/**
	 * 
	 * delete product from DB
	 */
	@Override
	public boolean loescheProduktByProdID(String prodID) {
		if(prodID.equals(""))
			return false; //no product with this ID
		
		try{
			deleteProdStmt.setString(1, prodID);
			deleteProdStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DBProduktDAO: LoescheProduktByProdID: "+e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * delete product from DB
	 */
	@Override
	public boolean loescheCategoryByCategoryID(String catID) {
		if(catID.equals(""))
			return false; //no product with this ID
		
		try{
			deleteCatStmt.setString(1, catID);
			deleteCatStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DBProduktDAO: LoescheCategoryByCategoryID: "+e.getMessage());
			return false;
		}
		return true;
	}

}
