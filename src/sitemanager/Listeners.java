package sitemanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * This class contains convenience methods for all the listeners in the program
 * <p>
 * @author Richard Luby, Copyright 2015
 */
public class Listeners {

	/**
	 * determines what type of file should be allowed in file choosers
	 */
	private static enum FILE_TYPE {

		DIRECTORY, FILE
	};

	/**
	 * the mainFrame of the application
	 */
	private static MainFrame mainFrame;

	static void setMainFrame(MainFrame mf) {
		mainFrame = mf;
	}

	/**
	 * creates a listener that selects the root folder for a website. This listener
	 * proceeds to load relevant data from that directory
	 * <p>
	 * @return returns a listener that causes the program to reload the active album
	 *         information
	 */
	static ActionListener createRootFolderForSiteChooser() {
		return (ActionEvent e) -> {
			File rootFolder = openDialogChooser(FILE_TYPE.DIRECTORY.ordinal(),
				"Choose Root Folder");
			if (rootFolder != null) {
				mainFrame.setRootFolderForSite(rootFolder);
				mainFrame.loadSiteControllerData();
			}
		};
	}

	/**
	 * returns a listener that displays a dialog to select a directory
	 */
	static ActionListener createBrowseForAlbumFolder() {
		return (ActionEvent e) -> {
			File albumFolder = openDialogChooser(FILE_TYPE.DIRECTORY.ordinal(),
				"Choose Album Folder");
			if (albumFolder != null) {
				mainFrame.getSiteController().setAlbumLocation(albumFolder);
			}
		};
	}

	/**
	 * instructs the update the active album with information from the display
	 * <p>
	 * @return returns a listener that instructs the controller to update the active labum
	 */
	static ActionListener createUpdateAlbum() {
		return (ActionEvent e) -> {
			mainFrame.getSiteController().updateDisplayForActiveElement();
			mainFrame.fireAlbumTableDataChanged();
		};
	}

	/**
	 * instructs the controller to update the active album with the selected album cover
	 * <p>
	 * @return returns a listener that instructs the controller to set the album cover of
	 *         the active album with a user selected file
	 */
	static ActionListener createBrowseForAlbumCover() {
		return (ActionEvent e) -> {
			File file = openDialogChooser(FILE_TYPE.FILE.ordinal(), "Choose an Album Cover");
			if (file != null) {
				mainFrame.getSiteController().setActiveAlbumCoverFile(file);
			}
		};
	}

	/**
	 * opens a JFileChooser with the given title that accepts single files only
	 * <p>
	 * @return returns the file selected from the user, or null if no file was selected
	 */
	private static File openDialogChooser(int fileType, String title) {
		JFileChooser chooser = new JFileChooser(new File("." + File.separator));
		chooser.setDialogTitle(title);
		if (fileType == FILE_TYPE.DIRECTORY.ordinal()) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else if (fileType == FILE_TYPE.FILE.ordinal()) {
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		}
		chooser.setMultiSelectionEnabled(false);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}

}
