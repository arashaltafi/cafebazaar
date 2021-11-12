package com.arash.altafi.caffebazar.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.arash.altafi.caffebazar.about.ResponseMessageForm;
import com.arash.altafi.caffebazar.auth.ResponseLogin;
import com.arash.altafi.caffebazar.category.ResponseCategory;
import com.arash.altafi.caffebazar.comment.ResponseComment;
import com.arash.altafi.caffebazar.comment.ResponseMessageComment;
import com.arash.altafi.caffebazar.detailsNews.ResponseDetailsNews;
import com.arash.altafi.caffebazar.detailsNews.ResponseMessageFavourite;
import com.arash.altafi.caffebazar.detailsProduct.ResponseTransaction;
import com.arash.altafi.caffebazar.home.modelhome.ResponseEducation;
import com.arash.altafi.caffebazar.home.modelhome.ResponseHomeCategory;
import com.arash.altafi.caffebazar.home.modelhome.ResponseNews;
import com.arash.altafi.caffebazar.home.modelhome.ResponsePadcast;
import com.arash.altafi.caffebazar.home.modelhome.ResponseVideo;
import com.arash.altafi.caffebazar.detailsProduct.ResponseDetailsProduct;
import com.arash.altafi.caffebazar.link.ResponseLink;
import com.arash.altafi.caffebazar.morePodcast.ResponseMorePodcast;
import com.arash.altafi.caffebazar.profile.ResponseEditProfile;
import com.arash.altafi.caffebazar.profile.ResponseProfile;
import com.arash.altafi.caffebazar.subcribe.ResponseSubscribe;
import com.arash.altafi.caffebazar.transaction.ResponseGetTransaction;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiService {

    private RequestQueue requestQueue;
    private Map<String,String> map = new HashMap<String,String>();

    public ApiService(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void login(String phone, String pushPoleId, Response.Listener<ResponseLogin> listener,Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseLogin> request = new GsonRequest<>(Request.Method.POST,"profile/register.php",
                new TypeToken<ResponseLogin>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobile_phone",phone);
        jsonObject.addProperty("pushPole_Id",pushPoleId);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void get_Education(Response.Listener<List<ResponseEducation>> listListener,Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseEducation>> request = new GsonRequest<>(Request.Method.GET,"home/home_education.php",
                new TypeToken<List<ResponseEducation>>(){}.getType(),listListener,errorListener);
        requestQueue.add(request);
    }

    public void get_Videos(Response.Listener<List<ResponseVideo>> listListener, Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseVideo>> request = new GsonRequest<>(Request.Method.GET,"home/home_videos.php",
                new TypeToken<List<ResponseVideo>>(){}.getType(),listListener,errorListener);
        requestQueue.add(request);
    }

    public void get_News(Response.Listener<List<ResponseNews>> listListener,Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseNews>> request = new GsonRequest<>(Request.Method.GET,"home/home_news.php",
                new TypeToken<List<ResponseNews>>(){}.getType(),listListener,errorListener);
        requestQueue.add(request);
    }

    public void get_Padcast(Response.Listener<List<ResponsePadcast>> listListener,Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponsePadcast>> request = new GsonRequest<>(Request.Method.GET,"home/home_padcast.php",
                new TypeToken<List<ResponsePadcast>>(){}.getType(),listListener,errorListener);
        requestQueue.add(request);
    }

    public void category(int category,Response.Listener<List<ResponseCategory>> listListener,Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseCategory>> request = new GsonRequest<>(Request.Method.POST,"products/category_products.php",
                new TypeToken<List<ResponseCategory>>(){}.getType(),listListener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cat_id",category);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void get_details_product (int id , String header , Response.Listener<ResponseDetailsProduct> listener , Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseDetailsProduct> request = new GsonRequest<>(Request.Method.POST,"products/product.php",
                new TypeToken<ResponseDetailsProduct>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",id);
        map.put("Authorization",header);
        request.setMap(map);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void get_All_Video(int page,Response.Listener<List<ResponseVideo>> listListener , Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseVideo>> request = new GsonRequest<>(Request.Method.GET,"videos/all_videos.php?page="+page+"&per_page=5",
                new TypeToken<List<ResponseVideo>>(){}.getType(),listListener,errorListener);
        requestQueue.add(request);
    }

    public void get_details_news(int id, String header, Response.Listener<ResponseDetailsNews> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseDetailsNews> request = new GsonRequest<>(Request.Method.POST,"news/news.php",
                new TypeToken<ResponseDetailsNews>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",id);
        map.put("Authorization",header);
        request.setMap(map);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void send_comment(int id , String content , String header , Response.Listener<ResponseMessageComment> listener,Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseMessageComment> request = new GsonRequest<>(Request.Method.POST,"news/get_comment.php",
                new TypeToken<ResponseMessageComment>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("news_id",id);
        jsonObject.addProperty("content",content);
        map.put("Authorization",header);
        request.setMap(map);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void get_comment(int id,Response.Listener<List<ResponseComment>> listListener,Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseComment>> request = new GsonRequest<>(Request.Method.POST,"news/show_comments.php",
                new TypeToken<List<ResponseComment>>(){}.getType(),listListener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("news_id",id);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void get_More_News(int page,Response.Listener<List<ResponseNews>> listListener,Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseNews>> request = new GsonRequest<>(Request.Method.GET,"news/all_news.php?page="+page+"&per_page=10",
                new TypeToken<List<ResponseNews>>(){}.getType(),listListener,errorListener);
        requestQueue.add(request);
    }

    public void get_More_Podcasts(Response.Listener<List<ResponseMorePodcast>> listListener, Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseMorePodcast>> request = new GsonRequest<>(Request.Method.GET,"padcast/all_padcasts.php",
                new TypeToken<List<ResponseMorePodcast>>(){}.getType(),listListener,errorListener);
        requestQueue.add(request);
    }

    public void get_Profile (String header , Response.Listener<ResponseProfile> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseProfile> request = new GsonRequest<>(Request.Method.POST,"profile/show_profile.php",
                new TypeToken<ResponseProfile>(){}.getType(),listener,errorListener);
        map.put("Authorization",header);
        request.setMap(map);
        requestQueue.add(request);
    }

    public void edit_Profile(String name , String image , String header , Response.Listener<ResponseEditProfile> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseEditProfile> request = new GsonRequest<>(Request.Method.POST,"profile/edit_profile.php",
                new TypeToken<ResponseEditProfile>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name_family",name);
        jsonObject.addProperty("image",image);
        map.put("Authorization",header);
        request.setMap(map);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void get_Home_Category(Response.Listener<List<ResponseHomeCategory>> listListener, Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseHomeCategory>> request = new GsonRequest<>(Request.Method.GET,"home/home_category.php",
                new TypeToken<List<ResponseHomeCategory>>(){}.getType(),listListener,errorListener);
        requestQueue.add(request);
    }

    public void insert_Transaction(int id_Product , String ref_Id , String header , Response.Listener<ResponseTransaction> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseTransaction> request = new GsonRequest<>(Request.Method.POST,"profile/get_transaction.php",
                new TypeToken<ResponseTransaction>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("product_id",id_Product);
        jsonObject.addProperty("ref_id",ref_Id);
        map.put("Authorization",header);
        request.setMap(map);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void insert_Subscribe (String type, String ref_Id, String header , Response.Listener<ResponseSubscribe> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseSubscribe> request = new GsonRequest<>(Request.Method.POST,"profile/get_bought_subscribtion.php",
                new TypeToken<ResponseSubscribe>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type",type);
        jsonObject.addProperty("ref_id",ref_Id);
        map.put("Authorization",header);
        request.setMap(map);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void get_Transaction (String header , Response.Listener<List<ResponseGetTransaction>> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseGetTransaction>> request = new GsonRequest<>(Request.Method.POST,"profile/show_transactions.php",
                new TypeToken<List<ResponseGetTransaction>>(){}.getType(),listener,errorListener);
        map.put("Authorization",header);
        request.setMap(map);
        requestQueue.add(request);
    }

    public void get_Link (String ref_Id, String header , Response.Listener<List<ResponseLink>> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseLink>> request = new GsonRequest<>(Request.Method.POST,"profile/show_links.php",
                new TypeToken<List<ResponseLink>>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ref_id",ref_Id);
        map.put("Authorization",header);
        request.setMap(map);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void insert_Favourite (int id, String type, String header , Response.Listener<ResponseMessageFavourite> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseMessageFavourite> request = new GsonRequest<>(Request.Method.POST,"news/add_bookmark.php",
                new TypeToken<ResponseMessageFavourite>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("news_id",id);
        jsonObject.addProperty("type",type);
        map.put("Authorization",header);
        request.setMap(map);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

    public void get_favourite (String header , Response.Listener<List<ResponseNews>> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<List<ResponseNews>> request = new GsonRequest<>(Request.Method.POST,"profile/show_bookmarks.php",
                new TypeToken<List<ResponseNews>>(){}.getType(),listener,errorListener);
        map.put("Authorization",header);
        request.setMap(map);
        requestQueue.add(request);
    }

    public void form (String content, String header , Response.Listener<ResponseMessageForm> listener, Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseMessageForm> request = new GsonRequest<>(Request.Method.POST,"profile/form.php",
                new TypeToken<ResponseMessageForm>(){}.getType(),listener,errorListener);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("description",content);
        map.put("Authorization",header);
        request.setMap(map);
        request.setJsonObject(jsonObject);
        requestQueue.add(request);
    }

}