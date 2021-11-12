package com.arash.altafi.caffebazar.category;

import com.google.gson.annotations.SerializedName;

public class ResponseCategory{

	@SerializedName("image")
	private String image;

	@SerializedName("price")
	private int price;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public String getImage(){
		return image;
	}

	public int getPrice(){
		return price;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}
}