package hw1_2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Country {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CountryID;
	private String CountryName;
	private String ContinentName;
	private String CapitalName;
	private int Population;
	
	public Country(String countryName, String continentName, String capitalName, int population, int countryID) {
		super();
		this.CountryName = countryName;
		this.ContinentName = continentName;
		this.CapitalName = capitalName;
		this.Population = population;
		this.CountryID = countryID;
	}
	public Country() {
		super();
	}
	public String getCountryName() {
		return CountryName;
	}
	public void setCountryName(String countryName) {
		this.CountryName = countryName;
	}
	public String getContinentName() {
		return ContinentName;
	}
	public void setContinentName(String continentName) {
		this.ContinentName = continentName;
	}
	public String getCapitalName() {
		return CapitalName;
	}
	public void setCapitalName(String capitalName) {
		this.CapitalName = capitalName;
	}
	public void setPopulation(int population) {
		this.Population = population;
	}
	public int getCountryID() {
		return CountryID;
	}
	public void setCountryID(int countryID) {
		this.CountryID = countryID;
	}
	public int getPopulation() {
		return Population;
	}
	
}
