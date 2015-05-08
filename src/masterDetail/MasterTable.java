package masterDetail;

import javax.swing.table.DefaultTableModel;

/**
 * this class uses generics in order to work with the Tabulate interface
 * @author Richard Luby, Copyright 2015
 * @param <T> the Object Controller that contains the data for this table
 */
public class MasterTable<T extends Tabulate> extends DefaultTableModel {
	/**
	 * the controller object used for this table. this parameter is not optional. When
	 * data changes, the table will not automatically recognize it.
	 */
	private T controller;

	/**
	 * initializes the table
	 * @param controller the controller object for this table
	 */
	public MasterTable(T controller) {
		super();
		this.controller = controller;
	}

	/**
	 * returns the number of columns in the table
	 * @return returns the number of columns in the table
	 */
	@Override
	public int getColumnCount() {
		return controller.getColumnNames().length;
	}

	/**
	 * returns the name of the column at the index
	 * @param col the index of the column
	 * @return returns the name of the column at col
	 */
	@Override
	public String getColumnName(int col) {
		return controller.getColumnNames()[col];
	}

	/**
	 * returns the number of rows in the table
	 * @return returns the number of rows in the table
	 */
	@Override
	public int getRowCount() {
		if (controller == null) {
			return 0;
		}
		return controller.getNumberOfRows();
	}

	/**
	 * returns the class for this cell
	 * @return the class type for this cell
	 */
	//@Override public Class<?> getColumnClass(int col){
	//    if (columnNames[tracker.getMainFrame().getDataTable()
	//                    .convertColumnIndexToModel(col)].equals("Rented")) { return Boolean.class; }
	//    return Object.class;
	//}
	/**
	 * function to disallow editing of all table contents
	 * @return returns false if this cell is not editable
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	/**
	 * function to change table contents
	 * @param in  the object that is being passed to set
	 * @param row the view row
	 * @param col the view column
	 */
	/*@Override
		public void setValueAt(Object in, int row, int col) {
		Boolean newValue = (Boolean) in;
		if (columnNames[col].contains("Attended")) {
			if (newValue) {
				cadetList.get(row).incrementAttendanceCount();
			} else {
				cadetList.get(row).decrementAttendanceCount();
			}
		} else if (columnNames[col].contains("Memo")) {
			cadetList.get(row).setHasMemo(newValue);
		}
		fireTableDataChanged();
	}*/

	/**
	 * returns the class for this cell. this method allows the table to display checkboxes
	 * for attendance
	 * @param col the view column index
	 * @return the class type for this cell
	 */
	@Override
	public Class<?> getColumnClass(int col) {
		return controller.getColumnClass(col);
	}

	/**
	 * returns the object at (row,column)
	 * @return returns the object at (row, column)
	 */
	@Override
	public Object getValueAt(int row, int column) {
		return controller.getDataForColumn(row, column);
	}

	/**
	 * sets the controller for this table, and fires a property change event
	 * @param controller the new controller to set
	 */
	public void setController(T controller) {
		this.controller = controller;
		fireTableDataChanged();
	}
}
