package masterDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * This class embeds a Master-Detail view JSplitPane.
 * <p>
 *
 * @author Richard Luby, Copyright 2015
 * This code may be redistributed, modified, or used in other projects provided that
 * this notice is included, and any contributing authors are mentioned.
 */
public class MasterDetailPane<T extends Tabulate> extends JSplitPane{

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
	 * <p>
	 *
	 * @param controller the object controller for this Pane
	 */
	public MasterDetailPane(T controller){
		super();
		this.controller = controller;
		setRightComponent(controller.initDetailComponent());
		setupTable();
	}

	/**
	 * sets up the table for this Pane, and sets it as the left component of the pane
	 */
	private void setupTable(){
		masterTable = new JTable(new MasterTable(controller)){
			/**
			 * Create alternating, colored rows, with the active row a different color
			 * <p>
			 * @return returns a renderer that creates alternating row colors, with the
			 *         active row a third color
			 */
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row,
			                                 int column){
				Component c = super.prepareRenderer(renderer, row, column);

				//  Alternate row color, set active row to med Gray
				if(!isRowSelected(row)){
					c.setBackground((row % 2) == 0 ? getBackground() : Color.LIGHT_GRAY);
				} else{
					c.setBackground(Color.GRAY);
				}
				return c;
			}

		};
		masterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//movieTable.setCellSelectionEnabled(true);
		masterTable.setRowSelectionAllowed(true);
		if(masterTable.getModel().getRowCount() > 0){
			masterTable.setRowSelectionInterval(0, 0);
		}
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
		ListSelectionModel selectionModel = masterTable.getSelectionModel();
		selectionModel.addListSelectionListener(listener.createSelectionChange());
		tableScroller = new JScrollPane(masterTable);
		setLeftComponent(tableScroller);
	}

	/**
	 * tells the pane to update the table due to data modification
	 */
	public void fireTableDataChanged(){
		((DefaultTableModel) masterTable.getModel()).fireTableDataChanged();
	}

	private void setupPopupMenu(){
		listener = new Listener(controller, masterTable);
		JPopupMenu rightClick = new JPopupMenu();
		//create add element menu item
		JMenuItem addElement = new JMenuItem("Add...");
		addElement.addMouseListener(listener.mouseCreateAddElement());
		rightClick.add(addElement);
		//create remove element menu item
		JMenuItem removeElement = new JMenuItem("Remove...");
		removeElement.addMouseListener(listener.mouseCreateRemoveElement());
		rightClick.add(removeElement);
		//add the popup menu to the jtable
		masterTable.setComponentPopupMenu(rightClick);
	}

	/**
	 * sets the controller for this table, and fires a property change event
	 * <p>
	 *
	 * @param controller the new controller to set
	 */
	public void setController(T controller){
		listener.setController(controller);
		this.controller = controller;
		((MasterTable) masterTable.getModel()).setController(controller);
		((MasterTable) masterTable.getModel()).fireTableDataChanged();
		controller.updateDisplayForElement(0);
	}
}
