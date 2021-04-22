package hw1_2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//---------- JPA PROJECT -----------

public class AleynaJPAManager {
	
	
	public static ArrayList<Country> readStudentsFromFile(String filename)
	{
		ArrayList<Country> countries = new ArrayList<Country>();
		try 
		{
			FileReader reader = new FileReader(filename);
			BufferedReader bfr = new BufferedReader(reader);
			while(true)
			{
				String line = bfr.readLine();
				if (line == null)
				{
					break;
				}
				
				String[] arr = line.split(",");
				
				String CountryName= arr[0];
				String ContinentName= arr[1];
				String CapitalName= arr[2];
				int Population = Integer.parseInt(arr[3]);
			
				Country s = new Country(CountryName, ContinentName, CapitalName, Population, 0);
				
				countries.add(s);
			}
			reader.close();
		
		}
		catch (FileNotFoundException e) {
			System.out.println("Does not exist such file!");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("You have no rights to read that file!");
			e.printStackTrace();
		}
		return countries;
	}
	
	public static void writeIntoTable(ArrayList<Country> countries) {
		
		try
		{
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
			EntityManager entityManager =emf.createEntityManager();
			entityManager.getTransaction().begin();
			
			// Each Country object will be persisted to DB using EntityManager
			for (Country s : countries)
			{
		        
				entityManager.persist(s);
				
			}
						
			System.out.println("Data inserted!!!");
			entityManager.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
   public static Country getCountryByID(int countryID) {
	
	
	 try {
		 
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		 EntityManager entityManager =emf.createEntityManager();
		
		 // Country is found by using EntityManager find() function and returned
		 Country c = entityManager.find(Country.class, countryID);
		 System.out.println("Data found!!!");
		    
	     return c;
	        
	 }catch (Exception e) {
			e.printStackTrace();
    }
	 return null;
	
   }
	
   public static void deleteCountryByID(int countryID) {
		
		 try {
			 EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
			 EntityManager entityManager =emf.createEntityManager();
			
			 Country c = entityManager.find(Country.class, countryID);
			 entityManager.getTransaction().begin();
			 entityManager.remove(c);         // data is found and that row is deleted from DB
		     entityManager.getTransaction().commit();
		     System.out.println("Data deleted!!!");
		        
		 }catch (Exception e) {
				e.printStackTrace();
	     }
	}
   
   
   public static void updateCountryPopulationByID(int countryID, int population) {
		
		 try {
			 EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
			 EntityManager entityManager =emf.createEntityManager();
			
			 // Data is changed and by using merge, it is saved to DB
			 Country c = entityManager.find(Country.class, countryID);
			 c.setPopulation(population);
			 entityManager.getTransaction().begin();
			 entityManager.merge(c);
		     entityManager.getTransaction().commit();
			 
		     System.out.println("Data updated!!!");
		        
		 }catch (Exception e) {
				e.printStackTrace();
	     }
	}

}








