package com.example.android.nasapicoftheday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.android.nasapicoftheday.utils.NetworkUtils;
import com.example.android.nasapicoftheday.utils.PicList;
import com.example.android.nasapicoftheday.utils.PicOfDayUtils;

import java.io.IOException;

public class SavedPicsActivity extends AppCompatActivity implements PicAdapter.OnPicItemClickListener{

    private RecyclerView mSavedPicListRV;
    private PicAdapter mSavedPicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_pics);

        // setup recycler view for saved imags
        mSavedPicListRV = findViewById(R.id.rv_saved_pic_list);
        mSavedPicListRV.setLayoutManager(new LinearLayoutManager(this));
        mSavedPicAdapter = new PicAdapter(this);
        mSavedPicListRV.setAdapter(mSavedPicAdapter);
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
                mSavedPicAdapter.updatePicData(searchResultsList);
            }
            else {
            }
        }
    }
}
