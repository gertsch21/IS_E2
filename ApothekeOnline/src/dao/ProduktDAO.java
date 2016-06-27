package dao;

import java.util.List;

import model.Produkt;
import model.Category;

public interface ProduktDAO {
	public boolean speichereProdukt(Produkt p);
	public boolean speichereCategory(Category c);
	
	public List<Produkt> getProduktList();
	public List<Category> getCategoryList();
	public Produkt getProduktByProduktID(String prodID);
	public Category getCategoryByCategoryID(String categoryID);
	
    
	public boolean loescheProduktByProdID(String prodID);
	public boolean loescheCategoryByCategoryID(String categoryID);
	
}
