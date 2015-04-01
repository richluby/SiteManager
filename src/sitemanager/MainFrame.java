package sitemanager;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * the main application window
 * @author Richard Luby, Copyright 2015
 */
class MainFrame extends JFrame {

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

		setVisible(true);
	}

}
