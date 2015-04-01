/**@author Richard Luby, Copyright 2013*/
package person;

import java.util.Scanner;

/** this class represents an American address */
public class Address {//changes
	/** the street address of the address */
	private String street;
	/** the house number of the address */
	private int houseNumber;
	/** the city of this address */
	private String city;
	/** the state of this address */
	private String state;
	/** the zip code for this address */
	private int zipCode;
	
	/** constructor to initialize variables to blanks */
	public Address() {
		street = "";
		houseNumber = 0;
		city = "";
		state = "";
		zipCode = 0;
	}
	/** returns the street address
	 * @return the street */
	public String getStreet(){
		return houseNumber + " " + street;
	}
	/** sets the street of the address without the house number
	 * @param street the street to set */
	public void setStreet(String street){
		this.street = street.trim();
	}
	
	/** sets the street address for this address The method expects a house
	 * number, and then the remainder of the address
	 * @param add the street address to be used */
	public void setStreetAddress(String add){
		Scanner scan = new Scanner(add);
		houseNumber = scan.nextInt();
		street = scan.nextLine().trim();
	}
	
	/** sets the house number of the address
	 * @param hn the house number of the address */
	public void setHouseNumber(int hn){
		houseNumber = hn;
	}
	
	/** returns the city for this address
	 * @return the city */
	public String getCity(){
		return city;
	}
	/** sets the city for this address
	 * @param city the city to set */
	public void setCity(String city){
		this.city = city.trim();
	}
	/** returns the state for this address
	 * @return the state */
	public String getState(){
		return state;
	}
	/** sets the state for this address
	 * @param state the state to set */
	public void setState(String state){
		this.state = state.trim();
	}
	/** sets the zip code for this address
	 * @return the zipCode */
	public int getZipCode(){
		return zipCode;
	}
	/** sets the zip code for this address
	 * @param zipCode the zipCode to set */
	public void setZipCode(int zipCode){
		this.zipCode = zipCode;
	}
	/** returns a long string of this address, or "" if no street && no city &&
	 * no state is specified
	 * @return returns a string with all information for this address */
	@Override public String toString(){
		if (street.equals("") && city.equals("") && state.equals("")) { return ""; }
		StringBuffer buffer = new StringBuffer();
		buffer.append(houseNumber).append(" ").append(street).append("\n");
		buffer.append(city).append(", ").append(state).append(" ").append(zipCode);
		return buffer.toString();
	}
	
	/** returns true if the contents of each address are the same, or if at least
	 * one address is incomplete
	 * @param other the other address
	 * @return returns true after checking that both return the same string */
	public boolean equals(Address other){
		if (toString().equals(other.toString())) { return true;
		}
		return false;
	}
}
