package com.orane.icliniq.walletdetails;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orane.icliniq.retrofitmodel.SlotTypeModel;
import com.orane.icliniq.retrofitmodel.TimeslotModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

}
