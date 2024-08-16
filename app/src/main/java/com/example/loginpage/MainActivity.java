package com.example.loginpage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView recyclerView;
    private DiaryEntryAdapter adapter;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply Edge-to-Edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database, RecyclerView, and ExecutorService
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "diary.db").build();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        executorService = Executors.newSingleThreadExecutor();

        // Load diary entries in the background using ExecutorService
        executorService.execute(() -> {
            List<DiaryEntry> entries = db.diaryEntryDao().getAllEntries();
            runOnUiThread(() -> {
                adapter = new DiaryEntryAdapter(entries);
                recyclerView.setAdapter(adapter);
            });
        });

        // Set up FloatingActionButton to add new entries
        FloatingActionButton addEntryButton = findViewById(R.id.add_entry_button);
        addEntryButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditEntryActivity.class);
            startActivity(intent);
        });
    }
}
