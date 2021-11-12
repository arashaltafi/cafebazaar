package com.arash.altafi.caffebazar.detailsNews;

import com.google.gson.annotations.SerializedName;

public class ResponseMessageFavourite{

	@SerializedName("message")
	private String message;

	public String getMessage(){
		return message;
	}
}