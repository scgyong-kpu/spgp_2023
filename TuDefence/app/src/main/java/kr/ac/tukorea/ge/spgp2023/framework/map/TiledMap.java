package kr.ac.tukorea.ge.spgp2023.framework.map;

import android.graphics.Canvas;

import java.util.ArrayList;

public class TiledMap {
    private static final String TAG = TiledMap.class.getSimpleName();
    ArrayList<TiledTileset> tilesets;
    ArrayList<TiledLayer> layers;
    ////////////////////////////////////////////////////////////
    // from tmj
    public int width, height;
    public int tilewidth, tileheight;
    ////////////////////////////////////////////////////////////

    public float scale = 1.0f;
    public boolean wraps;

    public TiledLayer getLayerAt(int index) {
        return layers.get(index);
    }

    public void draw(Canvas canvas, float x, float y) {
        draw(canvas, x, y, 0, 0);
    }
    public void draw(Canvas canvas, float x, float y, int tilesetIndex, int layerIndex) {
        try {
            TiledLayer layer = layers.get(layerIndex);
            TiledTileset tileset = tilesets.get(tilesetIndex);
            tileset.draw(canvas, layer, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
