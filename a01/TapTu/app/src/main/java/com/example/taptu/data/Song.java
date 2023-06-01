package com.example.taptu.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {
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
            jr.skipValue();
        }
        jr.endObject();
        return null;
    }
}
