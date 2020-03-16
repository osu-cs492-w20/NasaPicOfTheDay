package com.example.android.nasapicoftheday.utils;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.nasapicoftheday.data.SavedPicsRepository;

import java.util.List;

public class SavedPicsViewModel extends AndroidViewModel {
    private SavedPicsRepository mSavedPicsRepository;

    public SavedPicsViewModel(Application application) {
        super(application);
        mSavedPicsRepository = new SavedPicsRepository(application);
    }

    public void insertSavedPic(PicList pic) { mSavedPicsRepository.insertSavedPic(pic); }

    public void deleteSavedPic(PicList pic) { mSavedPicsRepository.deleteSavedRepo(pic); }

    public LiveData<List<PicList>> getAllPics() {
        return mSavedPicsRepository.getAllPics();
    }



}