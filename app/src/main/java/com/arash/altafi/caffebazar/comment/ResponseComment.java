package com.arash.altafi.caffebazar.comment;

import com.google.gson.annotations.SerializedName;

public class ResponseComment{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("name_family")
	private String nameFamily;

	@SerializedName("id")
	private String id;

	@SerializedName("content")
	private String content;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getNameFamily(){
		return nameFamily;
	}

	public String getId(){
		return id;
	}

	public String getContent(){
		return content;
	}
}