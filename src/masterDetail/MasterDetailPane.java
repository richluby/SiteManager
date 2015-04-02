package masterDetail;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

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
	 * the JTable used in this pane
	 */
	private JTable masterTable;
	/**
	 * the listener object used for this pane
	 */
	private Listener listener;
	/**
	 * the controller model for this Pane
	 */
	private T controller;

	/**
	 * initializes the JTable for the class. the RIGHT component must be set manually
	 * @param controller the object controller for this Pane
	 */
	public MasterDetailPane(T controller) {
		super();
		this.controller = controller;
		listener = new Listener(controller);
		setupTable();
	}
	/**
	 * sets up the table for this Pane, and sets it as the left component of the pane
	 */
	private void setupTable() {
		masterTable = new JTable(new MasterTable(controller)) {
			/**
			 * Create alternating, colored rows, with the active row a different color
			 * @return returns a renderer that creates alternating row colors, with the
			 *         active row a third color
			 */
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row,
											 int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				//  Alternate row color, set active row to med Gray
				if (!isRowSelected(row)) {
					c.setBackground((row % 2) == 0 ? getBackground() : Color.LIGHT_GRAY);
				} else {
					c.setBackground(Color.GRAY);
				}
				return c;
			}

		};
		masterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//movieTable.setCellSelectionEnabled(true);
		masterTable.setRowSelectionAllowed(true);
		masterTable.setRowSelectionInterval(0, 0);
		masterTable.setUpdateSelectionOnSort(true);
		masterTable.setFocusable(true);
		masterTable.setShowGrid(true);
		masterTable.setFillsViewportHeight(true);
		masterTable.setColumnSelectionAllowed(false);
		masterTable.setAutoCreateRowSorter(true);
		masterTable.getTableHeader().setReorderingAllowed(false);
		//resultsTable.setRowSelectionInterval(0, 0); //set selection to origin
		//movieTable.setColumnSelectionInterval(0, 0);
		TableRowSorter<MasterTable> sorter = new TableRowSorter<>((MasterTable) masterTable.getModel());
		masterTable.setRowSorter(sorter);
		setupPopupMenu();
		tableScroller = new JScrollPane(masterTable);
		setLeftComponent(tableScroller);
	}

	/**
	 * tells the pane to update the table due to data modification
	 */
	public void fireTableDataChanged() {
		((DefaultTableModel) masterTable.getModel()).fireTableDataChanged();
	}

	private void setupPopupMenu() {
		JPopupMenu rightClick = new JPopupMenu();
		JMenuItem addElement = new JMenuItem("Add...");
		addElement.addMouseListener(listener.mouseCreateAddElement());
		rightClick.add(addElement);
		JMenuItem removeElement = new JMenuItem("Remove...");
		removeElement.addMouseListener(listener.mouseCreateRemoveElement());
		rightClick.add(removeElement);
		masterTable.setComponentPopupMenu(rightClick);
	}
}
