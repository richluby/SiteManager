/*
 * Copyright RLuby, All Rights Reserved
 */
package sitemanager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class generates HTML based on given templates and a controller.
 * <p>
 *
 * @author RLuby
 */
public class HTMLGenerator implements Runnable{

	/**
	 * the list of keywords supported by the program
	 */
	private enum KEYWORDS{
		;
		/** the number of keywords recognized by the program */
		static final public int NUMBER_OF_KEYWORDS = 15;
		static final public String ALBUM_TITLE = "AlbumTitle";//0
		static final public String ALBUM_DESCRIPTION = "AlbumDescription";//1
		static final public String ALBUM_COVER = "AlbumCover";//2
		static final public String ALBUM_SOURCE = "AlbumSource";//3
		static final public String PHOTO_SOURCE = "PhotoSource";//4
		static final public String PHOTO_TITLE = "PhotoTitle"; //5
		static final public String PHOTO_DESCRIPTION = "PhotoDescription";//6
		static final public String BEGIN_LOOP_ALBUM = "BeginLoop:Album";//7
		static final public String BEGIN_LOOP_PHOTO = "BeginLoop:Photo";//8
		static final public String END_LOOP = "EndLoop";//9
		static final public String LIST_LENGTH_ALBUM = "ListLength:Album";//10
		static final public String LIST_LENGTH_PHOTO = "ListLength:Photo";//11
		static final public String MATH = "Math";//12
		static final public String SEQUENTIAL_ID_ALBUM = "SequentialID:ALBUM";//13
		static final public String SEQUENTIAL_ID_PHOTO = "SequentialID:PHOTO";//14
		/** the name of the file that contains information about photos within an album */
		static final public String PHOTO_DATA = "PHOTODATA";
	}//15 used by the progam to substitute

	/**
	 * the LOCATIONS in which html data is stored
	 */
	private enum LOCATION{

		/**
		 * the folder in which the album files are all placed
		 */
		albumData;

		/**
		 * returns the location for the index template within the JAR
		 */
		public static String INDEX_TEMPLATE(){
			return File.separator + "webFiles" + File.separator + "indexTemplate.html";
		}

		/**
		 * returns the location for the album template within the JAR
		 */
		public static String ALBUM_TEMPLATE(){
			return File.separator + "webFiles" + File.separator + "albumTemplate.html";
		}

		/**
		 * returns the location for the <tt>main.css</tt> within the jar
		 */
		public static String MAIN_CSS_TEMPLATE(){
			return File.separator + "webFiles" + File.separator + "main.css";
		}

		/**
		 * returns the location for the <tt>album.css</tt> within the jar
		 */
		public static String ALBUM_CSS_TEMPLATE(){
			return File.separator + "webFiles" + File.separator + "albumStyles.css";
		}

		/**
		 * returns the location for the album folder within the jar
		 */
		public static String ALBUM_FOLDER(Album album){
			return LOCATION.albumData.toString() + File.separator + album.getId() + "-" + album.getName();
		}
	}

	/**
	 * the pattern to use for searching for math expressions
	 */
	private final static Pattern PATTERN = Pattern.compile("\\Q^{" + KEYWORDS.MATH + ":\\E");
	/**
	 * the controller related to this generator
	 */
	private SiteController controller;
	/**
	 * the primary application window
	 */
	private MainFrame mainFrame;
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
	 *
	 * @param mf the mainFrame associated with this generator
	 */
	public HTMLGenerator(MainFrame mf){
		mainFrame = mf;
		controller = mf.getSiteController();
		siteFolder = new File("./");
		stringMap = new HashMap<>(KEYWORDS.NUMBER_OF_KEYWORDS);
		r = new Random();
	}

	/**
	 * instructs the generator to create the site hierarchy
	 * <p>
	 *
	 * @param folder the folder in which to create the site
	 */
	public void generateHTMLData(File folder){
		if(folder != null){
			siteFolder = folder;
			Thread thread = new Thread(this, "writeDataOut");
			thread.start();
		}
	}

