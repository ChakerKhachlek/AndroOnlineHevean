package com.example.onlineheaven.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.onlineheaven.R;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder> {
    Context context;
    List<String> HistoryList;

    public HistoryRecyclerAdapter(Context context, List<String> HistoryList){
        this.context=context;
        this.HistoryList=HistoryList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryRecyclerAdapter.HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.historyItem.setText(HistoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return HistoryList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView historyItem;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            historyItem=itemView.findViewById(R.id.history_item);
        }
    }
}
