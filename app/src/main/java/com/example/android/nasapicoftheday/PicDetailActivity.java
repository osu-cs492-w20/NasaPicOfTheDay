package com.example.android.nasapicoftheday;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.nasapicoftheday.utils.PicList;
import com.example.android.nasapicoftheday.utils.SavedPicsViewModel;
import com.squareup.picasso.Picasso;


import java.util.UUID;

import dmax.dialog.SpotsDialog;

import java.util.List;


public class PicDetailActivity extends AppCompatActivity {
    public static final String EXTRA_PIC_ACTIVITY = "PicDetail";
    private PicList mPic;
    private boolean mIsSaved = false;


    private SavedPicsViewModel mViewModel;


    ImageView myImageDownload;

    private static final int PERMISSION_REQUEST_CODE = 1000;

    private Button downloadBtn;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);

        mViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedPicsViewModel.class);

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

        final ImageView picSavedIcon = findViewById(R.id.iv_repo_bookmark);
        picSavedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPic != null) {
                    mIsSaved = !mIsSaved;
                    if (mIsSaved) {
                        mViewModel.insertSavedPic(mPic);
                        picSavedIcon.setImageResource(
                                R.drawable.ic_add_circle_black_24dp
                        );
                    } else {
                        mViewModel.deleteSavedPic(mPic);
                        picSavedIcon.setImageResource(
                                R.drawable.ic_add_circle_outline_black_24dp
                        );
                    }
                }

            }
        });



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }

        downloadBtn = findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(PicDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PicDetailActivity.this, "you should grant permission", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_REQUEST_CODE);
                    return;
                }
                else {
                    SpotsDialog dialog = new SpotsDialog(PicDetailActivity.this);
                    dialog.show();
                    dialog.setMessage("Downloading...");

                    String fileName = UUID.randomUUID().toString()+".jpg";
                    Picasso.get()
                            .load(mPic.url)
                            .into(new SaveImageHelper(getBaseContext(),
                                    dialog,
                                    getApplicationContext().getContentResolver(),
                                    fileName,
                                    "Image description"));

                }
            }
        });

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
