package masterDetail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;

/**
 * this class contains the listeners used throughout the detail pane
 * <p>
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
	 * <p>
	 * @param controller the controller from the master-detail view
	 * @param dataJTable the jtable that contains the data
	 */
	public Listener(T controller, JTable dataJTable) {
		this.controller = controller;
		this.dataTable = dataJTable;
	}

	/**
	 * sets the controller for this table, and fires a property change event
	 * <p>
	 * @param controller the new controller to set
	 */
	public void setController(T controller) {
		this.controller = controller;
	}

	/**
	 * creates a listener to add an element to the controller
	 * <p>
	 * @return returns a listener that instructs the controller to add an element
	 */
	public MouseListener mouseCreateAddElement() {
		return new MouseInputListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && controller != null) {//check for left click
					JPopupMenu callingMenu = ((JPopupMenu) ((JMenuItem) e.getSource()).getParent());
					callingMenu.setVisible(false);
					callingMenu.setFocusable(false);
					controller.addElement();
					((DefaultTableModel) dataTable.getModel()).fireTableDataChanged();
					//callingMenu.setFocusable(true);
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

	/**
	 * instructs the controller to change the display for the right side component due to
	 * a selection change in the table
	 */
	ListSelectionListener createSelectionChange() {
		return (ListSelectionEvent e) -> {
			int rowIndex = dataTable.getSelectedRow();
			if (controller != null && rowIndex < controller.getNumberOfRows() && rowIndex >= 0) {//check for left click
				rowIndex = dataTable.convertRowIndexToModel(rowIndex);
				if (rowIndex >= 0) {
					controller.updateDisplayForElement(rowIndex);
				}
			}
		};
	}

	/**
	 * instructs the controller to remove an element from the data model, and then this
	 * method fires the property change event
	 */
	MouseListener mouseCreateRemoveElement() {
		return new MouseInputListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && controller != null) {//check for left click
					int rowIndex = dataTable.getSelectedRow();
					System.out.println("Row: " + rowIndex);
					//int correctRow = dataTable.convertColumnIndexToModel(rowIndex);
					if (rowIndex >= 0) {
						JPopupMenu callingMenu = ((JPopupMenu) ((JMenuItem) e.getSource()).getParent());
						callingMenu.setVisible(false);
						callingMenu.setFocusable(false);
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

	/**
	 * instructs the controller to update the information for the element at the active
	 * selection index
	 * <p>
	 * @return returns a listener that calls the <tt>upDateElemnt(int)</tt> method in the
	 *         controller
	 */
	public ActionListener createSaveElementInformation() {
		return (ActionEvent e) -> {
			int rowIndex = dataTable.getSelectedRow();
			if (rowIndex >= 0 && rowIndex < dataTable.getRowCount()) {
				controller.updateElement(rowIndex);
				((DefaultTableModel) dataTable.getModel()).fireTableDataChanged();
			}
		};
	}
}
