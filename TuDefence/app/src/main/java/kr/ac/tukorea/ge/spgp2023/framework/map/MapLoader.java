package kr.ac.tukorea.ge.spgp2023.framework.map;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
            Log.d(TAG, "Map JSON TMJ key = " + name);
            if (name.equals("width")) {
                map.width = reader.nextInt();
            } else if (name.equals("height")) {
                map.height = reader.nextInt();
            } else {
                Log.d(TAG, " -- Skipping");
                reader.skipValue();
            }
        }

        return null;
    }
}
