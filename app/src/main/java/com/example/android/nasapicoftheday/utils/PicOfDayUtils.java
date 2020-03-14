package com.example.android.nasapicoftheday.utils;

import android.net.Uri;

import com.google.gson.Gson;

import java.util.ArrayList;

public class PicOfDayUtils {
    private final static String PIC_SEARCH_BASE_URL = "https://api.nasa.gov/planetary/apod";
    private final static String PIC_SEARCH_DATE_PARAM = "date"; //add value when buildpicsearchURL
    private final static String PIC_SEARCH_KEY_PARAM = "api_key";
    private final static String PIC_SEARCH_KEY_VALUE = "OtajrOetTdMjKd88Y81ishNQKAfmsv0Cv5jNYM5z";


    public static String buildPicSearchURL (String date) {  //pass in query and spit out url to use to query
        return Uri.parse(PIC_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(PIC_SEARCH_DATE_PARAM, date)
                .appendQueryParameter(PIC_SEARCH_KEY_PARAM, PIC_SEARCH_KEY_VALUE)
                .build()
                .toString();
    }

    public static PicList parsePicSearchResults(String json) { //parse json and return parsed results
        Gson gson = new Gson();
        PicList results = gson.fromJson(json, PicList.class); //returns picList obj
        if (results != null) {
            return results;
        }
        else {
            return null;
        }
    }

}
