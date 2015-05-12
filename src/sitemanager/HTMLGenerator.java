/*
 * Copyright RLuby, All Rights Reserved
 */
package sitemanager;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class generates HTML based on given templates and a controller.
 * <p>
 * @author RLuby
 */
public class HTMLGenerator {

	/**
	 * the controller related to this generator
	 */
	private SiteController controller;

	/**
	 * initializes the variables for the class
	 * <p>
	 * @param ctrl the controller associated with this generator
	 */
	public HTMLGenerator(SiteController ctrl) {
		controller = ctrl;
	}

	/**
	 * instructs the generator to create the site hierarchy
	 * <p>
	 * @param folder the folder in which to create the site
	 */
	public void generateHTMLData(File folder) {
		try {
			System.out.println("Path: " + this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		} catch (URISyntaxException ex) {
			Logger.getLogger(HTMLGenerator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
