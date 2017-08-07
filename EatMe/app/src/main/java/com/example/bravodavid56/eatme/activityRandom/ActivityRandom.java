package com.example.bravodavid56.eatme.activityRandom;


import android.os.AsyncTask;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.bravodavid56.eatme.*;
import com.example.bravodavid56.eatme.data.LinearLayoutManagerWithSmoothScroller;
import com.example.bravodavid56.eatme.data.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.Random;

import com.example.bravodavid56.eatme.data.DBHelper;
import com.example.bravodavid56.eatme.data.DatabaseUtils;

import static java.lang.Thread.sleep;



public class ActivityRandom extends AppCompatActivity {

    public static final String TAG = "randomActivity";
    private RecyclerView recyclerView;
    private BusinessItemAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this));
        db = new DBHelper(ActivityRandom.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new BusinessItemAdapter(cursor);
        recyclerView.setAdapter(adapter);

        button = (Button) findViewById(R.id.randomClick);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.activity_random, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button)popupView.findViewById(R.id.button_go);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(button, 50, -30);
                scrollingTest();
            }
        });
//        new TestApiCall().execute();
    }





    private void scrollingTest()
    {
        button.setVisibility(View.GONE);
        Random random = new Random();
        int i = random.nextInt(cursor.getCount());
        recyclerView.smoothScrollToPosition(i);
    }

//    class TestApiCall extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
//                URL url = NetworkUtils.buildUrl("South Gate, CA");
//                String response = NetworkUtils.getResponse(url);
//                return response;
//
//                // prints the response just to show it's calling it correctly
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            TextView tx = (TextView) findViewById(R.id.textView);
//            tx.setText(s);
//            try {
//                NetworkUtils.parseSearchJson(s);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(ActivityRandom.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new BusinessItemAdapter(cursor);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }



}