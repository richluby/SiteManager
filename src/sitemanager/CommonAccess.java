/*
 * Copyright RLuby, All Rights Reserved
 */
package sitemanager;

/**
 * allows a method to have a common approach to get similarly named data from an object
 * <p>
 * @author RLuby
 */
interface CommonAccess {

	/**
	 * returns the name of this element
	 */
	public String getName();

	/**
	 * returns a description of this element
	 */
	public String getDescription();

	/**
	 * returns a file location for this element
	 */
	public java.io.File getLocationFile();
}
