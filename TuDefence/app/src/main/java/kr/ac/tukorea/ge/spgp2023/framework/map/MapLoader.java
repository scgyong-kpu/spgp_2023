package kr.ac.tukorea.ge.spgp2023.framework.map;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import kr.ac.tukorea.ge.spgp2023.framework.view.GameView;

public class MapLoader {
    private static final String TAG = MapLoader.class.getSimpleName();
    private final AssetManager assets;
    private String folder;

    public MapLoader() {
        this.assets = GameView.view.getContext().getAssets();
    }

    public TiledMap loadAsset(String folder, String tmjFile) {
        this.folder = folder;
        try {
            InputStream is = assets.open(folder + "/" + tmjFile);
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            return readMap(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private TiledMap readMap(JsonReader reader) throws IOException {
        Log.v(TAG, "Reading map:");
        TiledMap map = new TiledMap();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            //Log.d(TAG, "Map JSON TMJ key = " + name);
            if (readProperty(map, name, reader)) {
                Log.d(TAG, " - did read in readProperty()");
            } else {
                //Log.d(TAG, " -- Skipping");
                reader.skipValue();
            }
        }

        return null;
    }

    private boolean readProperty(Object object, String name, JsonReader reader) throws IOException {
        try {
            Field field = object.getClass().getField(name);
            Class<?> type = field.getType();
            if (type == int.class) {
                int value = reader.nextInt();
                Log.d(TAG, "Int " + name + ": " + value + " - " + object);
                field.setInt(object, value);
            } else if (type == String.class) {
                String value = reader.nextString();
                Log.d(TAG, "String " + name + ": " + value + " - " + object);
                field.set(object, value);
            } else {
                Log.e(TAG, "Not handling " + name + ". type: " + type + " - " + object);
                return false;
            }
            return true;
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "No field \"" + name + "\" in " + object);
//            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
            return false;
        }
    }
}
