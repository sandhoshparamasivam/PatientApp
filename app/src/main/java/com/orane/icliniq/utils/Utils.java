package com.orane.icliniq.utils;

import com.orane.icliniq.Model.Model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    public static Retrofit generateBaseUrl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Model.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
