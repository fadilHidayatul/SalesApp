package com.example.sales.UtilsApi;

public class UtilsApi {
    public static final String baseUrl = "http://192.168.100.35/distribusi/api/"; //localhost

    public static ApiInterface getApiService(){
        return RetrofitClient.getRetrofit(baseUrl).create(ApiInterface.class);
    }
}
