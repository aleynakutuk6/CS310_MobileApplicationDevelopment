package edu.sabanciuniv.cs310hw2.rs;

public class Product {
	
	private int productID;
	private String productName;
	private double productPrice;
	private int productStock;
	
	
	public Product() {
		super();
	}
	
	public Product(String productName, double productPrice, int productStock) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStock = productStock;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductStock() {
		return productStock;
	}
	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}
	
	
	

}
