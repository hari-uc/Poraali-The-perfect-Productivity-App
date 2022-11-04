package com.hari.poraali.QuotesActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hari.poraali.R;

import org.json.JSONException;
import org.json.JSONObject;


public class EnglishQuote extends Fragment {


    TextView quoteText,authorText;
    String BASE_URL = "https://api.quotable.io/random?maxLength=120?tags=inspirational";



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
        ProgressDialog mProgressDialog = new ProgressDialog(getContext ());

        mProgressDialog.setIndeterminate (true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        quoteText = getView ().findViewById (R.id.jsontxtenglish);
        authorText = getView().findViewById(R.id.authorname);
        quoteText.setText ("");
        authorText.setText("");

        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                BASE_URL, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mProgressDialog.dismiss();
                    Log.d("myapp", "the response is " + response.getString("content"));
//                    Toast.makeText(getApplicationContext(), ""+response.getString("content"), Toast.LENGTH_SHORT).show();
                    String content = (String) response.getString("content");
                    String author = (String) response.getString("author");

                    quoteText.setText(content);
                    authorText.setText("-"+author);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp","Something went wrong");
            }
        });
        requestQueue.add(jsonObjectRequest);



    }


}