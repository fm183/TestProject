package com.example.testproject.serviceapi;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DataApi {


//    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    @GET("idea.medeming.com/c3pr90ntc0td/jihuoma/images/jihuoma.zip")
    Call<ResponseBody> getData();
}
