package masterDetail;

import javax.swing.*;

/**
 * this interface is designed to be implemented by an object, most likely a Model or
 * Controller object. The interface allows an * object to be applied directly to the master-detail pane.
 *
 * @author Richard Luby, Copyright 2015
 * This code may be redistributed, modified, or used in other projects provided that
 * this notice is included, and any contributing authors are mentioned.
 */
public interface Tabulate{

	/**
	 * retrieves the data for use in the master-detail pan
	 *
	 * @param rowIndex    the row index for this piece of data
	 * @param columnIndex the column index for this piece of data
	 * @return e
	 */
	Object getDataForColumn(int rowIndex, int columnIndex);

	/**
	 * retrieves the column name
	 *
	 * @return returns the names of the columns. The order of the elements is initially
	 * kept, and will remain constant throughout the program operation.
	 */
	String[] getColumnNames();

	/**
	 * retrieves the number of rows in this table
	 *
	 * @return returns the number of elements for the data set in this table
	 */
	int getNumberOfRows();

	/**
	 * returns the class for this column. this method allows the table to display a
	 * different renderer per column
	 *
	 * @param col the view column index
	 * @return the class type for this cell
	 */
	Class<?> getColumnClass(int col);

	/**
	 * instructs the controller to remove the element at the specified row index
	 *
	 * @param rowIndex the row index selected for removal
	 */
	void removeElement(int rowIndex);

	/**
	 * instructs the controller to add an element based on the
	 * current values in the
	 * right-side component. This method should also fire the <tt>tableDataChanged</tt>
	 * for the pane.
	 */
	void addElement();

	/**
	 * instructs the controller to update the element model at the specified row index
	 * based off	 * the current values in the right-side component
	 *
	 * @param rowIndex the rowIndex that should be updated
	 */
	void updateElement(int rowIndex);

	/**
	 * instructs the controller to load the data for the element into the right-component
	 * view and fire a repaint on the right-component
	 *
	 * @param rowIndex the row index of the element to be displayed
	 */
	void updateDisplayForElement(int rowIndex);

	/**
	 * retrieves the right-side component of the detail pane. the Controller should handle
	 * any updates to this component
	 *
	 * @return returns a Jpanel properly prepared for display
	 */
	JPanel initDetailComponent();
}
