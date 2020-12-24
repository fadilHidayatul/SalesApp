package com.example.sales.UtilsApi;

public class UtilsApi {
    public static final String baseUrl = "http://192.168.43.136/distribusi/api/"; //localhost

    public static ApiInterface getApiService(){
        return RetrofitClient.getRetrofit(baseUrl).create(ApiInterface.class);
    }
}
