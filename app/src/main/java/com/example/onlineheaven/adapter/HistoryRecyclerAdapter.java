package com.example.onlineheaven.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.onlineheaven.R;
import com.example.onlineheaven.fragements.HistoryFragment;
import com.example.onlineheaven.model.History;
import com.example.onlineheaven.sqllite.DatabaseHelper;

import java.util.List;

import static java.security.AccessController.getContext;


public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder> {
    Context context;
    List<History> HistoryList;

    public HistoryRecyclerAdapter(Context context, List<History> HistoryList){
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


        holder.historyItem.setText(HistoryList.get(position).getHistory());
        holder.historyItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage("Remove from history");
                alertbox.setTitle("remove");
                alertbox.setIcon(R.drawable.not_favourite);
                alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(HistoryList.get(position));

                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertbox.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return HistoryList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView historyItem;
        Button buttonClear;



        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            buttonClear=(Button) itemView.findViewById(R.id.clear_history);

            historyItem=itemView.findViewById(R.id.history_item);
            historyItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void removeItem(History item){

        int currPosition = HistoryList.indexOf(item);
        HistoryList.remove(currPosition);
        DatabaseHelper db=(DatabaseHelper) new DatabaseHelper(this.context);
        db.deleteSingle(item.getId());

        notifyItemRemoved(currPosition);


    }
}
