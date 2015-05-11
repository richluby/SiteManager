package sitemanager;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

/**
 * This class contains the frame work to display information common to both albums and
 * photos
 * <p>
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
	 * the label to hold the image displayed in this item
	 */
	private JLabel imageLabel;

	/**
	 * initializes the class
	 * <p>
	 * @param title          the title to put around the border of this item
	 * @param browseListener the listener attached to the browse button
	 * @param saveListener   the listener attached to the save button. If this listener is
	 *                       null, then no save button is added.
	 */
	public InformationPanel(String title,
		ActionListener browseListener,
		ActionListener saveListener) {
		//init variables
		titleField = new JTextField();

		locationField = new JTextField(10);
		locationField.setFocusable(false);

		descriptionArea = new JTextArea(3, 5);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setLineWrap(true);
		//init layout
		MigLayout thisLayout = new MigLayout();
		//constraints.weightx = .2;
		setLayout(thisLayout);

		//populate panel
		JLabel tempLabel = new JLabel("Title: ");

		add(tempLabel);
		add(titleField, "span 4, growx, wrap");
		//add(tempPanel);

		tempLabel = new JLabel("Location: ");
		JButton button = new JButton("Browse");
		button.addActionListener(browseListener);
		add(tempLabel);
		add(locationField, "span 3");
		add(button, "wrap");
		//add(tempPanel);

		tempLabel = new JLabel("Description: ");
		add(tempLabel);
		JScrollPane scroller = new JScrollPane(descriptionArea);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		add(scroller, "span, grow, wrap");
		//add(tempPanel);
		if (saveListener != null) {
			button = new JButton("Save");
			button.addActionListener(saveListener);
			add(button, "skip 1");
		}
		setBorder(new TitledBorder(title));
	}

	/**
	 * sets the title of this item
	 * <p>
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		titleField.setText(title);
	}

	/**
	 * sets the photo displayed by this panel
	 * <p>
	 * @param photo       the photo to display
	 * @param constraints the constraints to be applied to this photo, based on
	 * <tt>MiGLayout</tt>
	 * @param widthScale  the proportion of horizontal space to use in the panel for the
	 *                    image
	 * @param heightScale the proportion of vertical space to use in the panel for the
	 *                    image
	 * @return returns true if the image was successfully set, false otherwise
	 */
	public boolean setDisplayedImage(File photo, String constraints, double widthScale,
		double heightScale) {
		try {
			BufferedImage img = ImageIO.read(photo);
			ImageIcon icon = new ImageIcon(img.getScaledInstance(
				(int) (getWidth() * widthScale),
				(int) (getHeight() * heightScale),
				BufferedImage.SCALE_DEFAULT));
			if (imageLabel == null) {
				imageLabel = new JLabel(icon);
				add(imageLabel, "gapleft 10px, " + constraints);
			} else {
				imageLabel.setIcon(icon);
				imageLabel.revalidate();
				imageLabel.repaint();
			}

			return true;
		} catch (IOException ex) {
			System.out.println("No image found for infoPanel");
			return false;
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println("Improper image format passed to infoPanel");
			imageLabel.removeAll();
			return false;
		}
	}

	/**
	 * sets the description of this item
	 * <p>
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		descriptionArea.setText(description);
	}

	/**
	 * sets the description of this item
	 * <p>
	 * @param description the description to set
	 */
	public void setLocation(String location) {
		locationField.setText(location);
	}

	/**
	 * returns the text in the title of this item
	 * <p>
	 * @return returns the text in the titleBox of this item
	 */
	public String getTitle() {
		return titleField.getText();
	}

	/**
	 * returns the field in the description box of this item
	 * <p>
	 * @return returns the current description text of this item
	 */
	public String getDescription() {
		return descriptionArea.getText();
	}
}
