package com.example.android.nasapicoftheday.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.nasapicoftheday.utils.PicList;

@Database(entities = {PicList.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SavedPicsDao savedPicsDao();
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "saved_pics_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}