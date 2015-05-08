package sitemanager;

import help.HelpClass;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import masterDetail.MasterDetailPane;

/**
 * the main application window
 * <p>
 * @author Richard Luby, Copyright 2015
 */
public class MainFrame extends JFrame {
	/**
	 * the location at which to start the divided
	 */
	public final static double DIVIDER_LOCATION = .4;
	/**
	 * the controller to use for the application
	 */
	private SiteController siteController;
	/**
	 * the panel used to notify the user without interrupting
	 */
	private NotificationPanel notifPanel;
	/**
	 * the root folder of the site. this folder must follow the correct directory
	 * structure.
	 * <p>
	 * All album data is under "/albumData"
	 * <p>
	 * All files loaded in the index page are under "/indexData/", except for album cover
	 * images
	 * <p>
	 * <tt>main.css</tt> is in "/"
	 * <p>
	 * <tt>album.css</tt> is in "/albumData/"
	 */
	private File rootSiteFolder;
	/**
	 * the master-detail panel for the site management
	 */
	private MasterDetailPane<SiteController> siteMasterDetailPane;

	/**
	 * creates the main application window
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Dimension mainSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(mainSize.width * 10 / 15, mainSize.height * 3 / 4);
		int x = (mainSize.width - getWidth()) / 2, y = (mainSize.height - getHeight()) / 2;
		setLocation(x, y);
		setTitle("Account Manager");
		BorderLayout layout = new BorderLayout(10, 10);
		rootSiteFolder = null;
		Listeners.setMainFrame(this);
		setLayout(layout);
		initMenuBar();
		initMainPanel();
		initNotificationArea();
		setVisible(true);
	}

	/**
	 * generates a menu bar and adds it to the program
	 */
	private void initMenuBar() {
		MenuBar menuBar = new MenuBar();
		//set up File menu
		Menu fileMenu = new Menu("File");
		MenuItem chooseAlbum = new MenuItem("Choose Site Folder");
		chooseAlbum.addActionListener(Listeners.createRootFolderForSiteChooser());
		fileMenu.add(chooseAlbum);
		menuBar.add(fileMenu);
		//set up Help menu
		Menu helpMenu = new Menu("Help");
		MenuItem viewHelp = new MenuItem("Help...");
		viewHelp.addActionListener(HelpClass.createNewHelpWindowListener(this));
		helpMenu.add(viewHelp);
		menuBar.add(helpMenu);

		setMenuBar(menuBar);
	}

	/**
	 * generates and adds the main panel to the application
	 */
	private void initMainPanel() {
		JTabbedPane tabbedPane = new javax.swing.JTabbedPane();
		initSitePanel(tabbedPane);
		add(tabbedPane, BorderLayout.CENTER);
	}

	/**
	 * generates and adds a site management pane to the pane
	 * <p>
	 * @param pane the pane to which to add the site managing layout
	 */
	private void initSitePanel(JTabbedPane tabbedPane) {
		siteController = new SiteController(this);
		siteMasterDetailPane = new MasterDetailPane(siteController);
		siteMasterDetailPane.setDividerLocation((int) (getWidth() * DIVIDER_LOCATION));
		tabbedPane.addTab("Site", siteMasterDetailPane);

		tabbedPane.addTab("Accounts", new JPanel());
	}

	/**
	 * generates and places a JPanel at the base of the program window to display
	 * notifications to the user
	 */
	private void initNotificationArea() {
		notifPanel = new NotificationPanel();
		notifPanel.setMessage("Welcome to the Site Manager Application");
		add(notifPanel, BorderLayout.SOUTH);
	}
	/**
	 * sets the notification to the user
	 * @param message the message to send to the user
	 */
	public void setNotification(String message) {
		notifPanel.setMessage(message);
	}

	/**
	 * loads the site controller with data from the currently selected root folder
	 */
	public void loadSiteControllerData() {
		siteController.setRootSiteFolder(new File(
				rootSiteFolder.getAbsoluteFile() + File.separator + "albumData"));
		Thread thread = new Thread(siteController, "initSiteControllerData");
		thread.start();
	}
	/**
	 * sets the root folder for a site/album, and updates the controller accordingly
	 * @param selectedFile the directory at the root of the album
	 */
	public void setRootFolderForSite(File selectedFile) {
		rootSiteFolder = selectedFile;
	}
	/**
	 * sets the location for the currently active album
	 * @param albumFolder the folder to set as the location for the active album
	 */
	void setAlbumLocation(File albumFolder) {
		siteController.setAlbumLocation(albumFolder);
	}
	/**
	 * instructs the album table to update due to data changes
	 */
	public void fireAlbumTableDataChanged() {
		siteMasterDetailPane.fireTableDataChanged();
	}
}
