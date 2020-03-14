package com.example.android.nasapicoftheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.nasapicoftheday.utils.PicList;
import com.squareup.picasso.Picasso;

public class PicDetailActivity extends AppCompatActivity {
    public static final String EXTRA_PIC_ACTIVITY = "PicDetail";
    private PicList mPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_PIC_ACTIVITY)) {
            //launch with new intent and grab the repo
            mPic = (PicList) intent.getSerializableExtra(EXTRA_PIC_ACTIVITY);

            TextView picDescriptionTV = findViewById(R.id.tv_pic_description);
            picDescriptionTV.setText(mPic.explanation);
            picDescriptionTV.setMovementMethod(new ScrollingMovementMethod());

            TextView pictitleTV = findViewById(R.id.tv_title_detail);
            pictitleTV.setText(mPic.title);
            pictitleTV.setMovementMethod(new ScrollingMovementMethod());

            TextView picdateTV = findViewById(R.id.tv_date_detail);
            picdateTV.setText(mPic.date);
            picdateTV.setMovementMethod(new ScrollingMovementMethod());

            ImageView image = findViewById(R.id.detail_image);
            Picasso.get().load(mPic.url).into(image);


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pic_detail, menu);
        return true;
    }
}
