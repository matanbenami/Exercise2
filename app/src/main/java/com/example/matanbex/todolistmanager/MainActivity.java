package com.example.matanbex.todolistmanager;

import android.app.FragmentManager;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import android.support.v4.app.DialogFragment;


public class MainActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener, RemoveDialogFragment.RemoveDialogListener{
    private static final String LIST = "list";

    private ArrayList<String> messagesList = new ArrayList<String>();;
    private RecyclerView rv;
    private RVAdapter adapter;
    private int PositionToDelete;
    FloatingActionButton f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            messagesList = savedInstanceState.getStringArrayList(LIST);
        }
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new RVAdapter(MainActivity.this, messagesList);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        f = (FloatingActionButton)findViewById(R.id.fab);
        f.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createAddDialog();
            }
        });
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {
                PositionToDelete = position;
                createRemoveDialog();
            }
        }));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(LIST, messagesList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                createAddDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createAddDialog(){
        android.app.FragmentManager fm = getFragmentManager();
        NoticeDialogFragment dialog = new NoticeDialogFragment();
        dialog.show(fm, "new_todo");
    }

    private void createRemoveDialog(){
        FragmentManager fm2 = getFragmentManager();
        RemoveDialogFragment alertDialog = new RemoveDialogFragment();
        alertDialog.show(fm2, "remove");
    }


    @Override
    public void onDialogPositiveClick(String answer) {

        if (!answer.equals("")){
            messagesList.add(answer);
            adapter.notifyDataSetChanged();
        }else
        {
            Toast.makeText(getApplicationContext(), "Can't add empty task", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRemoveDialogPositiveClick() {
        messagesList.remove(PositionToDelete);
        adapter.notifyDataSetChanged();
    }


    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }
        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

        @Override
        public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
    }


}

