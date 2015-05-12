package sitemanager;

/**
 * This class is designed to fail silently. It will print out any problems found, but will
 * not throw an exception.
 * Future implementations may include limited optional exception handling.
 * <p>
 * @author Richard Luby, Copyright 2015
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileOperations {

	/**
	 * determines which mode to select when an operation fails
	 * the default is to print the errors
	 */
	public static enum MODE_SELECT {

		SILENT, THROW_EXCEPTION, PRINT_ERROR
	};

	/**
	 * handles any errors that occur during the execution of the program.
	 * <p>
	 * @param e    the error that is being handled
	 * @param MODE the mode to use for handling exceptions
	 */
	protected static void handleError(Exception e, int MODE) {
		if (MODE_SELECT.PRINT_ERROR.ordinal() == MODE) {
			e.printStackTrace();
		}
	}

	/**
	 * this class simplifies writing programs with java. it removes the need to work with
	 * the filesystem. The class
	 * writes files, currently accepting only strings.
	 */
	public static class FileWriter extends FileOperations {

		/**
		 * the writer to use for this object
		 */
		private BufferedWriter fileWriter;
		/**
		 * determines what mode to use, ie fail silently or not
		 */
		private int MODE;

		/**
		 * takes a string and opens a buffered writer to write information
		 * <p>
		 * @param fileName the name of the file to be read
		 */
		public FileWriter(String fileName) {
			fileWriter = null;
			try {
				fileWriter = new BufferedWriter(new java.io.FileWriter(fileName));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				handleError(e, MODE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * opens a buffered writer for the file
		 * <p>
		 * @param file the file to which to write
		 */
		public FileWriter(File file) {
			try {
				fileWriter = new BufferedWriter(new java.io.FileWriter(file));
			} catch (IOException ex) {
				handleError(ex, MODE);
			}
		}

		/**
		 * writes a single piece of data to the file associated with this object
		 * <p>
		 * @param data the data to be written to file
		 */
		public void write(String data) {
			try {
				fileWriter.write(data);
			} catch (IOException e) {
				handleError(e, MODE);
			}
		}

		/**
		 * writes a single piece of data to the file associated with this object, and then
		 * appends a newline character
		 * <p>
		 * @param data the data to be written to file
		 */
		public void writeln(String data) {
			try {
				fileWriter.write(data + "\n");
			} catch (IOException e) {
				handleError(e, MODE);
			}
		}

		/**
		 * flushes the writer and closes associated resources
		 */
		public void close() {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				handleError(e, MODE);
			}
		}
	}

	/**
	 * this class simplifies the issue of reading files with java. Files are read line by
	 * line upon command from the
	 * program.
	 */
	public static class FileReader extends FileOperations {

		/**
		 * the reader to user for this object
		 */
		private BufferedReader fileReader;
		/**
		 * determines what mode to use, ie fail silently or not
		 */
		private int MODE;

		/**
		 * takes a file and opens a buffered reader to receive information
		 * <p>
		 * @param fileName the file to be read
		 */
		public FileReader(File fileName) {
			fileReader = null;
			try {
				fileReader = new BufferedReader(new java.io.FileReader(fileName));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				handleError(e, MODE);
			}
		}

		/**
		 * takes a string and opens a buffered reader to receive information
		 * <p>
		 * @param fileName the name of the file to be read
		 */
		public FileReader(String fileName) {
			fileReader = null;
			try {
				fileReader = new BufferedReader(new java.io.FileReader(fileName));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				handleError(e, MODE);
			}
		}

		/**
		 * reads the next line from the file, and moves forward in the file
		 * <p>
		 * @return returns the next line in the file. Null is returned if no line was
		 *         found, or if an error occurred.
		 */
		public String readLine() {
			try {
				if (fileReader != null && fileReader.ready()) {
					return fileReader.readLine();
				}
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				handleError(e, MODE);
				return null;
			}
		}

		/**
		 * sets the mode to be used for this instance
		 * <p>
		 * @param mode the mode to select for handling errors
		 */
		public void setMode(MODE_SELECT mode) {
			MODE = mode.ordinal();
		}

		/**
		 * closes associated resources
		 */
		public void close() {
			try {
				fileReader.close();
			} catch (IOException e) {
				handleError(e, MODE);
			}
		}
	}
}
