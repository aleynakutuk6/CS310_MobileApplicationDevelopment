package hw1_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

// -------- JDBC PORJECT -----------

public class AleynaJDBCManager {
	
	
	public static ArrayList<Country> readStudentsFromFile(String filename)
	{
		ArrayList<Country> countries
		= new ArrayList<Country>();
		try 
		{
			int CountryID=0;
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
			
				Country s = new Country(CountryName, ContinentName, CapitalName, Population, CountryID);
				countries.add(s);
			}
			reader.close();
		
		}
		catch (FileNotFoundException e) {
			System.out.println("no file");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("no have rights to read that file");
			e.printStackTrace();
		}
		return countries;
	}
	
	public static void writeIntoTable(ArrayList<Country> countries) {
		
		try
		{
			// Each country object is inserted to DB with for loop 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw1?serverTimezone=UTC", "root", "arDA1998");		
			for (Country s : countries)
			{
				PreparedStatement ps =  connection.prepareStatement("insert into Country (CountryName,ContinentName,CapitalName,Population,CountryID) values (?,?,?,?,?) ");
				ps.setString(1, s.getCountryName());
				ps.setString(2, s.getContinentName());
				ps.setString(3, s.getCapitalName());
				ps.setInt(4, s.getPopulation());
				ps.setInt(5, s.getCountryID());
				ps.execute();
			}
			
			
			System.out.println("Data inserted!!!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static Country getCountryByID(int countryID) {
		
		
		 try {
			 Country resultcountry = new Country();
			 
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw1?serverTimezone=UTC", "root", "arDA1998");
			 PreparedStatement ps = con.prepareStatement("select CountryName,ContinentName,CapitalName,Population,CountryID from Country where CountryID = " + countryID);
			 ResultSet rs =	ps.executeQuery();
			 
			 // By using given countryID, we found that row and returned its created Country object 
		     while (rs.next())
				 {
			      resultcountry.setCountryName(rs.getString("CountryName"));
			      resultcountry.setContinentName(rs.getString("ContinentName"));
			      resultcountry.setCapitalName(rs.getString("CapitalName"));
			      resultcountry.setPopulation(rs.getInt("Population"));
			      resultcountry.setCountryID(rs.getInt("CountryID"));
			    
				 }
				  
				  
			      System.out.println("Data found!!!");
			      return resultcountry;
		        
		 }catch (Exception e) {
				e.printStackTrace();
	     }
		 return null;
		
	}
	
   public static void deleteCountryByID(int countryID) {
		
		 try {
			 Statement stmt = null;
		     Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw1?serverTimezone=UTC", "root", "arDA1998");
		     String sql = "delete from Country where CountryID = " + countryID; 
		     stmt = con.createStatement();
		     stmt.executeUpdate(sql);
			 
		     // "delete from " query is used to delete row which has countryID as given 
		     
		     System.out.println("Data deleted!!!");
		        
		 }catch (Exception e) {
				e.printStackTrace();
	     }
	}
   
   
   public static void updateCountryPopulationByID(int countryID, int population) {
		
		 try {
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw1?serverTimezone=UTC", "root", "arDA1998");
			 Statement stmt = con.createStatement();
			 
			 // update set is used to make changes in DB 
			 String sql ="update Country set Population = " + population + " where CountryID = " + countryID;
			 stmt.executeUpdate(sql);
		     System.out.println("Data updated!!!");
		        
		 }catch (Exception e) {
				e.printStackTrace();
	     }
	}

}








