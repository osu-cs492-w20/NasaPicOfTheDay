package com.example.android.nasapicoftheday;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;

import com.example.android.nasapicoftheday.utils.NetworkUtils;
import com.example.android.nasapicoftheday.utils.PicList;
import com.example.android.nasapicoftheday.utils.PicOfDayUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements PicAdapter.OnPicItemClickListener {

    private RecyclerView mPicListRV;
    private PicAdapter mPicAdapter;
    private String date;
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //recyclerView setup
        mPicListRV = findViewById(R.id.rv_pic_list);
        mPicListRV.setLayoutManager(new LinearLayoutManager(this));
        mPicAdapter = new PicAdapter(this);
        mPicListRV.setAdapter(mPicAdapter);

        String date = getCurrentDate();

        // get pictures and display them
        int num_pictures = 7;
        for (int i=0; i < num_pictures; i++){
            date = getpreviousDate(date);
            doPicSearch(date);
        }

    }

    public String getpreviousDate (String date) {
        char c = date.charAt(9);
        char d = date.charAt(8);
        char m1 = date.charAt(5);
        char m2 = date.charAt(6);
        int flag = 0;
        if (d == 48 && c == 49) {
            if (m2 == 48) {
                m1 = 48;
                m2 = 57;
            }
            else {
                m2 = (char)(m2-1);
            }
            date = date.substring(0, 5)
                    + m1
                    + date.substring(6);
            date = date.substring(0, 6)
                    + m2
                    + date.substring(7);
//            System.out.println("date3: " + date);
        }
        if (c == 49 && d == 48) {
            System.out.println("here");
            c = 48;
            d = 51;
            flag = 1;
            date = date.substring(0, 8)
                    + d
                    + date.substring(9);
            date = date.substring(0, 9)
                    + c
                    + date.substring(10);
        }
        else if (c == 48 && flag == 0) {
            if (d == 51) {
                d = 50;
            }
            else if (d == 50) {
                d = 49;
            }
            else if (d == 49) {
                d = 48;
            }
            date = date.substring(0, 8)
                    + d
                    + date.substring(9);
            date = date.substring(0, 9)
                    + '9'
                    + date.substring(10);
//            System.out.println("date1: " + date);
        }
        else {
            c = (char) (c-1);
            date = date.substring(0, 9)
                    + c
                    + date.substring(10);
//            System.out.println("date2: " + date);
        }
        System.out.println("final date: " + date);
        return date;
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_3);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);

    }

    public void doPicSearch(String date) {
        String url = PicOfDayUtils.buildPicSearchURL(date);
        new PicSearchTask().execute(url);
    }

    @Override
    public void onPicItemClick(PicList pic) {
        Intent picActivityDetailIntent = new Intent(this, PicDetailActivity.class);
        picActivityDetailIntent.putExtra(
                PicDetailActivity.EXTRA_PIC_ACTIVITY,
                pic);
        startActivity(picActivityDetailIntent);
    }

    public class PicSearchTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() { //called before doInBackground
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            System.out.println(url);
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                PicList searchResultsList = PicOfDayUtils.parsePicSearchResults(s);
                System.out.println(searchResultsList.title);
                mPicAdapter.updatePicData(searchResultsList);
            }
            else {
            }
        }
    }
}
