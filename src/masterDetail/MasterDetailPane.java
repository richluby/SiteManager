package masterDetail;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * This class embeds a Master-Detail view JSplitPane.
 * @author Richard Luby, Copyright 2015
 */
public class MasterDetailPane<T extends Tabulate> extends JSplitPane {
	/**
	 * the left panel with the jtable
	 */
	private JScrollPane tableScroller;
	/**
	 * the controller model for this Pane
	 */
	private T controller;

	/**
	 * initializes the class
	 * @param controller the object controller for this Pane
	 */
	public MasterDetailPane(T controller) {
		super();
		this.controller = controller;
		setupTable();
	}
	/**
	 * sets up the table for this Pane
	 */
	private void setupTable() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
