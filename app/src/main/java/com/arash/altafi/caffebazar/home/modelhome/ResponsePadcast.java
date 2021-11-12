package com.arash.altafi.caffebazar.home.modelhome;

import com.google.gson.annotations.SerializedName;

public class ResponsePadcast{

	@SerializedName("padcast_id")
	private int padcastId;

	@SerializedName("padimage")
	private String padimage;

	@SerializedName("link")
	private String link;

	@SerializedName("title")
	private String title;

	@SerializedName("pad_time")
	private String padTime;

	@SerializedName("writer_name")
	private String writerName;

	@SerializedName("writer_image")
	private String writerImage;

	public int getPadcastId(){
		return padcastId;
	}

	public String getPadimage(){
		return padimage;
	}

	public String getLink(){
		return link;
	}

	public String getTitle(){
		return title;
	}

	public String getPadTime(){
		return padTime;
	}

	public String getWriterName(){
		return writerName;
	}

	public String getWriterImage(){
		return writerImage;
	}
}