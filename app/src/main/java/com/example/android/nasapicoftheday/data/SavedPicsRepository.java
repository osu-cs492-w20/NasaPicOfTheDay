package com.example.android.nasapicoftheday.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import com.example.android.nasapicoftheday.utils.PicList;

import java.util.List;

public class SavedPicsRepository {
    private SavedPicsDao mSavedPicsDao;

    public SavedPicsRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mSavedPicsDao = db.savedPicsDao();
    }

    public void insertSavedPic(PicList pic) {
        new InsertPicAsyncTask(mSavedPicsDao).execute(pic);
    }

    public void deleteSavedRepo(PicList pic) { new DeletePicAsyncTask(mSavedPicsDao).execute(pic); }

    public LiveData<List<PicList>> getAllPics() {
        return mSavedPicsDao.getAllPics();
    }

    public LiveData<PicList> getPicByTitle(String title) { return mSavedPicsDao.getPicByTitle(title); }


    private static class InsertPicAsyncTask extends AsyncTask<PicList, Void, Void> {
        private SavedPicsDao mAsyncTaskDao;

        InsertPicAsyncTask(SavedPicsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(PicList... picLists) {
            mAsyncTaskDao.insert(picLists[0]);
            return null;
        }
    }

    private static class DeletePicAsyncTask extends AsyncTask<PicList, Void, Void> {
        private SavedPicsDao mAsyncTaskDao;

        DeletePicAsyncTask(SavedPicsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(PicList... picLists) {
            mAsyncTaskDao.delete(picLists[0]);
            return null;
        }
    }
}