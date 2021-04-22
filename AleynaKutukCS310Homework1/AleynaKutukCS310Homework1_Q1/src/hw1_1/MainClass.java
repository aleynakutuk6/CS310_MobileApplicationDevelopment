package hw1_1;

import java.util.ArrayList;


public class MainClass {

	public static void main(String[] args) {
		
		    //readStudentsFromFile()
				ArrayList<Country> countries =
						AleynaJDBCManager.readStudentsFromFile("world.txt");
			
				//writeIntoTable()
				AleynaJDBCManager.writeIntoTable(countries);
				 
				 //getCountryByID()
				 System.out.println("Country is found with ID: " + AleynaJDBCManager.getCountryByID(5).getCountryID() +
						 " " + AleynaJDBCManager.getCountryByID(5).getCountryName() + " " +  AleynaJDBCManager.getCountryByID(5).getContinentName() + " "
				     + AleynaJDBCManager.getCountryByID(5).getCapitalName() + " " + AleynaJDBCManager.getCountryByID(5).getPopulation());
				 
				 //deleteCountryByID()
				 AleynaJDBCManager.deleteCountryByID(10);				
				 
				 //updateCountryPopulationByID()
				 AleynaJDBCManager.updateCountryPopulationByID(9, 125);
	}

}









