package com.example.loginpage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class AddEditEntryActivity extends AppCompatActivity {

    private AppDatabase db;
    private EditText titleEditText;
    private EditText contentEditText;
    private boolean isEdit = false;
    private int entryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_entry);

        // Initialize Room database instance
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build();

        // Initialize EditText fields
        titleEditText = findViewById(R.id.title);
        contentEditText = findViewById(R.id.content);

        // Initialize Save button
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveEntry());

        // Check if we're editing an existing entry
        Intent intent = getIntent();
        if (intent.hasExtra("entryId")) {
            isEdit = true;
            entryId = intent.getIntExtra("entryId", -1);
            loadEntry(entryId);
        }
    }

    private void saveEntry() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        long date = System.currentTimeMillis();

        DiaryEntry entry = new DiaryEntry();
        entry.title = title;
        entry.content = content;
        entry.date = date;

        new SaveEntryTask().execute(entry);
    }

    private void loadEntry(int entryId) {
        new LoadEntryTask().execute(entryId);
    }

    private class SaveEntryTask extends AsyncTask<DiaryEntry, Void, Void> {
        @Override
        protected Void doInBackground(DiaryEntry... entries) {
            if (isEdit) {
                entries[0].id = entryId;
                db.diaryEntryDao().update(entries[0]);
            } else {
                db.diaryEntryDao().insert(entries[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
        }
    }

    private class LoadEntryTask extends AsyncTask<Integer, Void, DiaryEntry> {
        @Override
        protected DiaryEntry doInBackground(Integer... ids) {
            return db.diaryEntryDao().getEntryById(ids[0]);
        }

        @Override
        protected void onPostExecute(DiaryEntry entry) {
            if (entry != null) {
                titleEditText.setText(entry.title);
                contentEditText.setText(entry.content);
            }
        }
    }
}
