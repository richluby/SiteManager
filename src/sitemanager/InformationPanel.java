package sitemanager;

import javax.swing.JLabel;
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
		JLabel tempLabel = new JLabel("Title: ");
		add(tempLabel);
		add(titleField);
		tempLabel = new JLabel("Location: ");
		add(tempLabel);
		add(locationField);
		tempLabel = new JLabel("Description: ");
		add(tempLabel);
		add(descriptionArea);
	}
	/**
	 * sets the title of this item
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		titleField.setText(title);
	}

	/**
	 * sets the description of this item
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		descriptionArea.setText(description);
	}

	/**
	 * sets the description of this item
	 * @param description the description to set
	 */
	public void setLocation(String location) {
		locationField.setText(location);
	}

	/**
	 * returns the text in the title of this item
	 * @return returns the text in the titleBox of this item
	 */
	public String getTitle() {
		return titleField.getText();
	}

	/**
	 * returns the field in the description box of this item
	 * @return returns the current description text of this item
	 */
	public String getDescription() {
		return descriptionArea.getText();
	}
}
