package sitemanager;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JPanel;
import masterDetail.Tabulate;

/**
 * This class acts as a controller for all photos in a given album
 * @author Richard Luby, Copyright 2015
 */
class PhotoController implements Tabulate {

	/**
	 * the column names for the photos table
	 */
	private final static String[] COLUMN_NAMES = new String[]{"Name", "Location"};
	/**
	 * the folder in which these photos reside
	 */
	private File albumFolder;
	/**
	 * the list containing all photos managed by this controller
	 */
	private ArrayList<Photo> photoList;
	/**
	 * initializes the controller
	 */
	public PhotoController() {
		albumFolder = null;
		photoList = new ArrayList<>();
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
	}

	@Override
	public JPanel initDetailComponent() {
		return new InformationPanel();
	}
}
