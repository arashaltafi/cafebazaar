package com.arash.altafi.caffebazar.detailsProduct;

import com.google.gson.annotations.SerializedName;

public class ResponseTransaction{

	@SerializedName("count")
	private String count;

	@SerializedName("message")
	private String message;

	public String getCount(){
		return count;
	}

	public String getMessage(){
		return message;
	}
}