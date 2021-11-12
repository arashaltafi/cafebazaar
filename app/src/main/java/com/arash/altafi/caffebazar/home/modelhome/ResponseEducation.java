package com.arash.altafi.caffebazar.home.modelhome;

import com.google.gson.annotations.SerializedName;

public class ResponseEducation{

	@SerializedName("Education_id")
	private String educationId;

	@SerializedName("catname")
	private String catname;

	@SerializedName("Education_image")
	private String educationImage;

	@SerializedName("Education_title")
	private String educationTitle;

	@SerializedName("capacity")
	private int capacity;

	public String getEducationId(){
		return educationId;
	}

	public String getCatname(){
		return catname;
	}

	public String getEducationImage(){
		return educationImage;
	}

	public String getEducationTitle(){
		return educationTitle;
	}

	public int getCapacity(){
		return capacity;
	}
}