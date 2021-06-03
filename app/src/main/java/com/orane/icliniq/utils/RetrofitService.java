package com.orane.icliniq.utils;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orane.icliniq.walletdetails.WalletAmountModel;
import com.orane.icliniq.walletdetails.WalletDetailsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("sapp/patientTransactionsDet?")
    Call<ArrayList<WalletDetailsModel>> patientTransactionDetails(@Query("user_id") String user_id, @Query("os_type") String os_type, @Query("token") String token, @Query("page") int page);

    @GET("sapp/patientWalletDet?")
    Call<WalletAmountModel> walletDetails(@Query("user_id") String user_id, @Query("os_type") String os_type, @Query("token") String token);

    @GET("sapp/isDocSlotAvailable?")
    Call<JsonElement> isDocSlotAvailable(@Query("user_id") String user_id, @Query("os_type") String os_type, @Query("token") String token, @Query("doctor_id") String page, @Query("tz_name") String tz_name, @Query("target_date") String target_date,@Query("enc")String enc);

    @GET("sapp/getTimeSlots?")
    Call<JsonElement> getTimeSlots(@Query("user_id") String user_id, @Query("os_type") String os_type, @Query("token") String token, @Query("doctor_id") String page, @Query("slot_type") String slot_type, @Query("target_date") String target_date,@Query("tz_name") String tz_name);

    @POST("sapp/submitDocConsultation")
    Call<JsonElement> submitConsultationWithSlots(@Query("user_id") String user_id,
                                                  @Query("token") String token,
                                                  @Query("os_type") String os_type,
                                                  @Body JsonObject jsonObject);

    @POST("sapp/submitDocConsultation")
    Call<JsonElement> submitWithConsultTime(@Query("user_id") String user_id,
                                            @Query("token") String token,
                                            @Query("os_type") String os_type,
                                            @Body JsonObject jsonObject);

    @GET("sapp/getFamilyProfile?")
    Call<JsonElement> getFamily(@Query("user_id") String user_id, @Query("token") String token);

    @GET("sapp/getFamilyProfile?")
    Call<JsonElement> getFamilyDetails(@Query("user_id") String user_id,@Query("id") String id,@Query("isView") String isView ,@Query("token") String token);

    @GET("sapp/getFamilyProfile?")
    Call<JsonElement> getEditFamilyDetails(@Query("user_id") String user_id,@Query("id") String id,@Query("isView") String isView ,@Query("token") String token);

    @POST("sapp/getFee?")
    Call<JsonElement> getFeesForICQ7Days(@Query("token") String token,@Body JsonObject jsonObject);

   @GET("/sapp/prepareInv?")
    Call<JsonObject> getPrepareInvoice(@Query("user_id") String user_id,@Query("inv_for") String inf_for,@Query("item_id")String item_id,@Query("os_type") String os_type,@Query("token")String token,@Query("enc") String enc);


    @POST("sapp/saveq?")
    Call<JsonElement> postQueryDetails(@Query("user_id") String user_id,@Query("token") String token,@Body JsonObject jsonObject);


}

