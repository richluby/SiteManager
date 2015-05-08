package sitemanager;

import help.HelpClass;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import javax.swing.JFrame;
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
	 * the controller to use for the application
	 */
	private SiteController siteController;
	/**
	 * the panel used to notify the user without interrupting
	 */
	private NotificationPanel notifPanel;
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
		siteController = new SiteController();
		siteMasterDetailPane = new MasterDetailPane(siteController);
		tabbedPane.addTab("Site", siteMasterDetailPane);
	}

	/**
	 * generates and places a JPanel at the base of the program window to display
	 * notifications to the user
	 */
	private void initNotificationArea() {
		notifPanel = new NotificationPanel();
		notifPanel.setMessage("Welcome...");
		add(notifPanel, BorderLayout.SOUTH);
	}

}
