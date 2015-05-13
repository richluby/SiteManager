/**@author Richard Luby, Copyright 2013*/
package person;

import java.io.Serializable;
import java.util.Scanner;


/** this class represents a person with basic info, plus life details */
public class Person extends SimplePerson implements Serializable {
    /** the home address for this person */
    private Address homeAddress;
    /** the significant other of this person */
    private SimplePerson significantOther;

    /** the testing point for the class
     * @param args */
    public static void main(String[] args){
        Person myPerson = new Person();
        Address address = new Address();
        address.setCity("Still");
        address.setState("Oklahoma");
        address.setStreetAddress("5575 E 490 Rd");
        address.setZipCode(74074);
        myPerson.setHomeAddress(address);
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter First Middle Last: ");
        myPerson.setName(scan.nextLine(), FIRST_MID_LAST_ORDER);
        System.out.println("Fix address, Detecting name\n" + myPerson.toLongString());
    }
    /** the initializer for this class */
    public Person() {
        super();
        homeAddress = new Address();
        significantOther = new SimplePerson();
    }
    /** returns the home address for this person
     * @return the homeAddress */
    public Address getHomeAddress(){
        return homeAddress;
    }
    /** sets the home address for this person
     * @param homeAddress the homeAddress to set */
    public void setHomeAddress(Address homeAddress){
        this.homeAddress = homeAddress;
    }
    /** get the significant other for this person
     * @return the significant Other */
    public SimplePerson getSignificantOther(){
        return significantOther;
    }
    /** set the significant other for this person */
    public void setSignificantOther(SimplePerson significantOther){
        this.significantOther = significantOther;
    }

    /** returns a string containing all information about this person
     * @return returns the total information of all that this person contains */
    @Override public String toLongString(){
        StringBuffer buffer = new StringBuffer(super.toLongString());
        String temp = homeAddress.toString();
        if (!temp.equals("")) { //append the home address of this person
            buffer.append("Home Address: ").append(temp).append("\n"); //<<<--------------Align address better
        }
        if (!(temp = significantOther.toString()).equals("")) { //append the significant other
            buffer.append("Significant Other: ").append(temp).append("\n");
        }

        return buffer.toString();
    }

}
