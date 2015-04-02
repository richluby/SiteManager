package sitemanager;

import masterDetail.Tabulate;

/**
 * This class controls the data used throughout the program.
 * @author Richard Luby, Copyright 2015
 */
public class AccountsController implements Tabulate {

	@Override
	public Object getDataForColumn(int rowIndex, int columnIndex) {
		return "R: " + rowIndex + " C: " + columnIndex;
	}

	@Override
	public String[] getColumnNames() {
		return new String[]{"This", "That", "These"};
	}

	@Override
	public int getNumberOfRows() {
		return 10;
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return String.class;
	}

	@Override
	public void removeElement(int rowIndex) {
		System.out.println("Row " + rowIndex + " removed.");
	}

}
