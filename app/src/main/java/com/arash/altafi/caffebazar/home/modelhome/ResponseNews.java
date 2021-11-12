package com.arash.altafi.caffebazar.home.modelhome;

import com.google.gson.annotations.SerializedName;

public class ResponseNews{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("content")
	private String content;

	@SerializedName("writer_image")
	private String writerImage;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getContent(){
		return content;
	}

	public String getWriterImage(){
		return writerImage;
	}
}