package help;

import sitemanager.MainFrame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;

/** @author Richard Luby, Copyright 2013 */

/** this class is used to generate a frame with help options for the user */
public class HelpClass{
	/** a boolean value to determine if the frame has already been created */
	private static boolean isCreated;
	/** the JFrame component responsible for running the application */
	private static MainFrame mainFrame;
	/** the model object containing all important data */
	//private static Controller controller;
	/** JPanel to hold the help topics */
	private JPanel helpTopics;
	/** JPanel to hold details */
	private JPanel detailPanel;
	/** HashTable to hold the data */
	private Hashtable<String, String> dataTable;
	/** JTextArea to hold the help data itself */
	private JTextArea dataArea;
	/** titleList to hold the titles of each help topic */
	private JList<String> titleList;
	/** JFrame to show the help window */
	private JFrame helpFrame;

	/** Constructor to build an display the window frame */
	public HelpClass(MainFrame f){
		if(!isCreated){
			mainFrame = f;
			//controller = c;
			helpFrame = new JFrame("Help");
			helpFrame.setLocationRelativeTo(mainFrame);
			helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			//listener to update isCreated in order to ensure only 1 window is open
			helpFrame.addWindowListener(new WindowListener(){

				@Override
				public void windowOpened(WindowEvent e){
					isCreated = true;
				}

				@Override
				public void windowClosed(WindowEvent e){
				}

				@Override
				public void windowIconified(WindowEvent arg0){
				}

				@Override
				public void windowDeiconified(WindowEvent arg0){
				}

				@Override
				public void windowDeactivated(WindowEvent arg0){
				}

				@Override
				public void windowClosing(WindowEvent arg0){
					isCreated = false;
					helpFrame.dispose();
				}

				@Override
				public void windowActivated(WindowEvent arg0){
				}
			});
			Dimension dim = mainFrame.getSize();
			dim.height = dim.height / 2;
			dim.width = (dim.width * 2) / 5;
			helpFrame.setSize(dim);
			helpFrame.setLayout(new FlowLayout(FlowLayout.LEADING));
			getData();
			createTopicPanel();
			createDetailPanel();
			helpFrame.add(helpTopics);
			helpFrame.add(detailPanel);
			helpFrame.setVisible(true);
		}
	}

	/** function to read the data off the disk */
	private void getData(){
		//information is keyed with title, data values
		dataTable = new Hashtable<String, String>();
		InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(
				"HelpFile"));//helpFile is stored in the jar
		try{
			StringBuffer buffer = new StringBuffer();
			while(reader.ready()){
				buffer.append((char) reader.read());
			}
			String[] rawArr = buffer.toString().split("\\|");//delimiter used between lines of (title, data) keys
			int hashTagIndex = 0;
			for(int i = 0; i < rawArr.length; i++){ //loop through all data splits until finished
				hashTagIndex = rawArr[i].indexOf("#");
				dataTable.put(rawArr[i].substring(0, hashTagIndex).trim(), rawArr[i]
						.substring(hashTagIndex + 1));
			}

		} catch(IOException e){
			e.printStackTrace();
		} finally{
			try{
				reader.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	/** Function to create a JPanel with the individual topic data */
	private void createDetailPanel(){
		dataArea = new JTextArea(dataTable.get("About the Program"));
		dataArea.setEditable(false);
		dataArea.setWrapStyleWord(true);
		dataArea.setLineWrap(true);
		JScrollPane scroller = new JScrollPane(dataArea);
		Dimension dim = helpFrame.getSize();
		dim.width = (dim.width * 55) / 100;
		dim.height = (dim.height * 8) / 10;
		scroller.setPreferredSize(dim);
		scroller.setBorder(new EtchedBorder());
		detailPanel = new JPanel();
		detailPanel.add(scroller);
	}

	/** function to create JPanel with an index of help topics */
	private void createTopicPanel(){
		helpTopics = new JPanel();
		Enumeration<String> keys = dataTable.keys();
		String[] temp = new String[dataTable.size()];
		for(int i = 0; keys.hasMoreElements(); i++){
			temp[i] = keys.nextElement().trim();
		}
		titleList = new JList<String>(temp);
		titleList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0){
				if(titleList.getSelectedValue() != null){
					dataArea.setText(dataTable.get(titleList.getSelectedValue()));
					dataArea.validate();
					dataArea.repaint();
				}
			}
		});
		titleList.addMouseListener(new MouseListener(){
			@Override
			public void mouseReleased(MouseEvent arg0){
			}

			@Override
			public void mousePressed(MouseEvent arg0){
			}

			@Override
			public void mouseExited(MouseEvent arg0){
			}

			@Override
			public void mouseEntered(MouseEvent arg0){
			}

			@Override
			public void mouseClicked(MouseEvent arg0){
				if(titleList.getSelectedValue() != null){
					dataArea.setText(dataTable.get(titleList.getSelectedValue()));
					dataArea.validate();
					dataArea.repaint();
				}
			}
		});
		helpTopics.add(titleList);
	}

	/** Inner class to begin the help function */
	public static ActionListener createNewHelpWindowListener(MainFrame mf){
		mainFrame = mf;
		//controller = c;
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0){
				new HelpClass(mainFrame);

			}
		};
	}
}
