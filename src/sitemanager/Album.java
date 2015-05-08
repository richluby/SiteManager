/*
 * Copyright RLuby, All Rights Reserved
 */
package sitemanager;

import java.io.File;

/**
 * This class contains the data necessary to represent an album to the program
 * <p>
 * @author RLuby
 */
public class Album {

	/**
	 * the name for this album
	 */
	private String albumName;
	/**
	 * the description for this album
	 */
	private String albumDescription;
	/**
	 * the folder in which the data for this album resides
	 */
	private File albumFolder;
	/**
	 * the cover image for this album
	 */
	private File albumCover;
	/**
	 * the list of photos contained in this album
	 */
	private PhotoController photoController;

	/**
	 * initializes the class
	 */
	public Album() {
		albumName = "";
		albumDescription = "";
		albumFolder = null;
		albumCover = null;
		photoController = new PhotoController();
	}
	/**
	 * returns the photoController that handles the photos for this album
	 */
	public PhotoController getPhotoController() {
		return photoController;
	}
	/**
	 * returns the album name
	 * <p>
	 * @return the name or title of this album
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * sets the name/title of this album
	 * <p>
	 * @param albumName the name to which to set this album
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	/**
	 * returns the album name
	 * <p>
	 * @return the description of this album
	 */
	public String getAlbumDescription() {
		return albumDescription;
	}

	/**
	 * sets the description of this album
	 * <p>
	 * @param albumDescription the description of this album
	 */
	public void setAlbumDescription(String albumDescription) {
		this.albumDescription = albumDescription;
	}

	/**
	 * returns the album name
	 * <p>
	 * @return the folder in which this album resides
	 */
	public File getAlbumFolder() {
		return albumFolder;
	}

	/**
	 * sets the containing folder of this album
	 * <p>
	 * @param albumFolder the root folder of this album
	 */
	public void setAlbumFolder(File albumFolder) {
		this.albumFolder = albumFolder;
	}

	/**
	 * returns the album name
	 * <p>
	 * @return the cover of this album
	 */
	public File getAlbumCover() {
		return albumCover;
	}

	/**
	 * sets the cover of this album
	 * <p>
	 * @param albumCover the cover of this album
	 */
	public void setAlbumCover(File albumCover) {
		this.albumCover = albumCover;
	}

	/**
	 * returns the title of this album
	 * <p>
	 * @return returns the name/title of this photo
	 */
	@Override
	public String toString() {
		return albumName;
	}
}
