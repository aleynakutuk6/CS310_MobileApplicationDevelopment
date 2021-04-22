package edu.sabanciuniv.cs310hw2.rs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class JDBCManager {

	public static boolean save(Product pr) 
	{
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw1?serverTimezone=UTC", "root", "arDA1998");
			PreparedStatement ps =  con.prepareStatement("insert into product (productName,productPrice,productStock) values (?,?,?)");
			ps.setString(1, pr.getProductName());
			ps.setDouble(2, pr.getProductPrice());
			ps.setInt(3, pr.getProductStock());
			
			int result = ps.executeUpdate();
			
			if(result==1)
			{
				return true;
			}
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean update(int productID, double newprice,int newstockcount) {
		
		 try {
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw1?serverTimezone=UTC", "root", "arDA1998");
			 PreparedStatement ps =  con.prepareStatement("update product set productPrice = ?, productStock = ? where productID = ?");
			
			 ps.setDouble(1, newprice);
			 ps.setInt(2, newstockcount);
			 ps.setInt(3, productID);
			 
			
			 int result = ps.executeUpdate();
				
				if(result==1)
				{
					return true;
				}
		     
		        
		 }catch (Exception e) {
				e.printStackTrace();
	     }
		 return false;
	}
	
	public static boolean delete(int productID) {
		
		 try {
		     Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw1?serverTimezone=UTC", "root", "arDA1998");
		     PreparedStatement ps =  con.prepareStatement("delete from product where productID = " + productID); 
		    
		     int result = ps.executeUpdate();
				
				if(result==1)
				{
					return true;
				}
		     
		        
		 }catch (Exception e) {
				e.printStackTrace();
	     }
		 return false;
	}
  
}
