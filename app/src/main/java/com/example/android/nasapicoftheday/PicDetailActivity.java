package com.example.android.nasapicoftheday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.nasapicoftheday.utils.PicList;
import com.squareup.picasso.Picasso;

import java.util.List;

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

    public void viewImageOnWeb() {
        if(mPic != null) {
            Uri picUri = Uri.parse(mPic.hdurl);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, picUri);
            PackageManager pm = getPackageManager();

            //pass a flag saying we don't want any false matches
            List<ResolveInfo> activities = pm.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);

            if(activities.size() > 0) {
                startActivity(webIntent);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                viewImageOnWeb();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pic_detail, menu);
        return true;
    }
}
