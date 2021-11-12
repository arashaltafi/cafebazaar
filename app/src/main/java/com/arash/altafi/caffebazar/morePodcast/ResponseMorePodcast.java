package com.arash.altafi.caffebazar.morePodcast;

import com.google.gson.annotations.SerializedName;

public class ResponseMorePodcast{

	@SerializedName("image")
	private String image;

	@SerializedName("link")
	private String link;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("time")
	private String time;

	@SerializedName("title")
	private String title;

	@SerializedName("writer_image")
	private String writerImage;

	public String getImage(){
		return image;
	}

	public String getLink(){
		return link;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getTitle(){
		return title;
	}

	public String getWriterImage(){
		return writerImage;
	}
}