package kr.ac.tukorea.ge.spgp2023.framework.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class TiledTileset {
    private static final String TAG = TiledTileset.class.getSimpleName();
    private final TiledMap map;
    protected Bitmap bitmap;

    //////////////////////////////////////////////////
    // from tmj json
    public int columns, tilecount;
    public int tilewidth, tileheight;
    public int imagewidth, imageheight;
    public int margin, spacing;
    public String image;
    //////////////////////////////////////////////////


    public TiledTileset(TiledMap map) {
        this.map = map;
    }

    public boolean loadAssetBitmap(AssetManager assets, String folder) {
        try {
            String fileName = folder + "/" + image;
            InputStream is = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(is);
            Log.d(TAG, "Loaded bitmap from file: " + fileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //////////////////////////////////////////////////
}
