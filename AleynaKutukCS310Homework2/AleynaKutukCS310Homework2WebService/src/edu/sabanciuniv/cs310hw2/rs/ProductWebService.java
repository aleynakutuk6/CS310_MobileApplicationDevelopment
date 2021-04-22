package edu.sabanciuniv.cs310hw2.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("ProductWebService")
public class ProductWebService {
	
	@GET
	@Path("addNewProduct/{pn}/{pr}/{sc}")
	public boolean addNewProduct(@PathParam("pn") String productname, @PathParam("pr") double price, @PathParam("sc")int stockcount)
	{
		Product p = new Product(productname,price,stockcount);
		boolean res= JDBCManager.save(p);
		return res;
	}
	
	@GET
	@Path("deleteProduct/{pi}")
	public boolean deleteProduct(@PathParam("pi") int productID)
	{
		boolean res= JDBCManager.delete(productID);
		return res;
	}
	
	@GET
	@Path("updateProductStock/{pi}/{npr}/{nsc}")
	public boolean updateProductStock(@PathParam("pi") int productID, @PathParam("npr") double newprice, @PathParam("nsc")int newstockcount)
	{
		boolean res=JDBCManager.update(productID,newprice,newstockcount);
		return res;
	}
		
	

}

	
	
	
	
	
	
	