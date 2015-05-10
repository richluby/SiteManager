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
	 *
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
				mainFrame.setAlbumLocation(albumFolder);
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
