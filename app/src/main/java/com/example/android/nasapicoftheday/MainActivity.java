package com.example.android.nasapicoftheday;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PicAdapter.OnPicItemClickListener {

    private RecyclerView mPicListRV;
    private PicAdapter mPicAdapter;

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



        doPicSearch();

    }

    public void doPicSearch() {
        String url = PicOfDayUtils.buildPicSearchURL("2020-03-14");
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
