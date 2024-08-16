package com.example.loginpage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class EntryDetailActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "diary.db").build();

        TextView titleTextView = findViewById(R.id.title);
        TextView contentTextView = findViewById(R.id.content);

        int entryId = getIntent().getIntExtra("entryId", -1);

        new AsyncTask<Void, Void, DiaryEntry>() {
            @Override
            protected DiaryEntry doInBackground(Void... voids) {
                return db.diaryEntryDao().getEntryById(entryId);
            }

            @Override
            protected void onPostExecute(DiaryEntry entry) {
                if (entry != null) {
                    titleTextView.setText(entry.title);
                    contentTextView.setText(entry.content);
                }
            }
        }.execute();
    }
}
