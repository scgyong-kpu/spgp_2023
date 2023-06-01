package com.example.taptu.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.Log;

import com.example.taptu.app.MainActivity;
import com.example.taptu.json.JsonHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {
    //////////////////////////////////////////////////
    /// from songs.json
    public String title, artist, album, cover;
    //////////////////////////////////////////////////

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
        Song song = new Song();
        jr.beginObject();
        while (jr.hasNext()) {
            String name = jr.nextName();
            if (!JsonHelper.readProperty(song, name, jr)) {
                jr.skipValue();
            }
        }
        jr.endObject();
        return song;
    }

    public Bitmap getThumbnail(Context context) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(cover));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
