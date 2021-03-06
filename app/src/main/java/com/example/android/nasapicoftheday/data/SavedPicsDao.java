package com.example.android.nasapicoftheday.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.nasapicoftheday.utils.PicList;

import java.util.List;

@Dao
public interface SavedPicsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PicList pic);

    @Delete
    void delete(PicList pic);

    @Query("SELECT * FROM pictures")
    LiveData<List<PicList>> getAllPics();

    @Query("SELECT * FROM pictures WHERE title = :title LIMIT 1")
    LiveData<PicList> getPicByTitle(String title);
}