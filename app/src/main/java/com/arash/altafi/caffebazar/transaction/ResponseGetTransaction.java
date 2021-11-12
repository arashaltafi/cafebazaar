package com.arash.altafi.caffebazar.transaction;

import com.google.gson.annotations.SerializedName;

public class ResponseGetTransaction {

	@SerializedName("ref_id")
	private String refId;

	@SerializedName("date")
	private String date;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	public String getRefId(){
		return refId;
	}

	public String getDate(){
		return date;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}
}