package kr.ac.tukorea.ge.spgp2023.taptu.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.taptu.json.JsonHelper;

public class Song {
    private static final String TAG = Song.class.getSimpleName();

    //////////////////////////////////////////////////
    /// from songs.json
    public String title, artist, album, cover;
    //////////////////////////////////////////////////

    protected static ArrayList<Song> songs;
    protected static AssetManager assets;
    public static ArrayList<Song> loadSongs(Context context, String filename) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            assets = context.getAssets();
            InputStream is = assets.open(filename);
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
        Song.songs = songs;
        return songs;
    }

    private static Song loadSong(JsonReader jr) {
        Song song = new Song();
        try {
            jr.beginObject();
            while (jr.hasNext()) {
                String name = jr.nextName();
                JsonHelper.readProperty(song, name, jr);
            }
            jr.endObject();
        } catch (IOException e) {
            return null;
        }

        return song;
    }

    public Bitmap getThumbnail() {
        try {
            return BitmapFactory.decodeStream(assets.open(cover));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
