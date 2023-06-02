package kr.ac.tukorea.ge.spgp2023.taptu.app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.taptu.R;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;
import kr.ac.tukorea.ge.spgp2023.taptu.databinding.ActivityMainBinding;
import kr.ac.tukorea.ge.spgp2023.taptu.databinding.SongItemBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private ArrayList<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        songs = Song.loadSongs(this, "songs.json");

        binding.listView.setAdapter(listAdapter);
        binding.listView.setOnItemClickListener(onItemClick);
    }

    private BaseAdapter listAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            Log.d(TAG, "getCount() is called");
            return songs.size();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.d(TAG, "getView(" + i + ") is called. view=" + view);
            SongItemBinding binding;
            if (view == null) {
                binding = SongItemBinding.inflate(getLayoutInflater());
                view = binding.getRoot();
                view.setTag(binding);
            } else {
                binding = (SongItemBinding) view.getTag();
            }
            Song song = songs.get(i);
            binding.title.setText(song.title);
            binding.artist.setText(song.artist);
            binding.album.setText(song.album);
            binding.thumbnail.setImageBitmap(song.getThumbnail());
            return view;
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

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            view.setSelected(true);
            Song song = songs.get(i);
            Log.d(TAG, "Selected: " + song.title);
        }
    };

    private Bitmap loadAssetBitmap(String filename) {
        try {
            return BitmapFactory.decodeStream(getAssets().open(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onBtnStart(View view) {
    }
}