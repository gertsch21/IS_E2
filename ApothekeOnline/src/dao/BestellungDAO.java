/**
 * 
 */
package dao;

import java.util.List;
import model.*;

/**
 * @author Katrin Interface zum Zugriff auf die jeweilige Datenbank. Definition
 *         der wichtigsten Funktionen. Funktionen werden im Management
 *         umgesetzt.
 */
public interface BestellungDAO {
	public boolean speicherePosition(Position p);
	public boolean speichereBestellung(Bestellung b);
	
	public List<Position> getPositionListbyBestellung(String oID);
	public List<Bestellung> getBestellungList();
	public Position getPositionByID(int pID, String oID);
	public Bestellung getBestellungByID(String oID);
	
	public boolean loeschePosition(int pID, String oID);
	public boolean loescheBestellung(String oID);
}
