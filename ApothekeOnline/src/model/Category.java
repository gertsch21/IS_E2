package model;

public class Category extends Produkt {

    private int categoryID;
    private String categoryName;
    private String categoryDescription;
    
    public Category (int categoryID, String categoryName, String categoryDescription) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }
    
//getters
    
    public int getcategoryID (){
        return categoryID;
    }
    public String getcategoryName (){
        return categoryName;
    }
    public String getcategoryDescription (){
        return categoryDescription;
    }
    
//setters
    
    public void setcategoryID (int categoryID){
        this.categoryID = categoryID;
    }
    public void setcategoryName (String categoryName){
        this.categoryName = categoryName;
    }
    public void setcategoryDescription (String categoryDescription){
        this.categoryDescription = categoryDescription;
    }
}
