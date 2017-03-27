package com.example.matanbex.todolistmanager;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    private static final int RETURN_MESSAGE = 1;
    private static final int RETURN_DATE = 0;
    static String mPhoneNumber ="";

    public RVAdapter(Context context, List<String> messageItemList) {
        this.messagesItemList = messageItemList;
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
        customViewHolder.todoMessage.setLongClickable(true);
        final String todo = messagesItemList.get(i);
        if ((i % 2) == 0){
            customViewHolder.todoMessage.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_1));
            customViewHolder.date.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_1));
        }
        else
        {
            customViewHolder.todoMessage.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_2));
            customViewHolder.date.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_2));
        }
        customViewHolder.todoMessage.setText(splitMessage(todo, RETURN_MESSAGE));
        customViewHolder.date.setText(splitMessage(todo, RETURN_DATE));
    }

    private String splitMessage(String message, int messageOrDate){
        String ret = "";
        String delims = " ";
        String[] tokens = message.split(delims);
        int len = tokens.length;
        if (messageOrDate == 1){
            for (int i = 0; i < len - 1; i++){
                ret += (tokens[i]);
                ret += " ";
            }
            ret = ret.substring(0, ret.length() - 1);
        }
        else{

            ret = tokens[len - 1];
        }
        return ret;
    }



    @Override
    public int getItemCount() {
        return (null != messagesItemList ? messagesItemList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener {
        protected TextView todoMessage;
        protected TextView date;


        public CustomViewHolder(View view) {
            super(view);
            view.setOnLongClickListener(this);
            this.date = (TextView) view.findViewById(R.id.date);
            this.todoMessage = (TextView) view.findViewById(R.id.message);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            TextView messageView = (TextView) v.findViewById(R.id.message);
            String message = messageView.getText().toString();
            menu.setHeaderTitle("Select what to do:");
            if (isCall(message)){
                menu.add(0, v.getId(), 0, "Call");
                menu.add(0, v.getId(), 0, "Remove");
            }else{
                menu.add(0, v.getId(), 0, "Remove");
            }
        }





        private boolean isCall(String message){
            String delim = "\\s+";
            String[] tokens = message.split(delim);
            System.out.println(tokens[0]);
            if (tokens[0].equals("call") && (tokens.length > 0)){
                if (Character.isDigit(tokens[1].charAt(0))){
                     mPhoneNumber = tokens[1];
                    return true;
                }
                return false;
            }
            return false;
        }
    }




}