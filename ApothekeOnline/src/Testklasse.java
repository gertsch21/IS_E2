import java.util.HashMap;
import java.util.Map;


public class Testklasse {
	public static void main(String[] args){
		
		String keyName = "productid1";
		
		Map<String, Integer> cart = new HashMap<String, Integer>();
		cart.put(keyName, 1);
		if(cart.containsKey(keyName)){
			int anz = cart.get(keyName)+1;
			cart.put(keyName, anz);
		}
		
		System.out.println(cart);
		
	}
}
