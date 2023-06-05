package kr.ac.tukorea.ge.spgp2023.taptu.data;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.taptu.json.JsonHelper;

public class Song {
    private static final String TAG = Song.class.getSimpleName();

    //////////////////////////////////////////////////
    /// from songs.json
    public String title, artist, album, cover, music, noteFile;
    public int demoStart, demoEnd;
    //////////////////////////////////////////////////

    public class Note {
        public int lane;
        public int msec;
        Note(String line) {
            String[] comps = line.split("\\s+");

            lane = Integer.parseInt(comps[1]);
            msec = Integer.parseInt(comps[2]);
        }
    }
    public ArrayList<Note> notes = new ArrayList<>();
    private float length;

    public boolean loadNotes(AssetManager assets) {
        notes.clear();
        try {
            InputStream is = assets.open(noteFile);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String title = this.title;
            int msec = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("T ")) {
                    title = line.substring(2).trim();
                } else if (line.startsWith("N")) {
                    Note note = new Note(line);
                    notes.add(note);
                    if (msec < note.msec) {
                        msec = note.msec;
                    }
                }
            }
            is.close();
            length = msec / 1000.0f;
            Log.d(TAG, "Title from nodeFile: " + title);
            Log.d(TAG, "Notes loaded: " + notes.size());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    protected static ArrayList<Song> songs;
    protected static Handler handler = new Handler();
    private static Context context;
    private MediaPlayer mediaPlayer;

    public static ArrayList<Song> loadSongs(Context context, String filename) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Song.context = context;
            AssetManager assets = context.getAssets();
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
                if (!JsonHelper.readProperty(song, name, jr)) {
                    jr.skipValue();
                }
            }
            jr.endObject();
        } catch (IOException e) {
            return null;
        }

        return song;
    }

    public static Song get(int index) {
        return songs.get(index);
    }

    public Bitmap getThumbnail() {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(cover));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void playDemo() {
        stop();
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(music);
            FileDescriptor fd = afd.getFileDescriptor();
            Log.d(TAG, "music=" + music + " afd=" + afd + " fd=" + fd);
            mediaPlayer = new MediaPlayer();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                mp.setDataSource(afd);
//            }
            MediaPlayer mp = mediaPlayer;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mp.stop();
                    if (mp == mediaPlayer) {
                        mediaPlayer = null;
                    }
                }
            }, demoEnd - demoStart);
            mediaPlayer.setDataSource(fd, afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.seekTo(demoStart);
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public void play() {
        stop();
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(music);
            FileDescriptor fd = afd.getFileDescriptor();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fd, afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentTime() {
        if (mediaPlayer == null) return 0;
        return mediaPlayer.getCurrentPosition();
    }
}
