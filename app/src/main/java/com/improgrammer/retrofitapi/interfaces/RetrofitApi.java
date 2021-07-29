package com.improgrammer.retrofitapi.interfaces;

import com.improgrammer.retrofitapi.modal.NewsModal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {
    @GET
    Call<NewsModal> getAllnewsitem(@Url String url);

    @GET
    Call<NewsModal> getAllnewsitembyCata(@Url String url1);
}
