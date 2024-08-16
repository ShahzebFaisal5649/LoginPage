package com.example.loginpage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiaryEntryDao {

    @Insert
    void insert(DiaryEntry entry);

    @Query("SELECT * FROM entries")
    List<DiaryEntry> getAllEntries();

    @Query("SELECT * FROM entries WHERE id = :id")
    DiaryEntry getEntryById(int id);

    @Update
    void update(DiaryEntry entry);

    @Delete
    void delete(DiaryEntry entry);
}
