package com.example.taptu.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.taptu.R;
import com.example.taptu.data.Song;
import com.example.taptu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Song.loadSongs(this);

        binding.listView.setAdapter(listAdapter);
    }
    private ListAdapter listAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            Log.d(TAG, "getCount() is called");
            return 100;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.d(TAG, "- getView(" + i + ") is called. view=" + view);
            TextView tv;
            if (view == null) {
                tv = new TextView(MainActivity.this);
                tv.setHeight(200);
                tv.setGravity(Gravity.CENTER_VERTICAL);
            } else {
                tv = (TextView) view;
            }
            tv.setText("TextView #" + i);
            return tv;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

    };


    public void onBtnStart(View view) {
    }
}