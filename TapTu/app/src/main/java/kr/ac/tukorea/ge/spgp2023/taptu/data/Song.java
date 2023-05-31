package kr.ac.tukorea.ge.spgp2023.taptu.data;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {
    public static ArrayList<Song> load(Context context, String filename) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open(filename);
            JsonReader jr = new JsonReader(new InputStreamReader(is));
            jr.beginArray();
            while (jr.hasNext()) {
                Song song = loadSong(jr);
                if (song != null) {
                    songs.add(song);
                }
            }
            jr.endArray();
            jr.close();
        } catch (IOException e) {
        }
        return songs;
    }

    private static Song loadSong(JsonReader jr) {
        return null;
    }
}
