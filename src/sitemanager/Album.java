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
public class Album implements CommonAccess {

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
	 * a unique id with which to id this album
	 */
	private int id;

	/**
	 * initializes the class
	 */
	public Album() {
		albumName = "";
		albumDescription = "";
		albumFolder = null;
		albumCover = null;
		photoController = new PhotoController(this);
		id = -1;//placeholder
	}

	/**
	 * returns the photoController that handles the photos for this album
	 * <p>
	 * @return returns the controller responsible for this album
	 */
	public PhotoController getPhotoController() {
		return photoController;
	}

	/**
	 * returns the album name
	 * <p>
	 * @return the name or title of this album
	 */
	public String getName() {
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
	public String getDescription() {
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
	 * get the unique id for this album
	 * <p>
	 * @return returns the unique id for this album
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets the unique id for this album
	 * <p>
	 * @param id the unique id to set for this album
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * returns the album name
	 * <p>
	 * @return the folder in which this album resides
	 */
	public File getLocationFile() {
		return albumFolder;
	}

	/**
	 * sets the containing folder of this album
	 * <p>
	 * @param af the root folder of this album
	 */
	public void setAlbumFolder(File af) {
		if (this.albumFolder == null || !this.albumFolder.equals(af)) {
			this.albumFolder = af;
			photoController.populatePhotoList(this.albumFolder);
		}
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
