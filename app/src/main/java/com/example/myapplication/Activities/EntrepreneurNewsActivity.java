package com.example.myapplication.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Adapter.WebviewAdapter;
import com.example.myapplication.Model.NewsModel;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EntrepreneurNewsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Object> viewItems = new ArrayList<>();

    private RecyclerView.Adapter mAdapter;
    private ImageView imageView;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = "EntrepreneurNewsActivity";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrepreneur_news);
//        requestWindowFeature (Window.FEATURE_NO_TITLE);
//        this.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar ().hide ();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycelerview);
        imageView = findViewById (R.id.imageView);


        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        mAdapter = new WebviewAdapter (this, viewItems, new WebviewAdapter.ItemClickListener () {
            @Override
            public void onItemClick(NewsModel news) {
                String uri = news.getUrl ().toString ();
                intent = new Intent (getApplicationContext (),WebviewActivity.class);
                intent.putExtra ("url",uri);
                startActivityForResult (intent,1);
//                showToast (news.getUrl ());

            }
        });
        mRecyclerView.setAdapter (mAdapter);

        addItemsFromJSON();

    }

    @SuppressLint("LongLogTag")
    private void addItemsFromJSON() {
        try {

            String jsonDataString = readJSONDataFromFile();
            JSONArray jsonArray = new JSONArray(jsonDataString);

            for (int i=0; i<jsonArray.length(); ++i) {

                JSONObject itemObj = jsonArray.getJSONObject(i);

                String name = itemObj.getString("title");
                String img = itemObj.getString ("img");
                String url = itemObj.getString ("url");


                NewsModel news  = new NewsModel (name,img,url);
                viewItems.add(news);
            }

        } catch (JSONException | IOException e) {
            Log.d(TAG, "addItemsFromJSON: ", e);
        }
    }

    private String readJSONDataFromFile() throws IOException{

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {

            String jsonString = null;
            inputStream = getResources().openRawResource(R.raw.news);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));

            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }

    private void showToast(String mesage){
        Toast.makeText (this,mesage,Toast.LENGTH_SHORT).show ();
    }
}