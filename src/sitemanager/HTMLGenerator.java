/*
 * Copyright RLuby, All Rights Reserved
 */
package sitemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class generates HTML based on given templates and a controller.
 * <p>
 * @author RLuby
 */
public class HTMLGenerator implements Runnable {

	/**
	 * the list of keywords supported by the program
	 */
	private final static String[] KEYWORDS = new String[]{"AlbumTitle",//0
		"AlbumDescription",//1
		"AlbumCover",//2
		"AlbumSource",//3
		"PhotoSource",//4
		"PhotoTitle",//5
		"PhotoDescription",//6
		"BeginLoop:Album|Photo",//7
		"EndLoop",//8
		"ListLength:Album|Photo",//9
		"Math",//10
		"SequentialID",//11
		"INC"};//12

	/**
	 * the LOCATIONS in which html data is stored
	 */
	private static enum LOCATION {

		;
		/**returns the location for the index template within the JAR*/
		public static String INDEX_TEMPLATE() {
			return File.separator + "webFiles" + File.separator + "indexTemplate.html";
		}

		/**
		 * returns the location for the album template within the JAR
		 */
		public static String ALBUM_TEMPLATE() {
			return File.separator + "webFiles" + File.separator + "albumTemplate.html";
		}
	};
	/**
	 * the controller related to this generator
	 */
	private SiteController controller;
	/**
	 * the folder in which to place the html data
	 */
	private File siteFolder;

	/**
	 * initializes the variables for the class
	 * <p>
	 * @param ctrl the controller associated with this generator
	 */
	public HTMLGenerator(SiteController ctrl) {
		controller = ctrl;
		siteFolder = new File("./");
	}

	/**
	 * instructs the generator to create the site hierarchy
	 * <p>
	 * @param folder the folder in which to create the site
	 */
	public void generateHTMLData(File folder) {
		if (folder != null) {
			siteFolder = folder;
			Thread thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * starts the generation process on a separate thread to prevent blocking the UI
	 */
	@Override
	public void run() {
		createWebsite();
	}

	/**
	 * uses the <tt>siteFolder</tt> to use as the root directory for the site
	 */
	private void createWebsite() {
		loadTemplateData();

	}

	/**
	 * loads the template data into memory
	 */
	private void loadTemplateData() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(LOCATION.INDEX_TEMPLATE())));
		try {
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				parseLine(temp);
			}

		} catch (IOException ex) {
			Logger.getLogger(HTMLGenerator.class.getName()).log(Level.INFO, null, ex);
		} finally {
			try {
				reader.close();
			} catch (IOException ex) {
				Logger.getLogger(HTMLGenerator.class.getName()).log(Level.INFO, null, ex);
			}
		}
	}

	/**
	 * parses the given line for a substitution
	 */
	private void parseLine(String dataLine) {
		String trimmed = dataLine.trim();
		String[] tokens = dataLine.split("\\$\\{");
		for (String token : tokens) {
			System.out.println("token: " + token);
		}
	}
}
