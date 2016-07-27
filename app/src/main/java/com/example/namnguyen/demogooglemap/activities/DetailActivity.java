package com.example.namnguyen.demogooglemap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.namnguyen.demogooglemap.R;
import com.example.namnguyen.demogooglemap.adapters.GooglePlaceResultFragmentRecyclerViewAdapter;
import com.example.namnguyen.demogooglemap.adapters.ImageSliderAdapter;
import com.example.namnguyen.demogooglemap.apis.FourSquareApi;
import com.example.namnguyen.demogooglemap.fragments.GooglePlaceResultFragment;
import com.example.namnguyen.demogooglemap.models.foursquare.photo.Item;
import com.example.namnguyen.demogooglemap.models.foursquare.photo.PhotoResponse;
import com.example.namnguyen.demogooglemap.services.FourSquareServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView tv1, tvLat, tvLng;
    FourSquareApi fourSquareApi;
    List<Item> itemList;
    ViewPager viewPager;
    ImageSliderAdapter adapter;
    List<String> listImage = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemList = new ArrayList<>();

        tv1 = (TextView) findViewById(R.id.tv_title);
        tvLat = (TextView) findViewById(R.id.tv_lat);
        tvLng = (TextView) findViewById(R.id.tv_lng);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Intent intent = this.getIntent();
        if (intent != null) {
            String strData = intent.getStringExtra("DetailActivity");

            if (strData.equals("SearchFourSquare")) {
                tv1.setText(getIntent().getStringExtra("TitleFS"));
                tvLat.setText(getIntent().getStringExtra("LatFS"));
                tvLng.setText(getIntent().getStringExtra("LngFS"));
                getPhotoFourSquare(getIntent().getStringExtra("IdFS"));

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        intent.putExtra("TitleDetail", getIntent().getStringExtra("TitleFS"));
                        intent.putExtra("LatDetail", getIntent().getStringExtra("LatFS"));
                        intent.putExtra("LngDetail", getIntent().getStringExtra("LngFS"));
                        intent.putExtra("MainActivity", "DetailActivity");
                        DetailActivity.this.startActivity(intent);
                    }
                });

            }
            if (strData.equals("MainActivity")) {
                tv1.setText(getIntent().getStringExtra("TitleMain"));
                tvLat.setText(getIntent().getStringExtra("LatMain"));
                tvLng.setText(getIntent().getStringExtra("LngMain"));
                String b = getIntent().getStringExtra("IdMain");
                if (b.length() < 25) {
                    getPhotoFourSquare(getIntent().getStringExtra("IdMain"));
                } else {
                    Object a = GooglePlaceResultFragment.photo.get(getIntent().getStringExtra("IdMain"));
                    if (a != null) {
                        listImage.add(a.toString());
                    } else {
                        listImage.add(GooglePlaceResultFragmentRecyclerViewAdapter.PHOTO);
                    }
                }


                adapter = new ImageSliderAdapter(DetailActivity.this, listImage);
                viewPager.setAdapter(adapter);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        intent.putExtra("TitleDetail", getIntent().getStringExtra("Title"));
                        intent.putExtra("LatDetail", getIntent().getStringExtra("LatMain"));
                        intent.putExtra("LngDetail", getIntent().getStringExtra("LngMain"));
                        intent.putExtra("MainActivity", "DetailActivity");
                        DetailActivity.this.startActivity(intent);
                    }
                });
            }
            if (strData.equals("SearchGoogle")) {
                tv1.setText(getIntent().getStringExtra("TitleGG"));
                tvLat.setText(getIntent().getStringExtra("LatGG"));
                tvLng.setText(getIntent().getStringExtra("LngGG"));
                listImage.add(getIntent().getStringExtra("photo1GG"));
//                listImage.add(getIntent().getStringExtra("photo2GG"));
                adapter = new ImageSliderAdapter(DetailActivity.this, listImage);
                viewPager.setAdapter(adapter);


                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        intent.putExtra("TitleDetail", getIntent().getStringExtra("Title"));
                        intent.putExtra("LatDetail", getIntent().getStringExtra("LatGG"));
                        intent.putExtra("LngDetail", getIntent().getStringExtra("LngGG"));
                        intent.putExtra("MainActivity", "DetailActivity");
                        DetailActivity.this.startActivity(intent);
                    }
                });
            }
        }
    }

    private void getPhotoFourSquare(String id) {
        fourSquareApi = FourSquareServiceGenerator.createService(FourSquareApi.class);
        Call<PhotoResponse> call = fourSquareApi.getPhoto(id, "20130815");
        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
//                Log.d("Success" , String.valueOf(response.body().getResponse().getPhotos().getCount()));

                itemList = response.body().getResponse().getPhotos().getItems();
                for (Item item : itemList) {
                    listImage.add(item.getUrl());
                }

                adapter = new ImageSliderAdapter(DetailActivity.this, listImage);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {

            }
        });
    }
}
