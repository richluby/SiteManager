package person;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Richard Luby, Copyright 2013
 */

/**
 * This is a class with the basics of a person
 */
public class SimplePerson implements Serializable{

	/**
	 * sentinel value to hold the blank date time
	 */
	@SuppressWarnings("deprecation")
	final private static Date SENTDATE = new Date(3059,
			12, 31);
	/**
	 * date format to display the date of birth
	 */
	final private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
	/**
	 * constant to determine if the last name is listed first when passing both
	 * names as a single string
	 */
	final public static int LAST_FIRST_ORDER = 0;
	/**
	 * constant to determine if the first name is listed first when passing both
	 * names as a single string
	 */
	final public static int FIRST_LAST_ORDER = 1;
	/**
	 * constant to determine if the middle name is also included in the First
	 * Middle Last order
	 */
	final public static int FIRST_MID_LAST_ORDER = 2;
	/**
	 * the last name of the person
	 */
	private String lastName;
	/**
	 * the first name of the person
	 */
	private String firstName;
	/**
	 * the middle name of the person
	 */
	private String middleName;
	/**
	 * the date of birth of the person
	 */
	private Date dateOfBirth;
	/**
	 * the work address of the person
	 */
	private Address workAddress;
	/**
	 * the cell phone number of the person
	 */
	private int cPhoneNum;
	/**
	 * the work phone number of the person
	 */
	private int wPhoneNum;
	/**
	 * the work email of the person
	 */
	private String wEmail;
	/**
	 * the home email of the person
	 */
	private String hEmail;
	/**
	 * special items to note about this person
	 */
	private StringBuffer specialNotes;

	/**
	 * Initializes all values to blanks
	 */
	public SimplePerson(){
		lastName = "";
		firstName = "";
		middleName = "";
		dateOfBirth = SENTDATE;
		workAddress = new Address();
		cPhoneNum = 0;
		wPhoneNum = 0;
		wEmail = "";
		hEmail = "";
		specialNotes = new StringBuffer();
	}

	/**
	 * creates a blank person with the specified name
	 * <p>
	 *
	 * @param lName the last name of this person
	 * @param fName the first name of this person
	 */
	public SimplePerson(String lName, String fName){
		this();
		lastName = lName;
		firstName = fName;
	}

	//begin getters and setters

	/**
	 * returns the last name of the person
	 * <p>
	 *
	 * @return the name of the person
	 */
	public String getLastName(){
		return lastName;
	}

	/**
	 * sets the last name of the person
	 * <p>
	 *
	 * @param n the name to set
	 */
	public void setLastName(String n){
		lastName = n;
	}

	/**
	 * returns the middle name of the person
	 * <p>
	 *
	 * @return returns the middle name of the person
	 */
	public String getMiddleName(){
		return middleName;
	}

	/**
	 * sets the middle name of the person
	 * <p>
	 *
	 * @param name the middle name to set
	 */
	public void setMiddleName(String name){
		middleName = name;
	}

	/**
	 * returns the first name of the person
	 * <p>
	 *
	 * @return the first Name
	 */
	public String getFirstName(){
		return firstName;
	}

