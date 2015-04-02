package masterDetail;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * this class contains the listeners used throughout the detail pane
 * @author Richard Luby, Copyright 2015
 */
public class Listener<T extends Tabulate> {

	/**
	 * the controller for this instance
	 */
	private final T controller;

	/**
	 * initializes the class
	 * @param controller the controller from the master-detail view
	 */
	public Listener(T controller) {
		this.controller = controller;
	}

	/**
	 * creates a listener to add an element to the controller
	 * @return returns a listener that instructs the controller to add an element
	 */
	public MouseListener mouseCreateAddElement() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && controller != null) {//check for right click

				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		};
	}

	MouseListener mouseCreateRemoveElement() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && controller != null) {//check for right click

				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		};
	}

}
