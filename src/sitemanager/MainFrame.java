package sitemanager;

import help.HelpClass;
import masterDetail.MasterDetailPane;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.io.File;

/**
 * the main application window
 * <p>
 *
 * @author Richard Luby, Copyright 2015
 */
public class MainFrame extends JFrame{

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
	 * <tt>album.css</tt> is in "/"
	 */
	private File rootSiteFolder;
	/**
	 * the master-detail panel for the site management
	 */
	private MasterDetailPane<SiteController> siteMasterDetailPane;

	/**
	 * creates the main application window
	 */
	public MainFrame(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try{
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
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
		addWindowListener(Listeners.createUpdateOnClose());
		setVisible(true);
	}

	/**
	 * generates a menu bar and adds it to the program
	 */
	private void initMenuBar(){
		MenuBar menuBar = new MenuBar();
		//set up File menu
		Menu fileMenu = new Menu("File");
		MenuItem menuItem = new MenuItem("Choose Site Folder");
		menuItem.addActionListener(Listeners.createRootFolderForSiteChooser());
		fileMenu.add(menuItem);

		menuItem = new MenuItem("Generate HTML");
		menuItem.addActionListener(Listeners.createGenerateHTML());
		fileMenu.add(menuItem);

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
	private void initMainPanel(){
		JTabbedPane tabbedPane = new javax.swing.JTabbedPane();
		initSitePanel(tabbedPane);
		add(tabbedPane, BorderLayout.CENTER);
	}

	/**
	 * generates and adds a site management pane to the pane
	 * <p>
	 *
	 * @param pane the pane to which to add the site managing layout
	 */
	private void initSitePanel(JTabbedPane tabbedPane){
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
	private void initNotificationArea(){
		notifPanel = new NotificationPanel();
		notifPanel.setMessage("Welcome to the Site Manager Application");
		add(notifPanel, BorderLayout.SOUTH);
	}

	/**
	 * sets the notification to the user
	 * <p>
	 *
	 * @param message the message to send to the user
	 */
	public void setNotification(String message){
		notifPanel.setMessage(message);
	}

	/**
	 * instructs the album table to update due to data changes
	 */
	public void fireAlbumTableDataChanged(){
		siteMasterDetailPane.fireTableDataChanged();
	}

	/**
	 * instructs the update the active album with information from the display
	 */
	void updateActiveAlbum(){
		siteController.updateActiveElement();
	}

	/**
	 * returns the current site controller for this application
	 * <p>
	 *
	 * @return the active site controller for the application
	 */
	public SiteController getSiteController(){
		return siteController;
	}
}
