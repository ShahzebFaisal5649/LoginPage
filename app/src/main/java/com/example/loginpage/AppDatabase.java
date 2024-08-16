package com.example.loginpage;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, DiaryEntry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract DiaryEntryDao diaryEntryDao();

    private static volatile AppDatabase INSTANCE;

    // Singleton pattern for AppDatabase
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()  // Handles schema changes by recreating the database
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
