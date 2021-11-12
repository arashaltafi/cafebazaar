package com.arash.altafi.caffebazar.about;

import com.google.gson.annotations.SerializedName;

public class ResponseMessageForm{

	@SerializedName("message")
	private String message;

	public String getMessage(){
		return message;
	}
}