package com.example.loginpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {

    private List<DiaryEntry> diaryEntries;

    public DiaryEntryAdapter(List<DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_entry_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // You can bind your diary entry data here if needed
        // Example:
        // DiaryEntry entry = diaryEntries.get(position);
        // holder.titleTextView.setText(entry.getTitle());
        // holder.contentTextView.setText(entry.getContent());
    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }

    public void setDiaryEntries(List<DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // You can initialize your item views here if needed
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Example initialization:
            // titleTextView = itemView.findViewById(R.id.title);
            // contentTextView = itemView.findViewById(R.id.content);
        }
    }
}
