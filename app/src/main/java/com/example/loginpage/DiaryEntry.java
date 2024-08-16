package com.example.loginpage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "entries")
public class DiaryEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String content;
    public long date;
}
