package com.example.taptu.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {
    private static final String TAG = Song.class.getSimpleName();

    public static ArrayList<Song> loadSongs(Context context, String filename) {
        AssetManager assets = context.getAssets();
        ArrayList<Song> songs = new ArrayList<>();
        try {
            InputStream is = assets.open(filename);
            InputStreamReader isr = new InputStreamReader(is);
            JsonReader jr = new JsonReader(isr);
            jr.beginArray();
            while (jr.hasNext()) {
                // read a song object
                Song song = loadSong(jr);
                songs.add(song);
                Log.d(TAG, "Songs count = " + songs.size());
            }
            jr.endArray();
            jr.close();
        } catch (Exception e) {
            e.printStackTrace();
            //return songs;
        }
        return songs;
    }

    private static Song loadSong(JsonReader jr) throws IOException {
        jr.beginObject();
        while (jr.hasNext()) {
            String name = jr.nextName();
            Log.d(TAG, "key(" + name + ")");
            jr.skipValue();
        }
        jr.endObject();
        return null;
    }
}
