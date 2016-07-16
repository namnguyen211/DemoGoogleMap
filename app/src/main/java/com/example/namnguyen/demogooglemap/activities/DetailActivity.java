package com.example.namnguyen.demogooglemap.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.namnguyen.demogooglemap.R;
import com.example.namnguyen.demogooglemap.apis.FourSquareApi;
import com.example.namnguyen.demogooglemap.models.photo.Item;
import com.example.namnguyen.demogooglemap.models.photo.PhotoResponse;
import com.example.namnguyen.demogooglemap.models.photo.Photos;
import com.example.namnguyen.demogooglemap.services.FourSquareServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView tv1,tvLat,tvLng;
    FourSquareApi fourSquareApi;
    ImageView imageView;
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getPhotoFourSquare();
        itemList = new ArrayList<>();

        tv1 = (TextView) findViewById(R.id.tv_title);
        tvLat= (TextView) findViewById(R.id.tv_lat);
        tvLng = (TextView) findViewById(R.id.tv_lng);
        imageView = (ImageView) findViewById(R.id.imv);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Intent intent = this.getIntent();
        if(intent != null){
            String strData = intent.getStringExtra("DetailActivity");

            if(strData.equals("SearchActivity")){


                tv1.setText(getIntent().getStringExtra("Title"));
                tvLat.setText(getIntent().getStringExtra("Lat"));
                tvLng.setText(getIntent().getStringExtra("Lng"));


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

                tvLat.setText(getIntent().getStringExtra("LatMain"));
                tvLng.setText(getIntent().getStringExtra("LngMain"));

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

    private  void getPhotoFourSquare(){
        fourSquareApi = FourSquareServiceGenerator.createService(FourSquareApi.class);
        Call<PhotoResponse> call = fourSquareApi.getPhoto(getIntent().getStringExtra("Id"),"20130815");
        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                Log.d("Success" , String.valueOf(response.body().getResponse().getPhotos().getCount()));

                itemList =    response.body().getResponse().getPhotos().getItems();

//                String a = photos.getItems().get(0).getUrl();
//                Glide.with(DetailActivity.this).load(a).into(imageView);
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {

            }
        });
    }
}
