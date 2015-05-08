package sitemanager;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class contains the frame work to display information common to both albums and
 * photos
 * @author Richard Luby, Copyright 2015
 */
class InformationPanel extends JPanel {

	/**
	 * the textBox for the title
	 */
	private JTextField titleField;
	/**
	 * the textBox for the location of this item
	 */
	private JTextField locationField;
	/**
	 * the text box for the description of this item
	 */
	private JTextArea descriptionArea;

	/**
	 * initializes the class
	 */
	public InformationPanel() {
		titleField = new JTextField();
		locationField = new JTextField();
		descriptionArea = new JTextArea();
	}

}
