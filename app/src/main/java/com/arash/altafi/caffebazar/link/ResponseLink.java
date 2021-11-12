package com.arash.altafi.caffebazar.link;

import com.google.gson.annotations.SerializedName;

public class ResponseLink{

	@SerializedName("link")
	private String link;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("image")
	private String image;

	public String getLink(){
		return link;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getImage(){
		return image;
	}
}