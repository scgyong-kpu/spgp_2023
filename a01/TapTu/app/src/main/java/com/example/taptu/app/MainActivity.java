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
import com.example.taptu.databinding.SongItemBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private ArrayList<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        songs = Song.loadSongs(this, "songs.json");
        binding.listView.setAdapter(listAdapter);
    }
    private ListAdapter listAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            Log.d(TAG, "getCount() is called");
            return songs.size();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.d(TAG, "getView(" + i + ") is called. view=" + view);
            SongItemBinding binding = SongItemBinding.inflate(getLayoutInflater());
            Song song = songs.get(i);
            Log.d(TAG, "song=" + song);
            binding.title.setText(song.title);
            binding.artist.setText(song.artist);
            binding.album.setText(song.album);
            binding.thumbnail.setImageBitmap(song.getThumbnail(MainActivity.this));
            return binding.getRoot();
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