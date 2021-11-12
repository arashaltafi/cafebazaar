package com.arash.altafi.caffebazar.auth;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("image")
	private String image;

	@SerializedName("code")
	private String code;

	@SerializedName("isRegister")
	private boolean isRegister;

	@SerializedName("token")
	private String token;

	public String getImage(){
		return image;
	}

	public String getCode(){
		return code;
	}

	public boolean isIsRegister(){
		return isRegister;
	}

	public String getToken(){
		return token;
	}
}