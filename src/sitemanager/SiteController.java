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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import masterDetail.MasterDetailPane;
import masterDetail.Tabulate;

/**
 * This class controls the list of albums maintained by the program. It searches for album
 * information in <tt>ALBUM_DATA_FILE_NAME</tt>.
 * @author RLuby
 */
public class SiteController implements Tabulate, Runnable {
	/**
	 * the array containing the column names for this class
	 */
	private static final String COLUMN_NAMES[] = new String[]{"Album Name", "Album Date"};
	/**
	 * the name of the file that this class checks in order to discover album data.
	 */
	private static final String ALBUM_DATA_FILE_NAME = "AlbumData";
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
	 * subfolder for automatic parsing purposes
	 */
	private File rootSiteFolder;
	/**
	 * the master detail pane for the photos. The siteController is responsible for
	 * ensuring that it has the controller for the currently selected album
	 */
	private MasterDetailPane<PhotoController> photoPane;
	/**
	 * the index of the currently selected album
	 */
	private int indexOfCurrentAlbum;

	/**
	 * initializes the controller with relevant data
	 * @param mainFrame the application window responsible for this controller
	 */
	public SiteController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		albumList = new ArrayList<>(30);
		rootSiteFolder = null;
		indexOfCurrentAlbum = 0;
	}

	/**
	 * sets the root site folder for this controller. The controller will
	 * automatically reload information from the new folder
	 * @param rootAlbumFolder the location to set the root site folder
	 */
	public void setRootSiteFolder(File rootAlbumFolder) {
		this.rootSiteFolder = rootAlbumFolder;
		Thread thread = new Thread(this);
		thread.start();
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
		Album album = new Album();
		String albumName = JOptionPane.showInputDialog(mainFrame,
													   "Enter a name for this album:");
		if (albumName != null && !albumName.equals("")) {
			album.setAlbumName(albumName);
			albumList.add(album);
		}

	}

	@Override
	public void updateElement(int rowIndex) {
	}

	@Override
	public void updateDisplayForElement(int rowIndex) {
		indexOfCurrentAlbum = rowIndex;
	}

	@Override
	public JPanel initDetailComponent() {
		JPanel detailPanel = new JPanel(new GridBagLayout());
		albumPanel = new InformationPanel("Album Information",
										  Listeners.createBrowseForAlbumFolder(),
										  Listeners.createSaveAlbumInformation());
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
		if (rootSiteFolder != null) {
			populateAlbumList();
			SwingUtilities.invokeLater(() -> {
				mainFrame.setNotification(
						"Album data from \"" + rootSiteFolder.getAbsolutePath() + "\" has been loaded into memory.");
				mainFrame.fireAlbumTableDataChanged();
			});
		}
	}
	/**
	 * reads the file <tt>ALBUM_DATA_FILE_NAME</tt> to discern album information. It will
	 * also examine the directory for unlisted albums.
	 */
	private void populateAlbumList() {
		//read files HERE
	}

	/**
	 * sets the location of the currently active album, and updates the text field
	 */
	void setAlbumLocation(File albumFolder) {
		if (indexOfCurrentAlbum < albumList.size() && albumFolder != null) {
			albumPanel.setLocation(albumFolder.getAbsolutePath());
			albumList.get(indexOfCurrentAlbum).setAlbumFolder(albumFolder);
			mainFrame.setNotification(
					"Album location set to \"" + albumFolder.getAbsolutePath() + "\"");
		} else {
			mainFrame.setNotification(
					"The selected album could not be found. Try choosing a different album.");
		}
	}

}
