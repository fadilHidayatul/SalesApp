package com.example.sales.UtilsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("getCustomer")
    Call<ResponseBody> getDataCustomer( @Field("customer_id") String cus_id
    );
}