	/**
	 * starts the generation process on a separate thread to prevent blocking the UI
	 */
	@Override
	public void run(){
		createWebsite();
		SwingUtilities.invokeLater(()->{
			mainFrame.setNotification("Website succesfully generated. See \"" + siteFolder.getAbsolutePath() + "\".");
		});
	}

	/**
	 * uses the <tt>siteFolder</tt> to use as the root directory for the site
	 */
	private void createWebsite(){
		writeIndexTemplateData(loadIndexTemplateData());
		writeAlbumData(loadAlbumData());
		writeCSSData();
		writePhotosToDirectory();
	}

	/**
	 * copies the photos from their current location to the correct location for site
	 * referencing
	 */
	private void writePhotosToDirectory(){
		Album album = null;
		URL inputUrl = null;
		File dest = null;
		PhotoController photoController = null;
		Photo photo = null;
		try{
			for(int i = 0; i < controller.getNumberOfRows(); i++){
				album = controller.getALbum(i);
				photoController = album.getPhotoController();
				for(int j = 0; j < photoController.getNumberOfRows(); j++){
					photo = photoController.getPhoto(j);
					dest = new File(siteFolder.getAbsolutePath() + File.separator + LOCATION.ALBUM_FOLDER(album) + File.separator + photo.getName());
					if(!dest.exists()){
						System.out.println(photo.getName() + " copied.");
						FileUtils.copyFile(photo.getLocationFile(), dest);
					}
					//System.out.println("PhotoDest: " + dest.getAbsolutePath());
					//throw new IOException("No need to refactor to generate all stuff");
				}
			}
		} catch(IOException ex){
			Logger.getLogger(HTMLGenerator.class.getName()).log(Level.INFO, null, ex);
		}
	}

	/**
	 * places the CSS data into the proper place
	 */
	private void writeCSSData(){
		try{
			URL inputUrl = getClass().getResource(LOCATION.MAIN_CSS_TEMPLATE());
			File dest = new File(siteFolder.getAbsolutePath() + File.separator + "main.css");
			FileUtils.copyURLToFile(inputUrl, dest);
			inputUrl = getClass().getResource(LOCATION.ALBUM_CSS_TEMPLATE());
			dest = new File(siteFolder.getAbsolutePath() + File.separator + LOCATION.albumData + File.separator + "albumStyles.css");
			FileUtils.copyURLToFile(inputUrl, dest);
		} catch(IOException ex){
			Logger.getLogger(HTMLGenerator.class.getName()).log(Level.INFO, null, ex);
		}
	}

	/**
	 * writes the data from the given string builder into an html file
	 */
	private void writeIndexTemplateData(StringBuilder builder){
		FileOperations.FileWriter writer = new FileOperations.FileWriter(siteFolder.getAbsoluteFile() + File.separator + "index.html");
		writer.write(builder.toString());
		writer.close();
	}

