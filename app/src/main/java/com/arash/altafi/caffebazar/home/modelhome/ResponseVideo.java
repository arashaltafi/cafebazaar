package com.arash.altafi.caffebazar.home.modelhome;

import com.google.gson.annotations.SerializedName;

public class ResponseVideo{

	@SerializedName("link")
	private String link;

	@SerializedName("videotime")
	private String videotime;

	@SerializedName("videoimage")
	private String videoimage;

	@SerializedName("title")
	private String title;

	@SerializedName("video_id")
	private String videoId;

	public String getLink(){
		return link;
	}

	public String getVideotime(){
		return videotime;
	}

	public String getVideoimage(){
		return videoimage;
	}

	public String getTitle(){
		return title;
	}

	public String getVideoId(){
		return videoId;
	}
}