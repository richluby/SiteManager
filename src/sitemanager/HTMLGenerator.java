/*
 * Copyright RLuby, All Rights Reserved
 */
package sitemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.text.StrSubstitutor;

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
		"BeginLoop:Album",//7
		"BeginLoop:Photo",//8
		"EndLoop",//9
		"ListLength:Album",//10
		"ListLength:Photo",//11
		"Math",//12
		"SequentialID",//13
		"INC"};//14

	/**
	 * the LOCATIONS in which html data is stored
	 */
	private static enum LOCATION {

		/**
		 * the folder in which the album files are all placed
		 */
		albumData;

		/**
		 * returns the location for the index template within the JAR
		 */
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
	 * the map to use in the string substitution
	 */
	private HashMap<String, String> stringMap;
	/**
	 * the random object to use in this generator. this object is used for folder names
	 * and id generation
	 */
	private Random r;

	/**
	 * initializes the variables for the class
	 * <p>
	 * @param ctrl the controller associated with this generator
	 */
	public HTMLGenerator(SiteController ctrl) {
		controller = ctrl;
		siteFolder = new File("./");
		stringMap = new HashMap<>(KEYWORDS.length);
		r = new Random();
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
		writeIndexTemplateData(loadIndexTemplateData());
	}

	/**
	 * writes the data from the given string builder into an html file
	 */
	private void writeIndexTemplateData(StringBuilder builder) {
		FileOperations.FileWriter writer = new FileOperations.FileWriter(siteFolder.getAbsoluteFile() + File.separator + "index.html");
		writer.write(builder.toString());
		writer.close();
	}

	/**
	 * loads the template data into memory
	 */
	private StringBuilder loadIndexTemplateData() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(LOCATION.INDEX_TEMPLATE())));
		try {
			String temp = "";
			StringBuilder builder = new StringBuilder(), finalBuilder = new StringBuilder();
			int albumIndex = 0;
			while ((temp = reader.readLine()) != null) {
				if (!temp.trim().startsWith("${" + KEYWORDS[7])) {
					//build data up for the str substitution to handle at once
					builder.append(temp).append("\n");
				} else {
					//handle previously built data, if exist
					if (builder.length() > 0) {
						finalBuilder.append(substituteAlbumDataForVariables(builder, 0)).append("\n");
						builder.delete(0, builder.length());
					}
					//run looping ops until end
					while ((temp = reader.readLine()) != null) {//build a loop set until the end of the loop
						if (!temp.trim().startsWith("${" + KEYWORDS[9])) {
							//build data up for the str substitution to handle per album
							builder.append(temp).append("\n");

						} else {
							for (int i = 0; i < controller.getNumberOfRows(); i++) {
								finalBuilder.append(substituteAlbumDataForVariables(new StringBuilder(builder.toString()), i));
							}//at worst, performance is O(m*n), m is file length, n is list length
							break;
						}
					}
				}
			}
			//finish last build, if exist
			if (builder.length() > 0) {
				finalBuilder.append(substituteAlbumDataForVariables(builder, 0)).append("\n");
				builder = null;
			}
			return finalBuilder;
		} catch (IOException ex) {
			Logger.getLogger(HTMLGenerator.class.getName()).log(Level.INFO, null, ex);
		} finally {
			try {
				reader.close();
			} catch (IOException ex) {
				Logger.getLogger(HTMLGenerator.class.getName()).log(Level.INFO, null, ex);
			}
		}
		return null;
	}

	/**
	 * builds a map and substitutes the appropriate data into the builder
	 * <p>
	 * @param builder the builder object in which to replace strings
	 * @param number  the index at which to find the item of interest. a negative number
	 *                indicates that static variables are the only ones changing. This
	 *                index will be appended to the end of an ID code if found.
	 * @return returns a string with all items substituted. NOTE: this will modify the
	 *         original builder
	 */
	private String substituteAlbumDataForVariables(StringBuilder builder, int index) {
		int id = r.nextInt(10000);
		Album album = controller.getALbum(index);
		album.setId(id);
		stringMap.put(KEYWORDS[0], album.getName());
		stringMap.put(KEYWORDS[1], album.getDescription());
		stringMap.put(KEYWORDS[2], album.getAlbumCover().getName());
		stringMap.put(KEYWORDS[3], LOCATION.albumData.toString() + File.separator + id + "-" + album.getName()); //the source for the hrefs; does not end in file separator
		stringMap.put(KEYWORDS[10], controller.getNumberOfRows() + "");//album length
		StrSubstitutor subber = new StrSubstitutor(stringMap);
		subber.replaceIn(builder);
		return builder.toString();
	}
}
