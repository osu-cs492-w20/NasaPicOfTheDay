package com.example.android.nasapicoftheday.data;

import android.os.AsyncTask;


import com.example.android.nasapicoftheday.utils.NetworkUtils;
import com.example.android.nasapicoftheday.utils.PicList;
import com.example.android.nasapicoftheday.utils.PicOfDayUtils;

import java.io.IOException;
import java.util.List;

public class PicAsyncTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;


    public interface Callback {
        void onSearchFinished(PicList searchResults);
    }

    public PicAsyncTask(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String openWeatherMapURL = params[0];
        String picJSON = null;
        try {
            picJSON = NetworkUtils.doHttpGet(openWeatherMapURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        PicList searchResults = null;
        if (s != null) {
            searchResults = PicOfDayUtils.parsePicSearchResults(s);
        }
        mCallback.onSearchFinished(searchResults);
    }
}
