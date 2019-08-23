package com.example.stripedemosagar.interfaces;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface StripeAPI {


    @Headers({
            "Authorization: Bearer secrete_key_here",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @POST("/v1/customers/cus_FOj9wjteKYaxZg/sources")
    @FormUrlEncoded
    Call<ResponseBody> addCard(@FieldMap HashMap<String, Object> map);



    @Headers({
            "Authorization: Bearer secrete_key_here",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @GET("/v1/customers/cus_FOj9wjteKYaxZg/sources")
    Call<ResponseBody> getAllCards(@QueryMap HashMap<String, String> msp);



    @Headers({
            "Authorization: Bearer secrete_key_here",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @POST("/v1/customers/cus_FOj9wjteKYaxZg/sources/{id}")
    @FormUrlEncoded
    Call<ResponseBody> updateCard(@Path("id") String id, @FieldMap HashMap<String, Object> map);


    @Headers({
            "Authorization: Bearer secrete_key_here"
    })
    @DELETE("/v1/customers/cus_FOj9wjteKYaxZg/sources/{id}")
    Call<ResponseBody> deleteCard(@Path("id") String id);



}
