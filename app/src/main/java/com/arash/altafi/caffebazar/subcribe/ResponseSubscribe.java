package com.arash.altafi.caffebazar.subcribe;

import com.google.gson.annotations.SerializedName;

public class ResponseSubscribe{

	@SerializedName("expire")
	private String expire;

	@SerializedName("message")
	private String message;

	public String getExpire(){
		return expire;
	}

	public String getMessage(){
		return message;
	}
}