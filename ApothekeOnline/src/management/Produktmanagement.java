package management;

import java.util.List;
import java.util.Random;

import model.Produkt;
import model.Category;
import dao.ProduktDAO;
import dao.DBProduktDAO1;

public class Produktmanagement {
	private static Produktmanagement produktmanagementInstance=null;
	private ProduktDAO dao;
	
	private Produktmanagement() {
		dao = new DBProduktDAO1();
	}
    
    public static Produktmanagement getInstance(){
		if(produktmanagementInstance == null) produktmanagementInstance = new Produktmanagement();
		return produktmanagementInstance;
	}
    
    
    public boolean produktAnlegen(String prodName, double price, String prodDescription, int categoryID) {
        Random randomGenerator = new Random();
		int id = randomGenerator.nextInt(Integer.MAX_VALUE);
		System.out.println("Produktmanagement:produktAnlegen: "+id+", "+prodName+", "+price+", "+prodDescription+", "+categoryID+", anlegen!");
		return dao.speichereProdukt(new Produkt(id, prodName, price, prodDescription, categoryID));
    }
  
    public boolean categoryAnlegen(String categoryName, String categoryDescription) {
        Random randomGenerator = new Random();
		int id = randomGenerator.nextInt(Integer.MAX_VALUE);
		System.out.println("Produktmanagement:categoryAnlegen: "+id+", "+categoryName+", "+categoryDescription+", anlegen!");
		return dao.speichereCategory(new Category(id, categoryName, categoryDescription));
    }
    
    
    public List<Produkt> getAlleProdukt(){
		return dao.getProduktList();
	}
	public List<Category> getAlleCategory(){
		return dao.getCategoryList();
	}
    
    
    public Produkt getProduktByProduktID(String prodID){
		return dao.getProduktByProduktID(prodID);
	}
	public Category getCategorybyCategoryID(String categoryID){
		return dao.getCategoryByCategoryID(categoryID);
	}
	
	
	public Produkt getProduktByProduktName(String prodName){
		return dao.getProduktByProduktID(prodName);
	}
    
    public boolean loescheProdukt(String prodID){
		return dao.loescheProduktByProdID(prodID);
	}
	public boolean loescheCategory(String categoryID){
		return dao.loescheCategoryByCategoryID(categoryID);
	}
}
