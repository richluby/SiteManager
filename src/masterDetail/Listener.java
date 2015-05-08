package masterDetail;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;

/**
 * this class contains the listeners used throughout the detail pane
 * @author Richard Luby, Copyright 2015
 */
public class Listener<T extends Tabulate> {

	/**
	 * the controller for this instance
	 */
	private T controller;
	/**
	 * the Jtable associated with this listener object
	 */
	private final JTable dataTable;
	/**
	 * initializes the class
	 * @param controller the controller from the master-detail view
	 * @param dataJTable the jtable that contains the data
	 */
	public Listener(T controller, JTable dataJTable) {
		this.controller = controller;
		this.dataTable = dataJTable;
	}
	/**
	 * sets the controller for this table, and fires a property change event
	 * @param controller the new controller to set
	 */
	public void setController(T controller) {
		this.controller = controller;
	}
	/**
	 * creates a listener to add an element to the controller
	 * @return returns a listener that instructs the controller to add an element
	 */
	public MouseListener mouseCreateAddElement() {
		return new MouseInputListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && controller != null) {//check for left click
					controller.addElement();
					((DefaultTableModel) dataTable.getModel()).fireTableDataChanged();
				}
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

			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

		};
	}

	MouseListener mouseCreateRemoveElement() {
		return new MouseInputListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && controller != null) {//check for left click
					int rowIndex = dataTable.getSelectedRow();
					//int correctRow = dataTable.convertColumnIndexToModel(rowIndex);
					if (rowIndex >= 0) {
						controller.removeElement(rowIndex);
						((DefaultTableModel) dataTable.getModel()).fireTableDataChanged();
					}
				}
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

			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		};
	}

}
