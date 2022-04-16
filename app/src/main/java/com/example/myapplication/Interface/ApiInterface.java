package com.example.myapplication.Interface;

import com.example.myapplication.Model.EnglishQuoteModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("random")
    Call<List<EnglishQuoteModel>>getQuote();


}
