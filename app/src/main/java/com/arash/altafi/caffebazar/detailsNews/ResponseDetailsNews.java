package com.arash.altafi.caffebazar.detailsNews;

import com.google.gson.annotations.SerializedName;

public class ResponseDetailsNews{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("content")
	private String content;

	@SerializedName("writer_image")
	private String writerImage;

	@SerializedName("status")
	private boolean status;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getName(){
		return name;
	}

	public int getId(){
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

	public boolean isStatus(){
		return status;
	}
}