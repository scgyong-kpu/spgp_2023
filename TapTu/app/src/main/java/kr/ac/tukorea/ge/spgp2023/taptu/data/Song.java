package kr.ac.tukorea.ge.spgp2023.taptu.data;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {
    private static final String TAG = Song.class.getSimpleName();

    public static ArrayList<Song> loadSongs(Context context, String filename) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open(filename);
            JsonReader jr = new JsonReader(new InputStreamReader(is));
            jr.beginArray();
            while (jr.hasNext()) {
                Song song = loadSong(jr);
                if (song != null) {
                    songs.add(song);
                    Log.d(TAG, "Songs count = " + songs.size());
                }
            }
            jr.endArray();
            jr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }

    private static Song loadSong(JsonReader jr) {
        Song song = new Song();
        try {
            jr.beginObject();
            while (jr.hasNext()) {
                String name = jr.nextName();
                Log.d(TAG, "key: " + name);
                jr.skipValue();
            }
            jr.endObject();
        } catch (IOException e) {
            return null;
        }

        return song;
    }
}
