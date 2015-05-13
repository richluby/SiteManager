package sitemanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
			File rootFolder = openDialogChooser(FILE_TYPE.DIRECTORY,
				"Choose Root Folder", new File(System.getProperty("user.home")));
			if (rootFolder != null) {
				mainFrame.getSiteController().setRootSiteFolder(rootFolder);
			}
		};
	}

	/**
	 * returns a listener that displays a dialog to select a directory
	 */
	static ActionListener createBrowseForAlbumFolder() {
		return (ActionEvent e) -> {
			File albumFolder = openDialogChooser(FILE_TYPE.DIRECTORY,
				"Choose Album Folder", mainFrame.getSiteController().getRootSiteFolder());
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
			mainFrame.getSiteController().updateActiveElement();
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
			File file = openDialogChooser(FILE_TYPE.FILE, "Choose an Album Cover", mainFrame.getSiteController().getActiveAlbum().getLocationFile());
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
	private static File openDialogChooser(FILE_TYPE fileType, String title, File startFolder) {
		JFileChooser chooser = new JFileChooser(startFolder);
		chooser.setDialogTitle(title);
		if (fileType == FILE_TYPE.DIRECTORY) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else if (fileType == FILE_TYPE.FILE) {
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		}
		chooser.setMultiSelectionEnabled(false);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}

	/**
	 * creates a listener that updates the album file on close
	 * <p>
	 * @return the listener that executes on program close. this listener instructs the
	 *         controller to write information to the disk
	 */
	static WindowListener createUpdateOnClose() {
		return new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.getSiteController().writeInformationToDisk();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		};
	}

	/**
	 * creates a dialog that allows a user to browse for a different photo location
	 * <p>
	 * @return allows the user to select a photo to use in the active album
	 */
	static ActionListener createBrowseForPhoto() {
		return (ActionEvent e) -> {
			PhotoController controller = mainFrame.getSiteController().getActiveAlbum().getPhotoController();
			File image = openDialogChooser(FILE_TYPE.FILE, "Choose a Photo", mainFrame.getSiteController().getActiveAlbum().getLocationFile());
			if (image != null) {
				controller.setPhotoLocationForActivePhoto(image);
			}
		};
	}

	/**
	 * saves the information for a photo from the display to the model data behind the
	 * display
	 * <p>
	 * @return a listener designed to update the active photo with the current display
	 *         information
	 */
	static ActionListener creatUpdatePhoto() {
		return (ActionEvent e) -> {
			mainFrame.getSiteController().getActiveAlbum().getPhotoController().updateActiveElement();
		};
	}

	/**
	 * creates a listener that starts building html
	 * <p>
	 * @return listener to start building hmtl
	 */
	static ActionListener createGenerateHTML() {
		return (ActionEvent e) -> {
			File siteFolder = openDialogChooser(FILE_TYPE.DIRECTORY, "Choose a Site Folder", mainFrame.getSiteController().getRootSiteFolder());
			if (siteFolder != null) {
				HTMLGenerator generator = new HTMLGenerator(mainFrame);
				generator.generateHTMLData(siteFolder);
			}
		};
	}
}
