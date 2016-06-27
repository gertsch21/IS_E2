import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import model.Produkt;

public class DBProduktDAO implements ProduktDAO {
	
	String database = "jdbc:oracle:thin:@oracle-lab.cs.univie.ac.at:1521:lab";
	String user = "a1363772";
	String pwd = "PRise16";

	@Override
	public boolean speichereProdukt(Produkt p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean speichereCategory(Category c) {
		// TODO Auto-generated method stub
        	try {
			System.out.println("DBProduktDB:speichereCategory: " + p.getcategoryName());

			saveCategoryStmt.setInt(1, c.getcategoryID());
			saveCategoryStmt.setString(2, c.getCategoryName());
			saveCategoryStmt.setString(4, c.getCategoryDescription());
			
			
			saveCategoryStmt.executeUpdate();
			return true;
		}catch(NullPointerException e){
			System.out.println("DBProduktDB:speichereCategory: Übergebene Kategorie(Parameter) ist null!!! ("+e.getMessage()+")");
			return false;
		}catch (Exception e) {
			System.out.println("DBProduktDB:speichereCategory: Fehler beim einfuegen der Kategorie zB.Schon vorhanden,...("+e.getMessage()+")!!!");
			return false;
		}

		return false;
	}

	@Override
	public List<Produkt> getProduktList() {
		List<Produkt> liste = new ArrayList<Produkt>();
		try {
			ResultSet result = loadAllProduktStmt.executeQuery();
			int anzProdukt = 0;
			
			while(result.next()){
				String prodName = result.getString("NAME");
				
				try {
					int prodID = Integer.parseInt(result.getString("PRODUCTID"));
					
					liste.add(new Product(prodName, prodID, price, prodDescription, categoryID));
					anzProduct++;
				} catch (NumberFormatException e) {
					System.out.println("DBProductDao: getProductList: Error beim Parsen des Strings in der DB in int wert");
					return null;
				}catch(Exception e){
					System.out.println("DBProductDao: getProductList: Error beim Parsen: "+e.getMessage());
					return null;
				}	
			}
			
			System.out.println("DBProductDao: getProductList: Anzahl Produkte: " + anzProdukt);

			return liste;
			
		} catch (Exception e) {
			System.out.println("DBProduktDAO: getProduktListe: Error");
			return null;
		}
		return null;
	}

	@Override
	public List<Category> getCategoryList() {
		List<Category> liste = new ArrayList<Category>();
		try {
			ResultSet result = loadAllCategoryStmt.executeQuery();
			int anzCategory = 0;
			
			while(result.next()){
				String categoryName = result.getString("NAME");
				
				try {
                        int categoryID = Integer.parseInt(result.getString("CATEGORYID"));
					
					liste.add(new Category(categoryName, categoryID, categoryDescription));
					anzCategory++;
				} catch (NumberFormatException e) {
					System.out.println("DBProductDao: getCategoryList: Error beim Parsen des Strings in der DB in int wert");
					return null;
				}catch(Exception e){
					System.out.println("DBProductDao: getCategoryList: Error beim Parsen: "+e.getMessage());
					return null;
				}	
			}
			
			System.out.println("DBProductDao: getCategoryList: Anzahl Kategorien: " + anzCategory);

			return liste;
			
		} catch (Exception e) {
			System.out.println("DBProduktDAO: getCategoryListe: Error");
			return null;
		}
		return null;
	}

	@Override
	public Produkt getProduktByProduktID(int prodID) {
		
		String  sql = "SELECT * FROM ISE_Product WHERE productID = ?";
		Connection con = DriverManager.getConnection(database, user, pwd);
		PreparedStatement pstm = con.prepareStatement(sql);
		
		pstm.setInt(1,prodID);
		ResultSet result = pstm.executeQuery();
		
		Produkt p = new Produkt();
		
		while(result.next()) {
			
			p.setprodID(result.getInt(1));
			p.setprodName(result.getString(2));
			p.setprice(result.getDouble(3));
			p.setprodDescription(result.getString(4));
			p.setcategoryID(result.getInt(5));
			
		}
		return p;
	}

	@Override
	public Product getProductByProductID(int prodID) {
        Product p = this.getProductByProductID(int prodID);
		if(b == null){
			System.out.println("DBBProduktDAO: getProductByProductID: nicht im System");
			return null;
		}
		} catch (Exception e) {
			System.out.println("DBProduktDAO: getProductByProductID: Error");
			return null;
		}
    }

	@Override
	public Category getCategoryByCategoryID(int categoryID) {
		try {
			loadCategoryStmtID.setString(1, cetagoryID);
			ResultSet result = loadCategoryStmtID.executeQuery();
			if (!result.next()){
				System.out.println("DBProduktDAO: getProductByProductID: Keine Category gefunden!");
				return null;
			}
			
		} catch (Exception e) {
			System.out.println("DBProduktDAO: getCategoryByCategoryID: Error");
			return null;
		}
		return null;
	}

	@Override
	public boolean loescheProduktByProdID() {
		if(produktID.equals("") || getProduktByproduktID(prodID) == null)
			return false;
		try{
			deleteProduktStmt.setInt(1, prodID);
			deleteProduktStmt.setString(2, prodName);
			deleteProduktStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DBProduktDAO: LoescheProdukt: "+e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean loescheCategoryByCategoryID() {
		if(categoryID.equals("") || getCategoryByCategoryID(categoryID) == null)
			return false;
		try{
			deleteCategoryStmt.setInt(1, categoryID);
			deleteCategoryStmt.setString(2, categoryName);
			deleteCategoryStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DBProduktDAO: LoescheCategory: "+e.getMessage());
			return false;
		}
		return true;
	}

}
