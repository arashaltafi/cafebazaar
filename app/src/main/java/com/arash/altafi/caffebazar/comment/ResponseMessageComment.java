package com.arash.altafi.caffebazar.comment;

import com.google.gson.annotations.SerializedName;

public class ResponseMessageComment{

	@SerializedName("message")
	private String message;

	public String getMessage(){
		return message;
	}
}