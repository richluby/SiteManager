package sitemanager;

import java.io.File;
import java.util.ArrayList;
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
	 * the name of the file in which to look for information regarding photos. This file
	 * must be in the same folder as the albumFolder passed to the controller
	 */
	private final static String FILE_PHOTO_INFORMATION = "PhotoData";
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
			informationPanel = new InformationPanel("Photo Information", null, null);
		}
	}

	@Override
	public Object getDataForColumn(int rowIndex, int columnIndex) {
		switch (COLUMN_NAMES[columnIndex]) {
			case "Name":
				return photoList.get(rowIndex).getPhotoName();
			case "Location":
				return photoList.get(rowIndex).getPhotoFile().getAbsolutePath();
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
			Photo activePhoto = photoList.get(rowIndex);
			informationPanel.setTitle(activePhoto.getPhotoName());
			informationPanel.setDescription(activePhoto.getPhotoDescription());
			informationPanel.setLocation(activePhoto.getPhotoFile().getAbsolutePath());
			informationPanel.setDisplayedImage(activePhoto.getPhotoFile(), "south", .8, .4);
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

	}
}
