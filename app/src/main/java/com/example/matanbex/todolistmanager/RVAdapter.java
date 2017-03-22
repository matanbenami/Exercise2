package com.example.matanbex.todolistmanager;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CustomViewHolder> {
    private List<String> messagesItemList;
    private Context mContext;
    private AdapterView.OnItemClickListener onItemClickListener;

    public RVAdapter(Context context, List<String> feedItemList) {
        this.messagesItemList = feedItemList;
        this.mContext = context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        customViewHolder.textView.setLongClickable(true);
        final String todo = messagesItemList.get(i);
        if ((i % 2) == 0){
            customViewHolder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_1));
        }
        else
        {
            customViewHolder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_2));
        }
        customViewHolder.textView.setText(todo);
    }


    @Override
    public int getItemCount() {
        return (null != messagesItemList ? messagesItemList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        protected TextView textView;


        public CustomViewHolder(View view) {
            super(view);
            view.setOnLongClickListener(this);
            this.textView = (TextView) view.findViewById(R.id.message);
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }

}