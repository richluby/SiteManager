/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitemanager;

import java.util.ArrayList;
import javax.swing.JPanel;
import masterDetail.Tabulate;

/**
 *
 * @author RLuby
 */
public class SiteController implements Tabulate {

	/**
	 * the array containing the column names for this class
	 */
	private static final String COLUMN_NAMES[] = new String[]{"Album Name", "Album Date"};

	/**
	 * contains the list of albums
	 */
	private ArrayList<Album> albumList;

	/**
	 * initializes the controller with relevant data
	 */
	public SiteController() {
		albumList = new ArrayList<>(30);
	}

	@Override
	public Object getDataForColumn(int rowIndex, int columnIndex) {
		if (COLUMN_NAMES[columnIndex].equals("Album Name")) {
			return albumList.get(rowIndex);
		} else if (COLUMN_NAMES[columnIndex].equals("Album Date")) {

		}
		return "";
	}

	@Override
	public String[] getColumnNames() {
		return COLUMN_NAMES;
	}

	@Override
	public int getNumberOfRows() {
		return albumList.size();
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return String.class;
	}

	@Override
	public void removeElement(int rowIndex) {

	}

	@Override
	public void addElement() {
	}

	@Override
	public void updateElement(int rowIndex) {
	}

	@Override
	public void updateDisplayForElement(int rowIndex) {
	}

	@Override
	public JPanel initDetailComponent() {
	}

}
