package com.arash.altafi.caffebazar.profile;

import com.google.gson.annotations.SerializedName;

public class ResponseEditProfile{

	@SerializedName("image")
	private String image;

	@SerializedName("name_family")
	private String nameFamily;

	@SerializedName("message")
	private String message;

	public String getImage(){
		return image;
	}

	public String getNameFamily(){
		return nameFamily;
	}

	public String getMessage(){
		return message;
	}
}