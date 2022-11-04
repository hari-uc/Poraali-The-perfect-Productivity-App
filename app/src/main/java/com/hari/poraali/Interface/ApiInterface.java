package com.hari.poraali.Interface;

import com.hari.poraali.Model.EnglishQuoteModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("random")
    Call<List<EnglishQuoteModel>>getQuote();


}
