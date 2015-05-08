/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitemanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JPanel;
import masterDetail.MasterDetailPane;
import masterDetail.Tabulate;

/**
 *
 * @author RLuby
 */
public class SiteController implements Tabulate, Runnable {
	/**
	 * the array containing the column names for this class
	 */
	private static final String COLUMN_NAMES[] = new String[]{"Album Name", "Album Date"};
	/**
	 * contains the list of albums
	 */
	private ArrayList<Album> albumList;
	/**
	 * the jpanel that handles the album information
	 */
	private InformationPanel albumPanel;
	/**
	 * the frame in charge of this controller. this is used to allow the controller to
	 * display notifications
	 */
	private MainFrame mainFrame;

	/**
	 * the folder for the website. The controller expects album data to be organized in a
	 * separate folder
	 */
	private File rootAlbumFolder;
	/**
	 * the master detail pane for the photos. The siteController is responsible for
	 * ensuring that it has the controller for the currently selected album
	 */
	private MasterDetailPane<PhotoController> photoPane;

	/**
	 * initializes the controller with relevant data
	 * @param mainFrame the application window responsible for this controller
	 */
	public SiteController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		albumList = new ArrayList<>(30);
		rootAlbumFolder = null;
	}

	/**
	 * sets the root album folder for this controller. The controller will NOT
	 * automatically reload information from the new folder
	 * @param rootAlbumFolder the location to set the root album folder
	 */
	public void setRootAlbumFolder(File rootAlbumFolder) {
		this.rootAlbumFolder = rootAlbumFolder;
	}

	@Override
	public Object getDataForColumn(int rowIndex, int columnIndex) {
		switch (COLUMN_NAMES[columnIndex]) {
			case "Album Name":
				return albumList.get(rowIndex).getAlbumName();
			case "Album Date":
				return albumList.get(rowIndex).getAlbumDescription();
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
		return albumList.size();
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return String.class;
	}

	@Override
	public void removeElement(int rowIndex) {
		System.out.println("removed: " + rowIndex);
	}

	@Override
	public void addElement() {
		System.out.println("Added");
	}

	@Override
	public void updateElement(int rowIndex) {
	}

	@Override
	public void updateDisplayForElement(int rowIndex) {
	}

	@Override
	public JPanel initDetailComponent() {
		JPanel detailPanel = new JPanel(new GridBagLayout());
		albumPanel = new InformationPanel("Album Information");
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.weighty = .2;
		gbConstraints.gridy = 0;
		gbConstraints.weightx = 1;
		gbConstraints.fill = GridBagConstraints.BOTH;
		detailPanel.add(albumPanel, gbConstraints);

		photoPane = new MasterDetailPane<>(new PhotoController());
		gbConstraints.weighty = .8;
		gbConstraints.gridy = 1;
		detailPanel.add(photoPane, gbConstraints);
		return detailPanel;
	}

	/**
	 * populates the controller with the correct album data given a root site folder. This
	 * implementation will not run if there is no root folder, or if <tt>rootFolder</tt>
	 * is null. A separate call must be made to set the root folder. This allows simple
	 * refreshing of the data due to external file changes.
	 */
	@Override
	public void run() {
		if (rootAlbumFolder != null) {

		mainFrame.setNotification(
					"Album data from" + rootAlbumFolder.getName() + " has been loaded into memory.");
		}
	}

}
