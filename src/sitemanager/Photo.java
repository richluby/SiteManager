/*
 * Copyright RLuby, All Rights Reserved
 */
package sitemanager;

import java.io.File;

/**
 * @author RLuby
 */
public class Photo implements CommonAccess{

	/**
	 * the name of this photo
	 */
	private String photoName;
	/**
	 * the descriptive text for this photo
	 */
	private String photoDescription;
	/**
	 * the location of this photo
	 */
	private File photoFile;
	/**
	 * a unique id with which to id this photo
	 */
	private int id;

	/**
	 * initializes this photo
	 */
	public Photo(){
		photoFile = null;
		photoName = "";
		photoDescription = "";
		id = -1;
	}

	/**
	 * returns the name of this photo
	 * <p>
	 *
	 * @return returns the name of this photo
	 */
	public String getName(){
		return photoName;
	}

	/**
	 * sets the name of this photo
	 * <p>
	 *
	 * @param photoName the name for the photo
	 */
	public void setPhotoName(String photoName){
		this.photoName = photoName;
	}

	/**
	 * returns the description of this photo
	 * <p>
	 *
	 * @return returns the description of this photo
	 */
	public String getDescription(){
		return photoDescription;
	}

	/**
	 * sets the description of this photo
	 * <p>
	 *
	 * @param photoDescription the description for the photo
	 */
	public void setPhotoDescription(String photoDescription){
		this.photoDescription = photoDescription;
	}

	/**
	 * returns the file for this photo
	 * <p>
	 *
	 * @return returns the file for this photo
	 */
	public File getLocationFile(){
		return photoFile;
	}

	/**
	 * sets the file of this photo
	 * <p>
	 *
	 * @param photoFile the file for the photo
	 */
	public void setPhotoFile(File photoFile){
		this.photoFile = photoFile;
	}

	/**
	 * get the unique id for this photo
	 * <p>
	 *
	 * @return returns the unique id for this photo
	 */
	public int getId(){
		return id;
	}

	/**
	 * sets the unique id for this photo
	 * <p>
	 *
	 * @param id the unique id to set for this photo
	 */
	public void setId(int id){
		this.id = id;
	}

	/**
	 * returns the title of this photo
	 * <p>
	 *
	 * @return returns the name/title of this photo
	 */
	@Override
	public String toString(){
		return photoName;
	}
}
