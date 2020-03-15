package com.example.android.nasapicoftheday.data;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.android.nasapicoftheday.utils.PicList;
import com.example.android.nasapicoftheday.utils.PicOfDayUtils;

import java.util.List;

public class PicRepo implements PicAsyncTask.Callback{
    private MutableLiveData<PicList> mSearchResults;


    public PicRepo(){
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

    }

    public LiveData<PicList> getSearchResults() {
        return mSearchResults;
    }


    public void loadSearchResults(String date) {
        String PicURL;
        if (date != null) {
            PicURL = PicOfDayUtils.buildPicSearchURL(date);
        }
        else {
            PicURL = PicOfDayUtils.buildPicSearchURL("2020-03-14");

        }

        mSearchResults.setValue(null);
        new PicAsyncTask((PicAsyncTask.Callback) this).execute(PicURL);
    }

    @Override
    public void onSearchFinished(PicList searchResults) {
        mSearchResults.setValue(searchResults);
    }
}
