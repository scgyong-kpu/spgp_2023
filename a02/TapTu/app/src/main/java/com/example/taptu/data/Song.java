package com.example.taptu.data;

import android.content.Context;
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
    private static final String TAG = Song.class.getSimpleName();

    //////////
    // from songs.json
    public String title, artist, album, cover, music, noteFile;
    public int demoStart, demoEnd;
    //////////

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
                    //Log.v(TAG, "key: " + key);
                    if (!JsonHelper.readProperty(song, key, jr)) {
                        jr.skipValue();
                    }
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

    public Bitmap getBitmap(Context context) {
        try {
            InputStream is = context.getAssets().open(cover);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