	/**
	 * writes the data for each album to disk
	 * <p>
	 *
	 * @param builder the album information to write
	 */
	private void writeAlbumData(final StringBuilder[] builder){
		FileOperations.FileWriter writer = null;
		File albumFolder = null;
		Album album = null;
		HashMap<String, String> photoDataHashMap = new HashMap<>(1);
		//StrSubstitutor photoDataSubber = new StrSubstitutor(photoDataHashMap);
		//String subbedTemplate = "";
		/*
		 * writeBuilder contains the entire file contents, photoBuilder contains the photo
		 * information in a list to be placed into writeBuilder @ <tt>PHOTODATA</tt>
		 */
		final StringBuilder writeBuilder = new StringBuilder(), photoBuilder = new StringBuilder();
		StringBuilder tempBuilder = new StringBuilder();
		for(int i = 0; i < controller.getNumberOfRows(); i++){//go through all albums
			tempBuilder.delete(0, tempBuilder.length());//
			photoBuilder.delete(0, photoBuilder.length());//clear buffer from previous loop
			writeBuilder.delete(0, writeBuilder.length());
			writeBuilder.append(builder[0]);//initialize the file with the default template
			album = controller.getALbum(i);
			albumFolder = new File(siteFolder.getAbsoluteFile() + File.separator + LOCATION.ALBUM_FOLDER(album));
			loadAlbumDataIntoMap(album);
			//subbedTemplate = substituteAlbumDataForVariables(writeBuilder, album);
			if(!albumFolder.exists()){
				albumFolder.mkdirs();
			}
			for(int j = 0; j < album.getPhotoController().getNumberOfRows(); j++){//go through all photos
				photoBuilder.append(subsitutePhotoDataForVariables(builder[1], album, j)).append("\n");
				//append this photo's information to the list of photo stuff
			}
			stringMap.put(KEYWORDS.PHOTO_DATA, photoBuilder.toString());//place into hashmap so that
			//it will be replaced in the write section
			tempBuilder.append(substituteAlbumDataForVariables(writeBuilder, album));
			checkForMath(tempBuilder);
			writer = new FileOperations.FileWriter(albumFolder.getAbsolutePath() + File.separator + album.getName() + ".html");
			writer.write(tempBuilder.toString());
			writer.close();
			//System.out.println(substituteAlbumDataForVariables(writeBuilder, album));
			stringMap.remove(KEYWORDS.PHOTO_DATA);

		}
		album = null;
	}

	/**
	 * replaces any math commands with the appropriate result. The builder is modified
	 * during this replacement
	 */
	private void checkForMath(StringBuilder builder){
		Matcher matcher = PATTERN.matcher(builder);
		String temp = "";
		Stack<Character> stack = new Stack<>();
		HashMap<String, Double> resultsMap = new HashMap<>(20);
		StrSubstitutor subber = new StrSubstitutor(resultsMap);
		subber.setEnableSubstitutionInVariables(true);
		subber.setVariablePrefix("^{");
		int lengthOfExpression = 0, startOfMatch = 0;
		final int OFFSET_FOR_COMMAND_PREFIX = 3;
		char currentChar = '\n';
		while(matcher.find()){
			stack.clear();
			stack.push('{');//clear the stack, and then load the first brace
			try{
				startOfMatch = matcher.start();
				lengthOfExpression = 0;
				for(int i = OFFSET_FOR_COMMAND_PREFIX; i < builder.length(); i++){
					currentChar = builder.charAt(i + startOfMatch);
					if(currentChar == '{'){
						stack.push('{');
					} else if(currentChar == '}'){
						stack.pop();
					}
					lengthOfExpression++;
					if(stack.empty()){//if the stack is empty, then the braces match
						i = builder.length() + 1;
					}
				}
				System.out.println("match: " + matcher.group() + " lng: " + lengthOfExpression);
				temp = builder.substring(startOfMatch + OFFSET_FOR_COMMAND_PREFIX - 1,
						startOfMatch + lengthOfExpression + OFFSET_FOR_COMMAND_PREFIX - 1);
				System.out.println("key: " + temp);
				resultsMap.put(temp, //the ones remove the braces at the start and end of the string
						parseMathExpression(temp.substring(1 + KEYWORDS.MATH.length())));
			} catch(NoSuchElementException | NumberFormatException e){
				System.out.println("The group <" + matcher.group() + "> could not be parsed due to "
						+ e.getMessage() + ".");
			}
		}
		subber.replaceIn(builder);
	}

