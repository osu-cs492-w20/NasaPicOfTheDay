package com.example.android.nasapicoftheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.android.nasapicoftheday.utils.PicList;

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

        }

    }
}
