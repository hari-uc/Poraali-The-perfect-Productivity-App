package com.hari.poraali.QuotesActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hari.poraali.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class TamilQuotes extends Fragment {
    TextView tamilq;
    final Random randomNum = new Random ();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate (R.layout.fragment_tamil_quotes, container, false);
    }


    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        tamilq = getView ().findViewById (R.id.tamilquotetxt);

        try {
            JSONObject jsonObject = new JSONObject (LoadFileFromAsset ());
            JSONArray jsonArray = jsonObject.getJSONArray ("quotes");

            JSONObject quotedata = jsonArray.getJSONObject (randomNum.nextInt (jsonArray.length () - 1));//need to add random num (i)
            tamilq.setText (quotedata.getString ("quote"));

        } catch (JSONException e) {
            e.printStackTrace ();
        }
    }


    public String LoadFileFromAsset() {
        String json = null;
        try {
            InputStream inputStream = getActivity ().getAssets ().open ("TamilQuotes.json");
            int sizeofFie = inputStream.available ();
            byte[] bufferData = new byte[sizeofFie];
            inputStream.read (bufferData);
            inputStream.close ();
            json = new String (bufferData, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace ();
            return null;
        }
        return json;


    }
}