	/**
	 * parses the expression and returns a value based on RPN
	 * <p>
	 *
	 * @param expression the expression to be parsed. This method assumes that the
	 *                   expression is valid
	 */
	private Double parseMathExpression(String expression){
		String[] tokens = expression.split(" ");
		System.out.println("expr: " + expression);
		RPNModel model = new RPNModel();
		for(String token : tokens){
			if(Character.isDigit(token.charAt(0))){
				model.push(Double.parseDouble(token));
			} else{
				model.parseAction(token.charAt(0));
			}
		}
		return model.getTopStack();
	}

	/**
	 * loads the template data
	 * <p>
	 *
	 * @return returns the two builders. [0] holds the default template, [1] holds the
	 * photo loop information
	 */
	private StringBuilder[] loadAlbumData(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(LOCATION.ALBUM_TEMPLATE())));
		StringBuilder defaultTemplate = new StringBuilder(), defaultPhotoTemplate = new StringBuilder();
		String tempLine = "";
		int lineCount = 0, lineLength = 0;
		try{
			while((tempLine = reader.readLine()) != null){
				if(!tempLine.trim().startsWith("${" + KEYWORDS.BEGIN_LOOP_PHOTO)){
					defaultTemplate.append(tempLine).append('\n');
					lineCount++;
				} else{//handle the photo loop
					defaultTemplate.append("${").append(KEYWORDS.PHOTO_DATA).append("}\n");//use as placeholder for photo data
					while((tempLine = reader.readLine()) != null){
						if(!tempLine.trim().startsWith("${" + KEYWORDS.END_LOOP)){
							defaultPhotoTemplate.append(tempLine).append("\n");
							lineCount++;
						} else{
							break;
						}
					}
				}
			}
		} catch(IOException ex){
			Logger.getLogger(HTMLGenerator.class
					.getName()).log(Level.SEVERE, null, ex);
		} finally{
			try{
				reader.close();

			} catch(IOException ex){
				Logger.getLogger(HTMLGenerator.class
						.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return new StringBuilder[]{defaultTemplate, defaultPhotoTemplate};
	}

	/**
	 * loads the template data into memory
	 */
	private StringBuilder loadIndexTemplateData(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(LOCATION.INDEX_TEMPLATE())));
		try{
			String temp = "";
			StringBuilder builder = new StringBuilder(), finalBuilder = new StringBuilder();
			int albumIndex = 0;
			while((temp = reader.readLine()) != null){
				if(!temp.trim().startsWith("${" + KEYWORDS.BEGIN_LOOP_ALBUM)){
					//build data up for the str substitution to handle at once
					builder.append(temp).append("\n");
				} else{
					//handle previously built data, if exist
					if(builder.length() > 0){
						finalBuilder.append(substituteStaticDataForVariables(builder)).append("\n");
						builder.delete(0, builder.length());
					}
					//run looping ops until end
					while((temp = reader.readLine()) != null){//build a loop set until the end of the loop
						if(!temp.trim().startsWith("${" + KEYWORDS.END_LOOP)){
							//build data up for the str substitution to handle per album
							builder.append(temp).append("\n");
						} else{
							for(int i = 0; i < controller.getNumberOfRows(); i++){
								finalBuilder.append(substituteAlbumDataForVariables(new StringBuilder(builder.toString()), controller.getALbum(i)));
							}//at worst, performance is O(m*n), m is file length, n is list length
							builder.delete(0, builder.length());
							break;
						}
					}
				}
			}
			//finish last build, if exist
			if(builder.length() > 0){
				finalBuilder.append(substituteStaticDataForVariables(builder)).append("\n");
				builder = null;
			}
			return finalBuilder;

		} catch(IOException ex){
			Logger.getLogger(HTMLGenerator.class
					.getName()).log(Level.INFO, null, ex);
		} finally{
			try{
				reader.close();

			} catch(IOException ex){
				Logger.getLogger(HTMLGenerator.class
						.getName()).log(Level.INFO, null, ex);
			}
		}
		return null;
	}

	/**
	 * builds a map and substitutes the appropriate data into the builder
	 * <p>
	 *
	 * @param builder the builder object in which to replace strings
	 * @return returns a string with all items substituted. NOTE: this will modify the
	 * original builder
	 */
	private String substituteAlbumDataForVariables(StringBuilder builder, Album album){
		loadAlbumDataIntoMap(album);
		StrSubstitutor subber = new StrSubstitutor(stringMap);
		subber.setEnableSubstitutionInVariables(true);
		subber.replaceIn(builder);
		return builder.toString();
	}

	/**
	 * substitutes all static, non-changing variables in the builder
	 * <p>
	 *
	 * @param builder the string in which to replace the text
	 * @return the modified string. NOTE: this will modify the original builder
	 */
	private String substituteStaticDataForVariables(StringBuilder builder){
		loadStaticDataIntoMap();
		StrSubstitutor subber = new StrSubstitutor(stringMap);
		subber.setEnableSubstitutionInVariables(true);
		subber.replace(builder);
		return builder.toString();
	}

	/**
	 * replaces photo related information in the given builder
	 * <p>
	 *
	 * @param builder    the builder in which to replace keywords
	 * @param album      the album from which to pull photo data
	 * @param photoIndex the index of the photo to use
	 * @return the modified string. NOTE: this will modify the original builder
	 */
	private String subsitutePhotoDataForVariables(StringBuilder builder, Album album, int photoIndex){
		loadPhotoDataIntoMap(photoIndex, album);
		StrSubstitutor subber = new StrSubstitutor(stringMap);
		subber.setEnableSubstitutionInVariables(true);
		return subber.replace(builder);
	}

	/**
	 * loads all album data into the map
	 * <p>
	 *
	 * @param album the album to load into the map
	 */
	private void loadAlbumDataIntoMap(Album album){
		if(album.getId() < 0){
			album.setId(r.nextInt(1000000));
		}
		stringMap.put(KEYWORDS.ALBUM_TITLE, album.getName());
		stringMap.put(KEYWORDS.ALBUM_DESCRIPTION, album.getDescription());
		stringMap.put(KEYWORDS.ALBUM_COVER, album.getAlbumCover().getName());
		stringMap.put(KEYWORDS.ALBUM_SOURCE, LOCATION.ALBUM_FOLDER(album)); //the source for the hrefs; does not end in file separator
		stringMap.put(KEYWORDS.LIST_LENGTH_ALBUM, controller.getNumberOfRows() + "");//album length
		stringMap.put(KEYWORDS.LIST_LENGTH_PHOTO, album.getPhotoController().getNumberOfRows() + "");//number of photos in this album
		stringMap.put(KEYWORDS.SEQUENTIAL_ID_ALBUM, album.getId() + "");
	}

	/**
	 * loads all photo data into the map
	 * <p>
	 *
	 * @param photoIndex the photo to load into memory
	 */
	private void loadPhotoDataIntoMap(int photoIndex, Album album){
		Photo photo = album.getPhotoController().getPhoto(photoIndex);
		if(photo.getId() < 0){
			photo.setId(r.nextInt(1000000));
		}
		stringMap.put(KEYWORDS.LIST_LENGTH_PHOTO, album.getPhotoController().getNumberOfRows() + "");//number of photos in this album
		//String photoLocation = LOCATION.ALBUM_FOLDER(album) + File.separator + photo.getName();
		stringMap.put(KEYWORDS.PHOTO_SOURCE, photo.getName());
		stringMap.put(KEYWORDS.PHOTO_TITLE, photo.getName());
		stringMap.put(KEYWORDS.PHOTO_DESCRIPTION, photo.getDescription());
		stringMap.put(KEYWORDS.SEQUENTIAL_ID_PHOTO, photo.getId() + "");
	}

	/**
	 * loads all static data into the map
	 */
	private void loadStaticDataIntoMap(){
		stringMap.put(KEYWORDS.LIST_LENGTH_ALBUM, controller.getNumberOfRows() + "");
	}
}
