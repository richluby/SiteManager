package sitemanager;

import java.awt.GridLayout;
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
		titleField = new JTextField(30);
		locationField = new JTextField(30);
		descriptionArea = new JTextArea(10, 30);
		GridLayout thisLayout = new GridLayout(3, 2, 10, 10);
		setLayout(thisLayout);

		JLabel tempLabel = new JLabel("Title: ");
		JPanel tempPanel = new JPanel();
		tempPanel.add(tempLabel);
		tempPanel.add(titleField);
		add(tempPanel);

		tempLabel = new JLabel("Location: ");
		tempPanel = new JPanel();
		tempPanel.add(tempLabel);
		tempPanel.add(locationField);
		add(tempPanel);

		tempLabel = new JLabel("Description: ");
		tempPanel = new JPanel();
		tempPanel.add(tempLabel);
		tempPanel.add(descriptionArea);
		add(tempPanel);
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
