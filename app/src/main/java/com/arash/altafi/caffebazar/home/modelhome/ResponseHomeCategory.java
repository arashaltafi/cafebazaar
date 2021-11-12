package com.arash.altafi.caffebazar.home.modelhome;

import com.google.gson.annotations.SerializedName;

public class ResponseHomeCategory{

	@SerializedName("id_cat")
	private String idCat;

	@SerializedName("name_cat")
	private String nameCat;

	public String getIdCat(){
		return idCat;
	}

	public String getNameCat(){
		return nameCat;
	}
}