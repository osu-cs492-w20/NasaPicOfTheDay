package com.example.android.nasapicoftheday;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.nasapicoftheday.data.PicRepo;
import com.example.android.nasapicoftheday.utils.PicList;

import java.util.List;

public class PicViewModel extends ViewModel {
    private PicRepo mRepository;
    private LiveData<PicList> mSearchResults;


    public PicViewModel() {
        mRepository = new PicRepo();
        mSearchResults = mRepository.getSearchResults();
    }

    public void loadSearchResults(String date) {
        mRepository.loadSearchResults(date);
    }


    public LiveData<PicList> getSearchResults() {
        return mSearchResults;
    }



}

