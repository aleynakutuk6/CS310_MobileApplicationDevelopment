package edu.sabanciuniv.cs310hw2.clientmain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainClass {

	public static void main(String[] args) {
		try 
		{
			URL url1  =  new URL("http://localhost:8080/AleynaKutukCS310Homework2WebService/rest/ProductWebService/updateProductStock/11/25.0/554");
			URL url2  =  new URL("http://localhost:8080/AleynaKutukCS310Homework2WebService/rest/ProductWebService/addNewProduct/Apple/5.0/3000");
			URL url3 =  new URL("http://localhost:8080/AleynaKutukCS310Homework2WebService/rest/ProductWebService/deleteProduct/10");
						
			
			InputStreamReader reader = new InputStreamReader(url1.openStream());
			InputStreamReader reader2 = new InputStreamReader(url2.openStream());
			InputStreamReader reader3 = new InputStreamReader(url3.openStream());
			
			BufferedReader rd = new BufferedReader(reader);
			BufferedReader rd2 = new BufferedReader(reader2);
			BufferedReader rd3 = new BufferedReader(reader3);
			
			while(true)
			{
				String line = rd.readLine();
				if(line==null)
					break;
				System.out.println(line);
			}
				
			while(true)
			{
				String line = rd2.readLine();
				if(line==null)
					break;
				System.out.println(line);
			}
			
			while(true)
			{
				String line = rd3.readLine();
				if(line==null)
					break;
				System.out.println(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
