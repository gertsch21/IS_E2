/**
 * 
 */
/**
 * 
 */
package model;

/**
 * @author Katrin, stellt den Warenkorb der Kunden dar
 *
 */
public class Bestellung {
	String orderID;
	String orderDate;
	double gesamtpreis;
	int benuterID;
	
	/**
	 * @param orderID
	 * @param orderDate
	 * @param gesamtpreis
	 * @param benuterID
	 */
	public Bestellung(String orderID, String orderDate, double gesamtpreis, int benuterID) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.gesamtpreis = gesamtpreis;
		this.benuterID = benuterID;
	}

	/**
	 * @return the orderID
	 */
	public String getOrderID() {
		return orderID;
	}

	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	/**
	 * @return the orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the gesamtpreis
	 */
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	/**
	 * @param gesamtpreis the gesamtpreis to set
	 */
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}

	/**
	 * @return the benuterID
	 */
	public int getBenuterID() {
		return benuterID;
	}

	/**
	 * @param benuterID the benuterID to set
	 */
	public void setBenuterID(int benuterID) {
		this.benuterID = benuterID;
	}
	
	
}
