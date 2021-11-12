package com.arash.altafi.caffebazar.detailsProduct;

import com.google.gson.annotations.SerializedName;

public class ResponseDetailsProduct {

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("teacher_name")
	private String teacherName;

	@SerializedName("catname")
	private String catname;

	@SerializedName("title")
	private String title;

	@SerializedName("content")
	private String content;

	@SerializedName("capacity")
	private String capacity;

	@SerializedName("sells_number")
	private String sellsNumber;

	@SerializedName("price")
	private int price;

	@SerializedName("teacher_image")
	private String teacherImage;

	@SerializedName("id")
	private int id;

	@SerializedName("time")
	private String time;

	@SerializedName("sku")
	private String sku;

	@SerializedName("status")
	private boolean status;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getTeacherName(){
		return teacherName;
	}

	public String getCatname(){
		return catname;
	}

	public String getTitle(){
		return title;
	}

	public String getContent(){
		return content;
	}

	public String getCapacity(){
		return capacity;
	}

	public String getSellsNumber(){
		return sellsNumber;
	}

	public int getPrice(){
		return price;
	}

	public String getTeacherImage(){
		return teacherImage;
	}

	public int getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getSku(){
		return sku;
	}

	public boolean isStatus(){
		return status;
	}
}