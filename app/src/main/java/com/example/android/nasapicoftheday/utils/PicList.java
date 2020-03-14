package com.example.android.nasapicoftheday.utils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pictures")
public class PicList implements Serializable {
    public String copyright;

    @ColumnInfo(name = "date")
    public String date;
    public String explanation;
    public String hdurl;

    @PrimaryKey
    @NonNull
    public String title;
    public String url;
}
