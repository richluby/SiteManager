package sitemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import masterDetail.Tabulate;

/**
 * This class acts as a controller for all photos in a given album
 * <p>
 * @author Richard Luby, Copyright 2015
 */
class PhotoController implements Tabulate {

	/**
	 * the column names for the photos table
	 */
	private final static String[] COLUMN_NAMES = new String[]{"Name", "Location"};
	/**
	 * the constraints to use for the photo in the preview photo sections. the order is
	 * {width, height}
	 */
	private final static float[] CONSTRAINTS = new float[]{.8f, .4f};
	/**
	 * the name of the file in which to look for information regarding photos. This file
	 * must be in the same folder as the albumFolder passed to the controller
	 */
	private final static String FILE_PHOTO_INFORMATION = "PhotoData";

	/**
	 * the keywords used through the file. The <tt>Name</tt> keyword is used to separate
	 * photo instances
	 */
	private static enum KEYWORDS {

		/**
		 * the name of the photo. this keyword must be at the beginning of a photo
		 */
		PHOTO_NAME,
		/**
		 * the description of the photo
		 */
		PHOTO_DESCRIPTION,
		/**
		 * the location on disk of the photo
		 */
		PHOTO_LOCATION,
		/**
		 * the unique id for this photo
		 */
		PHOTO_ID
	};
	/**
	 * the folder in which these photos reside
	 */
	private File albumFolder;
	/**
	 * the list containing all photos managed by this controller
	 */
	private ArrayList<Photo> photoList;
	/**
	 * the information panel to be displayed in the detail pane
	 */
	static private InformationPanel informationPanel;
	/**
	 * The album to which this controller belongs
	 */
	private Album parentAlbum;
	/**
	 * the photo that is currently selected
	 */
	private int activePhotoIndex;

	/**
	 * initializes the controller
	 */
	public PhotoController(Album parent) {
		albumFolder = null;
		photoList = new ArrayList<>();
		parentAlbum = parent;
		activePhotoIndex = 0;
		if (informationPanel == null) {
			informationPanel = new InformationPanel("Photo Information", Listeners.createBrowseForPhoto(), Listeners.creatUpdatePhoto());
		}
	}

	@Override
	public Object getDataForColumn(int rowIndex, int columnIndex) {
		switch (COLUMN_NAMES[columnIndex]) {
			case "Name":
				return photoList.get(rowIndex).getName();
			case "Location":
				return photoList.get(rowIndex).getLocationFile().getAbsolutePath();
			default:
				return "";
		}
	}

	/**
	 * builds the list of photos based on the selected directory
	 * <p>
	 * @param af the album folder in which to find photos.
	 */
	public void populatePhotoList(File af) {
		this.albumFolder = af;
		File photoData = new File(albumFolder.getAbsoluteFile() + File.separator + FILE_PHOTO_INFORMATION);
		if (!photoData.exists()) {
			File[] files = albumFolder.listFiles((File pathname) -> {
				return pathname.getName().endsWith("jpg") || pathname.getName().endsWith(
					"jpeg");
			});
			Photo photo = null;
			for (File file : files) {
				photo = new Photo();
				photo.setPhotoFile(file);
				photo.setPhotoName(file.getName());
				photoList.add(photo);
			}
		} else {
			//read from the photo data file already present
			String tempLine = "";
			Photo photo = new Photo();
			FileOperations.FileReader reader = new FileOperations.FileReader(photoData);
			while ((tempLine = reader.readLine()) != null) {
				tempLine = tempLine.trim();
				if (tempLine.startsWith(KEYWORDS.PHOTO_NAME.toString())) {
					tempLine = tempLine.substring(KEYWORDS.PHOTO_NAME.toString().length() + 2);
					photo = new Photo();
					photo.setPhotoName(tempLine);
					photoList.add(photo);
				} else if (tempLine.startsWith(KEYWORDS.PHOTO_DESCRIPTION.toString()) && tempLine.length() > KEYWORDS.PHOTO_DESCRIPTION.toString().length() + 2) {
					tempLine = tempLine.substring(KEYWORDS.PHOTO_DESCRIPTION.toString().length() + 2);
					photo.setPhotoDescription(tempLine);
				} else if (tempLine.startsWith(KEYWORDS.PHOTO_LOCATION.toString())) {
					tempLine = tempLine.substring(KEYWORDS.PHOTO_LOCATION.toString().length() + 2);
					photo.setPhotoFile(new File(tempLine));
				} else if (tempLine.startsWith(KEYWORDS.PHOTO_ID.toString())) {
					tempLine = tempLine.substring(KEYWORDS.PHOTO_ID.toString().length() + 2);
					photo.setId(Integer.parseInt(tempLine));
				}
			}
			reader.close();
		}
	}

