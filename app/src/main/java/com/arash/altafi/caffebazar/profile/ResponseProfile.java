package com.arash.altafi.caffebazar.profile;

import com.google.gson.annotations.SerializedName;

public class ResponseProfile{

	@SerializedName("comment_count")
	private int commentCount;

	@SerializedName("image")
	private String image;

	@SerializedName("name_family")
	private String nameFamily;

	@SerializedName("expire_date")
	private String expireDate;

	@SerializedName("transaction_count")
	private int transactionCount;

	public int getCommentCount(){
		return commentCount;
	}

	public String getImage(){
		return image;
	}

	public String getNameFamily(){
		return nameFamily;
	}

	public String getExpireDate(){
		return expireDate;
	}

	public int getTransactionCount(){
		return transactionCount;
	}
}