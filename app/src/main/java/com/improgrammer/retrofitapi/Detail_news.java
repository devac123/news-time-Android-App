package com.improgrammer.retrofitapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.http.Url;

public class Detail_news extends AppCompatActivity {
  ImageView detail_news_iv;
  TextView  detail_news_tv,detail_news_desc_tv,detail_news_content_tv ;
  Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        detail_news_iv = findViewById(R.id.detail_news_iv);
        detail_news_tv = findViewById(R.id.detail_news_tv);
        detail_news_desc_tv = findViewById(R.id.detail_news_desc_tv);
        detail_news_content_tv = findViewById(R.id.detail_news_content_tv);
        b1 = findViewById(R.id.detail_news_b);


        Intent detailIntent = getIntent();

        String title = detailIntent.getStringExtra("title");
        String desc = detailIntent.getStringExtra("desc");
        String imgUrl = detailIntent.getStringExtra("imgUrl");
        String url = detailIntent.getStringExtra("url");
        String content = detailIntent.getStringExtra("content");



        Picasso.get().load(imgUrl).into(detail_news_iv);
        detail_news_tv.setText(title);
        detail_news_desc_tv.setText(desc);
        detail_news_content_tv.setText(content);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act = new Intent(Intent.ACTION_VIEW);
                act.setData(Uri.parse(url));
                startActivity(act);
            }
        });





    }
}