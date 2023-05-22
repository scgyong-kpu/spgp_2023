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

    public void draw(Canvas canvas) {
        draw(canvas, 0, 0);
    }
    public void draw(Canvas canvas, int tilesetIndex, int layerIndex) {
        try {
            TiledLayer layer = layers.get(layerIndex);
            TiledTileset tileset = tilesets.get(tilesetIndex);
            tileset.draw(canvas, layer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
