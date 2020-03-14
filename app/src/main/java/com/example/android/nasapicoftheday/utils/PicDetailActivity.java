package com.example.android.nasapicoftheday.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class PicDetailActivity extends AppCompatActivity {
    public static final String EXTRA_PIC_REPO = "PicRepo";

    private PicList mPic;
    private boolean mIsSaved = false;

    private SavedPicsViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedPicsViewModel.class);

//        THIS IS WHERE THE ONCLICK FOR THE STAR WILL BE
//                IN ORDER TO SAVE THE PICTURE AND INSERT IT INTO THE REPO
    }
}