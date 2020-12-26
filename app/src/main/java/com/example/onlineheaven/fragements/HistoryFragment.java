package com.example.onlineheaven.fragements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineheaven.R;
import com.example.onlineheaven.adapter.HistoryRecyclerAdapter;
import com.example.onlineheaven.sqllite.DatabaseHelper;

import java.util.List;

public class HistoryFragment extends Fragment {
    RecyclerView historyRecycler;
    HistoryRecyclerAdapter historyAdapter;
    List<String> historyList;
    Button clearButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_history, container, false);

        clearButton=v.findViewById(R.id.clear_history);
        DatabaseHelper db=(DatabaseHelper) new DatabaseHelper(this.getContext());

        historyRecycler =
                v.findViewById(R.id.history_recycler);

        LinearLayoutManager
                layoutManager = new
                LinearLayoutManager(getActivity());

        historyRecycler.setLayoutManager(layoutManager);


        Integer userId = getArguments().getInt("userID");
        historyList=db.getAllHistory(userId);


        historyAdapter = new HistoryRecyclerAdapter(getActivity(), historyList);
        historyRecycler.setAdapter(historyAdapter);

        Integer elementCount=historyList.size();
        if(elementCount ==0){
            clearButton.setVisibility(View.INVISIBLE);
        }


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            db.deleteAll();

            historyList=db.getAllHistory(userId);
            historyAdapter = new HistoryRecyclerAdapter(getActivity(), historyList);
            historyRecycler.setAdapter(historyAdapter);
            }
        });




        return v;
    }
}