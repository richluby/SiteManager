package sitemanager;

import person.SimplePerson;

/**
 * this class manages a person for use with the accounting software
 *
 * @author Richard Luby, Copyright 2015
 */
public class AccountPerson extends SimplePerson{
	/**
	 * boolean to determine if this person is authorized access or not
	 */
	private boolean isAuthorized;

	/**
	 * retrieve the value of this person's authorization
	 *
	 * @return returns true if this person is authorized, false otherwise
	 */
	public boolean isAuthorized(){
		return isAuthorized;
	}

	/**
	 * set the authorization for this person
	 *
	 * @param isAuthorized true if the person is authorized
	 */
	public void setIsAuthorized(boolean isAuthorized){
		this.isAuthorized = isAuthorized;
	}

}
