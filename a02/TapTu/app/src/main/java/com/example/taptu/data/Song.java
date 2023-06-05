package com.example.taptu.data;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {
    private static final String TAG = Song.class.getSimpleName();

    public static ArrayList<Song> loadSongs(Context context) {
        ArrayList<Song> songs = new ArrayList<Song>();
        try {
            InputStream is = context.getAssets().open("songs.json");
            JsonReader jr = new JsonReader(new InputStreamReader(is));
            jr.beginArray();
            while (jr.hasNext()) {
                Song song = new Song();
                jr.beginObject();
                while (jr.hasNext()) {
                    String key = jr.nextName();
                    Log.v(TAG, "key: " + key);
                    jr.skipValue();
                }
                jr.endObject();
                songs.add(song);
                Log.d(TAG, "Song count = " + songs.size());
            }
            jr.endArray();
            jr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }
}
