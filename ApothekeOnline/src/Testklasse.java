import java.util.Random;

import management.Produktmanagement;




public class Testklasse {
	public static void main(String[] args){

		Random randomGenerator = new Random();
		int id = 0;
		
		Produktmanagement management = Produktmanagement.getInstance();
		
		for(int i=0;i<10;i++){
			id = randomGenerator.nextInt(Integer.MAX_VALUE);
			management.produktAnlegen("javaTestprodukt"+id, 10, "JavaTestproduktBeschreibung"+id, 356487644); //Kategorie: Testkategorie
		}
	}
}
