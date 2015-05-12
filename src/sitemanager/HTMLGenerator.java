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
		"SequentialID:ALBUM",//13
		"SequentialID:PHOTO",//14
		"PHOTODATA"};//15 used by the progam to substitute

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

		public static String ALBUM_FOLDER(Album album) {
			return LOCATION.albumData.toString() + File.separator + album.getId() + "-" + album.getName();
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
		writeAlbumData(loadAlbumData());
	}

	/**
	 * writes the data from the given string builder into an html file
	 */
	private void writeIndexTemplateData(StringBuilder builder) {
		FileOperations.FileWriter writer = new FileOperations.FileWriter(siteFolder.getAbsoluteFile() + File.separator + "index.html");
		//writer.write(builder.toString());
		writer.close();
	}

	/**
	 * writes the data for each album to disk
	 * <p>
	 * @param builder the album information to write
	 */
	private void writeAlbumData(final StringBuilder[] builder) {
		FileOperations.FileWriter writer = null;
		File albumFolder = null;
		Album album = null;
		HashMap<String, String> photoDataHashMap = new HashMap<>(1);
		StrSubstitutor photoDataSubber = new StrSubstitutor(photoDataHashMap);
		String subbedTemplate = "";
		/*
		 * writeBuilder contains the entire file contents, photoBuilder contains the photo
		 * information in a list to be placed into writeBuilder @ <tt>PHOTODATA</tt>
		 */
		final StringBuilder writeBuilder = new StringBuilder(), photoBuilder = new StringBuilder();
		StringBuilder tempBuilder = new StringBuilder();
		for (int i = 0; i < controller.getNumberOfRows(); i++) {//go through all albums
			tempBuilder.delete(0, tempBuilder.length());//
			photoBuilder.delete(0, photoBuilder.length());//clear buffer from previous loop
			writeBuilder.delete(0, writeBuilder.length());
			writeBuilder.append(builder[0]);//initialize the file with the default template
			album = controller.getALbum(i);
			albumFolder = new File(LOCATION.ALBUM_FOLDER(album));
			loadAlbumDataIntoMap(album);
			//subbedTemplate = substituteAlbumDataForVariables(writeBuilder, album);
			if (!albumFolder.exists()) {
				albumFolder.mkdirs();
			}
			for (int j = 0; j < album.getPhotoController().getNumberOfRows(); j++) {//go through all photos
				//tempBuilder.delete(0, tempBuilder.length());//clear previous photo text
				photoBuilder.append(subsitutePhotoDataForVariables(builder[1], album, j)).append("\n");
				//append this photo's information to the list of photo stuff
			}
			stringMap.put(KEYWORDS[15], photoBuilder.toString());//place into hashmap so that
			//it will be replaced in the write section
			//writer = new FileOperations.FileWriter(albumFolder.getAbsolutePath() + File.separator + album.getName() + ".html");
			//writer.write(substituteAlbumDataForVariables(writeBuilder, album));
			//writer.close();
			System.out.println(substituteAlbumDataForVariables(writeBuilder, album));
			stringMap.remove(KEYWORDS[15]);

		}
		album = null;
	}

	/**
	 * loads the template data
	 * <p>
	 * @return returns the two builders. [0] holds the default template, [1] holds the
	 *         photo loop information
	 */
	private StringBuilder[] loadAlbumData() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(LOCATION.ALBUM_TEMPLATE())));
		StringBuilder defaultTemplate = new StringBuilder(), defaultPhotoTemplate = new StringBuilder();
		String tempLine = "";
		Album album = null;
		try {
			while ((tempLine = reader.readLine()) != null) {
				if (!tempLine.trim().startsWith("${" + KEYWORDS[8])) {
					defaultTemplate.append(tempLine).append('\n');
				} else {//handle the photo loop
					defaultTemplate.append("${").append(KEYWORDS[15]).append("}\n");//use as placeholder for photo data
					while ((tempLine = reader.readLine()) != null) {
						if (!tempLine.trim().startsWith("${" + KEYWORDS[9])) {
							defaultPhotoTemplate.append(tempLine).append("\n");
						} else {
							break;
						}
					}
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(HTMLGenerator.class
				.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				reader.close();

			} catch (IOException ex) {
				Logger.getLogger(HTMLGenerator.class
					.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return new StringBuilder[]{defaultTemplate, defaultPhotoTemplate};
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
						finalBuilder.append(substituteStaticDataForVariables(builder)).append("\n");
						builder.delete(0, builder.length());
					}
					//run looping ops until end
					while ((temp = reader.readLine()) != null) {//build a loop set until the end of the loop
						if (!temp.trim().startsWith("${" + KEYWORDS[9])) {
							//build data up for the str substitution to handle per album
							builder.append(temp).append("\n");
						} else {
							for (int i = 0; i < controller.getNumberOfRows(); i++) {
								finalBuilder.append(substituteAlbumDataForVariables(new StringBuilder(builder.toString()), controller.getALbum(i)));
							}//at worst, performance is O(m*n), m is file length, n is list length
							builder.delete(0, builder.length());
							break;
						}
					}
				}
			}
			//finish last build, if exist
			if (builder.length() > 0) {
				finalBuilder.append(substituteStaticDataForVariables(builder)).append("\n");
				builder = null;
			}
			return finalBuilder;

		} catch (IOException ex) {
			Logger.getLogger(HTMLGenerator.class
				.getName()).log(Level.INFO, null, ex);
		} finally {
			try {
				reader.close();

			} catch (IOException ex) {
				Logger.getLogger(HTMLGenerator.class
					.getName()).log(Level.INFO, null, ex);
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
	private String substituteAlbumDataForVariables(StringBuilder builder, Album album) {
		loadAlbumDataIntoMap(album);
		StrSubstitutor subber = new StrSubstitutor(stringMap);
		subber.replaceIn(builder);
		return builder.toString();
	}

	/**
	 * substitutes all static, non-changing variables in the builder
	 * <p>
	 * @param builder the string in which to replace the text
	 * @return the modified string. NOTE: this will modify the original builder
	 */
	private String substituteStaticDataForVariables(StringBuilder builder) {
		loadStaticDataIntoMap();
		StrSubstitutor subber = new StrSubstitutor(stringMap);
		subber.replace(builder);
		return builder.toString();
	}

	/**
	 * replaces photo related information in the given builder
	 * <p>
	 * @param builder    the builder in which to replace keywords
	 * @param album      the album from which to pull photo data
	 * @param photoIndex the index of the photo to use
	 * @return the modified string. NOTE: this will modify the original builder
	 */
	private String subsitutePhotoDataForVariables(StringBuilder builder, Album album, int photoIndex) {
		loadPhotoDataIntoMap(photoIndex, album);
		StrSubstitutor subber = new StrSubstitutor(stringMap);
		subber.replace(builder);
		return builder.toString();
	}

	/**
	 * loads all album data into the map
	 * <p>
	 * @param index the album to load into the map
	 */
	private void loadAlbumDataIntoMap(Album album) {
		if (album.getId() < 0) {
			album.setId(r.nextInt(1000000));
		}
		stringMap.put(KEYWORDS[0], album.getName());
		stringMap.put(KEYWORDS[1], album.getDescription());
		stringMap.put(KEYWORDS[2], album.getAlbumCover().getName());
		stringMap.put(KEYWORDS[3], LOCATION.ALBUM_FOLDER(album)); //the source for the hrefs; does not end in file separator
		stringMap.put(KEYWORDS[10], controller.getNumberOfRows() + "");//album length
		stringMap.put(KEYWORDS[11], album.getPhotoController().getNumberOfRows() + "");//number of photos in this album
		stringMap.put(KEYWORDS[13], album.getId() + "");
	}

	/**
	 * loads all photo data into the map
	 * <p>
	 * @param photoIndex the photo to load into memory
	 */
	private void loadPhotoDataIntoMap(int photoIndex, Album album) {
		Photo photo = album.getPhotoController().getPhoto(photoIndex);
		if (photo.getId() < 0) {
			photo.setId(r.nextInt(1000000));
		}
		stringMap.put(KEYWORDS[11], album.getPhotoController().getNumberOfRows() + "");//number of photos in this album
		String photoLocation = LOCATION.ALBUM_FOLDER(album) + File.separator + photo.getName();
		stringMap.put(KEYWORDS[4], photoLocation);
		stringMap.put(KEYWORDS[5], photo.getName());
		stringMap.put(KEYWORDS[6], photo.getDescription());
		stringMap.put(KEYWORDS[14], photo.getId() + "");
	}

	/**
	 * loads all static data into the map
	 */
	private void loadStaticDataIntoMap() {
		stringMap.put(KEYWORDS[10], controller.getNumberOfRows() + "");
	}
}
