package com.example.android.nasapicoftheday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.android.nasapicoftheday.utils.NetworkUtils;
import com.example.android.nasapicoftheday.utils.PicList;
import com.example.android.nasapicoftheday.utils.PicOfDayUtils;
import com.example.android.nasapicoftheday.utils.SavedPicsViewModel;

import java.io.IOException;
import java.util.List;

public class SavedPicsActivity extends AppCompatActivity implements SavedPicsAdapter.OnSearchResultClickListener {

    private RecyclerView mSavedPicListRV;
    private SavedPicsAdapter mSavedPicAdapter;
    private SavedPicsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_pics);

        // setup recycler view for saved imags
        mSavedPicListRV = findViewById(R.id.rv_saved_pic_list);
        mSavedPicListRV.setLayoutManager(new LinearLayoutManager(this));
        mSavedPicAdapter = new SavedPicsAdapter(this);
        mSavedPicListRV.setAdapter(mSavedPicAdapter);

        mViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedPicsViewModel.class);

        LiveData<List<PicList>> allPics = mViewModel.getAllPics();
        mViewModel.getAllPics().observe(this, new Observer<List<PicList>>() {
            @Override
            public void onChanged(List<PicList> picLists) {
                mSavedPicAdapter.updatePicResults(picLists);
            }
        });
    }


    @Override
    public void onSearchResultClicked(PicList repo) {
        Intent intent = new Intent(this, PicDetailActivity.class);
        intent.putExtra(PicDetailActivity.EXTRA_PIC_ACTIVITY, repo);
        startActivity(intent);
    }

//    public class PicSearchTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() { //called before doInBackground
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String url = strings[0];
//            System.out.println(url);
//            String searchResults = null;
//            try {
//                searchResults = NetworkUtils.doHttpGet(url);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return searchResults;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            if (s != null) {
//                PicList searchResultsList = PicOfDayUtils.parsePicSearchResults(s);
//                System.out.println(searchResultsList.title);
//                mSavedPicAdapter.updatePicData(searchResultsList);
//            }
//            else {
//            }
//        }
//    }
}
