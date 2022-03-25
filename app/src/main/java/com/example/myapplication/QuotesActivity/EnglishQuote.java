package com.example.myapplication.QuotesActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Interface.ApiInterface;
import com.example.myapplication.Model.EnglishQuoteModel;
import com.example.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EnglishQuote extends Fragment {

    TextView quoteText;
    String BASE_URL = "https://zenquotes.io/api/";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate (R.layout.fragment_english_quote, container, false);
    }


    public void onViewCreated(@NonNull View view,@NonNull Bundle savedInstanceState){
        super.onViewCreated (view,savedInstanceState);

        quoteText = getView ().findViewById (R.id.qoutetext);
        quoteText.setText ("");


        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl (BASE_URL)
                .addConverterFactory (GsonConverterFactory.create ())
                .build ();


        ApiInterface apiInterface = retrofit.create (ApiInterface.class);

        Call<List<EnglishQuoteModel>> call = apiInterface.getQuote ();

        call.enqueue (new Callback<List<EnglishQuoteModel>> () {
            @Override
            public void onResponse(Call<List<EnglishQuoteModel>> call, Response<List<EnglishQuoteModel>> response) {
                List<EnglishQuoteModel> data = response.body ();

                for (int i = 0; i < data.size (); i++) {
                    quoteText.append (data.get (i).getquote ());

                }
            }

            @Override
            public void onFailure(Call<List<EnglishQuoteModel>> call, Throwable t) {
                Toast.makeText (getContext (),"failed",Toast.LENGTH_SHORT).show ();
            }
        });
    }


}