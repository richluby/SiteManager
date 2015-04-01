package sitemanager;

import help.HelpClass;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * the main application window
 * @author Richard Luby, Copyright 2015
 */
public class MainFrame extends JFrame {
	/**
	 * the controller to use for the application
	 */
	private Controller controller;
	/**
	 * the panel used to notify the user without interrupting
	 */
	private NotificationPanel notifPanel;
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
		setLayout(new BorderLayout(10, 10));
		controller = new Controller();
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
		add(new MainPanel(), BorderLayout.CENTER);
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
