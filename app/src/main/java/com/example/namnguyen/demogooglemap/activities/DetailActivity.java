package com.example.namnguyen.demogooglemap.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.namnguyen.demogooglemap.R;
import com.example.namnguyen.demogooglemap.adapters.ImageSliderAdapter;
import com.example.namnguyen.demogooglemap.apis.FourSquareApi;
import com.example.namnguyen.demogooglemap.models.foursquare.photo.Item;
import com.example.namnguyen.demogooglemap.models.foursquare.photo.PhotoResponse;
import com.example.namnguyen.demogooglemap.services.FourSquareServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView tv1,tvLat,tvLng;
    FourSquareApi fourSquareApi;
    List<Item> itemList;
    ViewPager viewPager;
    ImageSliderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemList = new ArrayList<>();

        tv1 = (TextView) findViewById(R.id.tv_title);
        tvLat= (TextView) findViewById(R.id.tv_lat);
        tvLng = (TextView) findViewById(R.id.tv_lng);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Intent intent = this.getIntent();
        if(intent != null){
            String strData = intent.getStringExtra("DetailActivity");

            if(strData.equals("SearchActivity")){


                tv1.setText(getIntent().getStringExtra("Title"));
                tvLat.setText(getIntent().getStringExtra("Lat"));
                tvLng.setText(getIntent().getStringExtra("Lng"));
                getPhotoFourSquare(getIntent().getStringExtra("Id"));

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                        intent.putExtra("TitleDetail",getIntent().getStringExtra("Title"));
                        intent.putExtra("LatDetail",getIntent().getStringExtra("Lat"));
                        intent.putExtra("LngDetail",getIntent().getStringExtra("Lng"));
                        intent.putExtra("MainActivity","DetailActivity");
                        DetailActivity.this.startActivity(intent);
                    }
                });

            }if(strData.equals("MainActivity")){
                tv1.setText(getIntent().getStringExtra("TitleMain"));
                tvLat.setText(getIntent().getStringExtra("LatMain"));
                tvLng.setText(getIntent().getStringExtra("LngMain"));
                getPhotoFourSquare(getIntent().getStringExtra("IdMain"));

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                        intent.putExtra("TitleDetail",getIntent().getStringExtra("Title"));
                        intent.putExtra("LatDetail",getIntent().getStringExtra("LatMain"));
                        intent.putExtra("LngDetail",getIntent().getStringExtra("LngMain"));
                        intent.putExtra("MainActivity","DetailActivity");
                        DetailActivity.this.startActivity(intent);
                    }
                });
            }
        }
    }

    private  void getPhotoFourSquare(String id){
        fourSquareApi = FourSquareServiceGenerator.createService(FourSquareApi.class);
        Call<PhotoResponse> call = fourSquareApi.getPhoto(id,"20130815",3);
        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                Log.d("Success" , String.valueOf(response.body().getResponse().getPhotos().getCount()));
                List<String> listImage = new ArrayList<String>();
                itemList =    response.body().getResponse().getPhotos().getItems();
                for(Item item:itemList){
                 listImage.add(item.getUrl());
                }

                adapter = new ImageSliderAdapter(DetailActivity.this,listImage);
                viewPager.setAdapter(adapter);
//                String a = photos.getItems().get(0).getUrl();
//                Glide.with(DetailActivity.this).load(a).into(imageView);
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {

            }
        });
    }
}