	/**
	 * sets the first name of the person
	 * <p>
	 *
	 * @param firstName the first Name to set
	 */
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	/**
	 * sets both names of the person
	 * <p>
	 *
	 * @param n   the first and last name of the person
	 * @param ord the order in which the first and last name is listed, as
	 *            specified by {FIRST_LAST_ORDER}
	 */
	public void setName(String n, int ord){
		String[] names = n.trim().split("[\\W]");
		try{
			if(ord == FIRST_LAST_ORDER){
				firstName = names[0];
				for(int i = 1; i < names.length; i++){//why is there a single-use loop?
					if(names[i].matches("[\\w]+")){//not sure what this checks
						lastName = names[i];
						break;
					}
				}
			} else if(ord == LAST_FIRST_ORDER){
				for(int i = 1; i < names.length; i++){
					if(names[i].matches("[\\w]+")){
						firstName = names[i];
						break;
					}

				}
				lastName = names[0];
			} else if(ord == FIRST_MID_LAST_ORDER){
				firstName = names[0];
				middleName = names[1];
				lastName = names[2];
			}
		} catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Improperly formatted name entered\n" + n + "\n" + names);
		}
	}

	/**
	 * gets the birthday of this person
	 * <p>
	 *
	 * @return the birthday
	 */
	public Date getDateOfBirth(){
		return dateOfBirth;
	}

	/**
	 * gets a formatted date of birth
	 * <p>
	 *
	 * @return returns the date of birth in a formatted manner
	 */
	public String getDateOfBirthFormatted(){
		return dateFormat.format(dateOfBirth);
	}

	/**
	 * sets the birthday for this person
	 * <p>
	 *
	 * @param dateOfBirth the birthday to set
	 */
	public void setDateOfBirth(Date dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * returns the work address of this person
	 * <p>
	 *
	 * @return the address
	 */
	public Address getWorkAddress(){
		return workAddress;
	}

	/**
	 * sets the work address of this person
	 * <p>
	 *
	 * @param a the work address to set
	 */
	public void setWorkAddress(Address a){
		workAddress = a;
	}

	/**
	 * returns the cell phone number of the person
	 * <p>
	 *
	 * @return the cell the number of the person
	 */
	public int getCellPhoneNum(){
		return cPhoneNum;
	}

	/**
	 * sets the cell phone number of the person
	 * <p>
	 *
	 * @param cPhoneNum the cell phone number to set
	 */
	public void setCellPhoneNum(int cPhoneNum){
		this.cPhoneNum = cPhoneNum;
	}

	/**
	 * returns the work phone number of the person
	 * <p>
	 *
	 * @return the work phone number
	 */
	public int getWorkPhoneNum(){
		return wPhoneNum;
	}

	/**
	 * sets the work phone number
	 * <p>
	 *
	 * @param wPhoneNum the work phone number to set
	 */
	public void setWorkPhoneNum(int wPhoneNum){
		this.wPhoneNum = wPhoneNum;
	}

	/**
	 * returns the work email of the person
	 * <p>
	 *
	 * @return the work email
	 */
	public String getWorkEmail(){
		return wEmail;
	}

	/**
	 * sets the work email
	 * <p>
	 *
	 * @param wEmail the work email to set
	 */
	public void setWorkEmail(String wEmail){
		this.wEmail = wEmail;
	}

	/**
	 * returns the home email
	 * <p>
	 *
	 * @return the home email
	 */
	public String getHomeEmail(){
		return hEmail;
	}

	/**
	 * sets the home email
	 * <p>
	 *
	 * @param hEmail the home email to set
	 */
	public void setHomeEmail(String hEmail){
		this.hEmail = hEmail;
	}

	/**
	 * returns the special notes of this person in a String
	 * <p>
	 *
	 * @return the special Notes
	 */
	public String getSpecialNotes(){
		return specialNotes.toString();
	}

	/**
	 * overrides the Special Notes for this person
	 * <p>
	 *
	 * @param specialNotes the special Notes to set as an override
	 */
	public void setSpecialNotes(StringBuffer specialNotes){
		this.specialNotes = specialNotes;
	}

	/**
	 * appends more notes to the end of the notes
	 * <p>
	 *
	 * @param notes the content to append to the end of the special notes
	 */
	public void addSpecialNotes(String notes){
		specialNotes.append(notes);
	}

	//Begin comparison and utility functions

	/**
	 * compares the people by last name, and then by first name
	 * <p>
	 *
	 * @param other the person to which to compare
	 * @return returns the same results as a lexicographical comparison of the
	 * name
	 */
	public int compareToIgnoreCase(SimplePerson other){
		if(lastName.compareToIgnoreCase(other.getLastName()) != 0){
			return lastName
					.compareToIgnoreCase(other.getLastName());
		}
		return firstName.compareToIgnoreCase(other.getFirstName());
	}

	/**
	 * checks to see if the names are equal
	 * <p>
	 *
	 * @param other the other person
	 * @return returns true if the first name and the last name are equal
	 */
	public boolean equals(SimplePerson other){
		return lastName.equals(other.getLastName()) && firstName
				.equals(other.getFirstName());
	}

	/**
	 * sets this person equal to the value of another
	 * <p>
	 *
	 * @param other the other person to use as a source
	 */
	public void setEqual(SimplePerson other){
		lastName = other.getLastName();
		firstName = other.getFirstName();
		dateOfBirth = other.getDateOfBirth();
		workAddress = other.getWorkAddress();
		wEmail = other.getWorkEmail();
		wPhoneNum = other.getWorkPhoneNum();
		cPhoneNum = other.getCellPhoneNum();
		specialNotes = new StringBuffer(other.getSpecialNotes());
	}

	/**
	 * returns the name of the person in Last, First format
	 * <p>
	 *
	 * @return returns the name of the person in Last, First format
	 */
	@Override
	public String toString(){
		if(lastName.equals("") && firstName.equals("")){
			return "";
		}
		return lastName + ", " + firstName;
	}

	/**
	 * returns all details of the person in a parsable format
	 * <p>
	 *
	 * @return returns labeled items in a parsable format
	 */
	public String toLongString(){
		StringBuffer results = new StringBuffer();
		if(!lastName.equals("")){
			results.append("Last Name: ").append(lastName).append("\n");
		}
		if(!firstName.equals("")){
			results.append("First Name: ").append(firstName).append("\n");
		}
		if(!middleName.equals("")){
			results.append("Middle Name: ").append(middleName).append("\n");
		}
		if(!dateOfBirth.equals(SENTDATE)){
			results.append("Date of Birth: ").append(
					dateFormat.format(dateOfBirth)).append("\n");
		}
		if(!workAddress.toString().equals("")){
			results.append("Work Address: ").append(workAddress.toString()).append("\n");
		}
		if(cPhoneNum != 0){
			results.append("Cell Phone Number: ").append(cPhoneNum).append("\n");
		}
		if(wPhoneNum != 0){
			results.append("Work Phone Number: ").append(wPhoneNum).append("\n");
		}
		if(!wEmail.equals("")){
			results.append("Work email: ").append(wEmail).append("\n");
		}
		if(!hEmail.equals("")){
			results.append("Home email: ").append(hEmail).append("\n");
		}
		if(!specialNotes.toString().equals("")){
			results.append("Special Notes: ").append(specialNotes.toString())
					.append("\n");
		}
		return results.toString();
	}
}
