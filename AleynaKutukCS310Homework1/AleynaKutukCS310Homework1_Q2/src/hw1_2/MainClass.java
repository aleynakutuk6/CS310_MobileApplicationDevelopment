package hw1_2;

import java.util.ArrayList;


public class MainClass {

	public static void main(String[] args) {
		
		//readStudentsFromFile()
		ArrayList<Country> countries =
				AleynaJPAManager.readStudentsFromFile("world.txt");
	
		//writeIntoTable()
		 AleynaJPAManager.writeIntoTable(countries);
		 
		 //getCountryByID()
		 System.out.println("Country is found with ID: " + AleynaJPAManager.getCountryByID(4).getCountryID() +
				 " " + AleynaJPAManager.getCountryByID(4).getCountryName() + " " +  AleynaJPAManager.getCountryByID(4).getContinentName() + " "
		     + AleynaJPAManager.getCountryByID(4).getCapitalName() + " " + AleynaJPAManager.getCountryByID(4).getPopulation());
		 
		 //deleteCountryByID()
		 AleynaJPAManager.deleteCountryByID(7);
		 
		 //updateCountryPopulationByID()
		 AleynaJPAManager.updateCountryPopulationByID(3, 125);
	}

}