	@Override
	public String[] getColumnNames() {
		return COLUMN_NAMES;
	}

	@Override
	public int getNumberOfRows() {
		return photoList.size();
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return String.class;
	}

	@Override
	public void removeElement(int rowIndex) {
	}

	@Override
	public void addElement() {
	}

	@Override
	public void updateElement(int rowIndex) {
	}

	@Override
	public void updateDisplayForElement(int rowIndex) {
		if (rowIndex < photoList.size()) {
			activePhotoIndex = rowIndex;
			Photo activePhoto = photoList.get(rowIndex);
			informationPanel.setTitle(activePhoto.getName());
			informationPanel.setDescription(activePhoto.getDescription());
			informationPanel.setLocation(activePhoto.getLocationFile().getAbsolutePath());
			informationPanel.setDisplayedImage(activePhoto.getLocationFile(), "south", CONSTRAINTS[0], CONSTRAINTS[1]);
		} else {//clear display
			informationPanel.setTitle("");
			informationPanel.setDescription("");
			informationPanel.setLocation("");
			informationPanel.setDisplayedImage(null, "south", CONSTRAINTS[0], CONSTRAINTS[1]);
		}
	}

	@Override
	public JPanel initDetailComponent() {
		return informationPanel;
	}

	/**
	 * writes the data of the photos to disk
	 */
	void writeDataToDisk() {
		FileOperations.FileWriter writer = new FileOperations.FileWriter(albumFolder.getAbsolutePath() + File.separator + FILE_PHOTO_INFORMATION);
		writer.writeln("#This file contains information regarding the photos in this album.\n#Whitespace is ignored when parsing this file if it occurs at either end of a line.");
		for (Iterator<Photo> iterator = photoList.iterator(); iterator.hasNext();) {
			Photo photo = iterator.next();
			writer.writeln(KEYWORDS.PHOTO_NAME + ": " + photo.getName());
			writer.writeln("\t" + KEYWORDS.PHOTO_DESCRIPTION + ": " + photo.getDescription());
			writer.writeln("\t" + KEYWORDS.PHOTO_LOCATION + ": " + photo.getLocationFile().getAbsolutePath());
			writer.writeln("\t" + KEYWORDS.PHOTO_ID + ": " + photo.getId());
			//System.out.println("{" + photo.getName() + "} was written.");
		}
		writer.close();
	}

	/**
	 * sets the location for the image file for the currently active photo
	 */
	void setPhotoLocationForActivePhoto(File imageFile) {
		photoList.get(activePhotoIndex).setPhotoFile(imageFile);
		informationPanel.setDisplayedImage(imageFile, "south", CONSTRAINTS[0], CONSTRAINTS[1]);
	}

	/**
	 * instructs the controller to update the active photo with the information from the
	 * view
	 */
	void updateActiveElement() {
		Photo photo = photoList.get(activePhotoIndex);
		photo.setPhotoName(informationPanel.getTitle());
		photo.setPhotoDescription(informationPanel.getDescription());
	}

	/**
	 * returns a photo at the given index
	 * <p>
	 * @param photoIndex the index at which to retrieve the photo
	 * @return the photo at the given index
	 */
	Photo getPhoto(int photoIndex) {
		if (photoIndex < photoList.size() && photoIndex >= 0) {
			return photoList.get(photoIndex);
		}
		return null;
	}
}
