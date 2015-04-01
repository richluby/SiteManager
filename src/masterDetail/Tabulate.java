package masterDetail;

/**
 * this interface is designed to be implemented by an object, most likely a Model or
 * Controller object. The interface allows an * object to be applied directly to the master-detail pane.
 * @author Richard Luby, Copyright 2015
 * @param <T> the object class being tabulated
 */
public interface Tabulate<T> {

	/**
	 * retrieves the data for use in the master-detail pan
	 * @param rowIndex    the row index for this piece of data
	 * @param columnIndex the column index for this piece of data
	 * @return e
	 */
	public Object getDataForColumn(int rowIndex, int columnIndex);

	/**
	 * retrieves the column name
	 * @return returns the names of the columns. The order of the elements is initially
	 *         kept, and will remain constant throughout the program operation.
	 */
	public String[] getColumnNames();

	/**
	 * retrieves the number of rows in this table
	 * @return returns the number of elements for the data set in this table
	 */
	public int getNumberOfRows();

	/**
	 * returns the class for this column. this method allows the table to display a
	 * different renderer per column
	 * @param col the view column index
	 * @return the class type for this cell
	 */
	public Class<?> getColumnClass(int col);
}
