package sitemanager;

import javax.swing.*;

/**
 * this class is designed as a notification area. it provides a simple api to allow
 * programmers to quickly create a notification area
 *
 * @author Richard Luby, Copyright 2015
 * This code may be redistributed, modified, or used in other projects provided that
 * this notice is included, and any contributing authors are mentioned.
 */
public class NotificationPanel extends JPanel{

	/**
	 * the label used to display messages for the user
	 */
	private JLabel messageLabel;

	/**
	 * initializes the message area with no message
	 */
	public NotificationPanel(){
		super();
		messageLabel = new JLabel();
		add(messageLabel);
	}

	/**
	 * sets the message to be displayed. if the message is the same as the current one,
	 * then the area will blink. The default number of blinks is 1.
	 *
	 * @param message the message to display
	 */
	public void setMessage(String message){
		messageLabel.invalidate();
		messageLabel.setText(message);
		messageLabel.revalidate();
		messageLabel.repaint();
	}
}
