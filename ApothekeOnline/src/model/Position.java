/**
 * 
 */
package model;

/**
 * @author Katrin
 * Deckt Positionen einer Bestellung ab. Produkt mit Menge und Gesamtpreis vereint.
 *
 */
public class Position{
	private String orderID;
	private int positionsNr;
	private int menge;
	private double gesamtpreis;
	private int produktID;
	
	/**
	 * @param oID
	 * @param positionsNr
	 * @param menge
	 * @param gesamtpreis
	 * @param produktID
	 */
	public Position(String oID, int positionsNr, int menge, double gesamtpreis, int produktID) { this.orderID = oID; this.positionsNr = positionsNr;
		this.menge = menge;
		this.gesamtpreis = gesamtpreis;
		this.produktID = produktID;
	}

	
	/**
	 * @return the orderID
	 */
	public String getorderID() {
		return orderID;
	}

	/**
	 * @return the positionsNr
	 */
	public int getPositionsNr() {
		return positionsNr;
	}

	/**
	 * @return the menge
	 */
	public int getMenge() {
		return menge;
	}

	/**
	 * @return the gesamtpreis
	 */
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	/**
	 * @return the produktID
	 */
	public int getProduktID() {
		return produktID;
	}
	
	/**
	 * @param warenkorbID the warenkorbID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	/**
	 * @param positionsNr the positionsNr to set
	 */
	public void setPositionsNr(int positionsNr) {
		this.positionsNr = positionsNr;
	}

	/**
	 * @param menge the menge to set
	 */
	public void setMenge(int menge) {
		this.menge = menge;
	}

	/**
	 * @param gesamtpreis the gesamtpreis to set
	 */
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}

	/**
	 * @param produktID the produktID to set
	 */
	public void setProduktID(int produktID) {
		this.produktID = produktID;
	}
}


