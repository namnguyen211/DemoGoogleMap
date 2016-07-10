package com.example.namnguyen.demogooglemap.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.namnguyen.demogooglemap.R;

public class DetailActivity extends AppCompatActivity {

    TextView tv1,tvLat,tvLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv1 = (TextView) findViewById(R.id.tv_title);
        tvLat= (TextView) findViewById(R.id.tv_lat);
        tvLng = (TextView) findViewById(R.id.tv_lng);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

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
                startActivity(intent);
            }
        });
    }
}
