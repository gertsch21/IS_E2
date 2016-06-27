package model;

public class Produkt {
    private int prodID;
    private String prodName;
    private double price;
    private String prodDescription;
    private int categoryID;
    
    
    public Produkt(int prodID, String prodName, double price, String prodDescription, int categoryID) {
        this.prodID = prodID;
        this.prodName = prodName;
        this.price = price;
        this.prodDescription = prodDescription;
        this.categoryID = categoryID;
    }
    
    public Produkt() {
    	
    }
//gevf,kvftters

    public int getprodID() {
        return prodID;
    }
    public String getprodName(){
        return prodName;
    }
    public double getprice(){
        return price;
    }
    public String getprodDescription(){
        return prodDescription;
    }
    public int getcategoryID(){
        return categoryID;
    }
    
//setters    
    
    public void setprodID(int prodID) {
        this.prodID = prodID;
    }
    public void setprodName(String prodName){
        this.prodName = prodName;
    }
    public void setprice(double price){
        this.price = price;
    }
    public void setprodDescription(String prodDescription){
        this.prodDescription = prodDescription;
    }
    public void setcategoryID(int categoryID){
        this.categoryID = categoryID;
    }
}
