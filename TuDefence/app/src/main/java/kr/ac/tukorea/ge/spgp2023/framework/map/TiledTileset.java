package kr.ac.tukorea.ge.spgp2023.framework.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

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

    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();

    public void draw(Canvas canvas, TiledLayer layer) {
        float canvasWidth = Metrics.game_width;
        float canvasHeight = Metrics.game_height;

        float sx = 0, sy = 0;

        int width = map.width;

        int tile_x = 0;
        int tile_y = 0;

        float beg_x = 0;
        float beg_y = 0;
        dstRect.top = beg_y;
        dstRect.bottom = beg_y + 1;
        int ty = tile_y;
        while (dstRect.top < canvasHeight) {
            if (ty >= 0) {
                dstRect.left = beg_x;
                dstRect.right = beg_x + 1;
                int tx = tile_x;
                int ti = ty * width + tx;
                while (dstRect.left < canvasWidth) {
                    if (ti >= layer.data.length) {
                        break;
                    }
                    int tile = layer.data[ti];
                    getRectForTile(tile, srcRect);
//                    Log.d(TAG, "src=" + srcRect + " dst=" + dstRect + " tx=" + tx + " ty=" + ty + " ti=" + ti);
                    canvas.drawBitmap(bitmap, srcRect, dstRect, null);
                    dstRect.left += 1;
                    dstRect.right += 1;
                    ti++;
                    tx = (tx + 1) % layer.width;
                }
            }
            dstRect.top += 1;
            dstRect.bottom += 1;
            ty = (ty + 1) % layer.height;
        }
    }
    public void getRectForTile(int tile, Rect rect) {
        int x = (tile - 1) % columns;
        int y = (tile - 1) / columns;
        rect.left = x * (tilewidth + spacing) + margin;
        rect.top = y * (tileheight + spacing) + margin;
        rect.right = rect.left + tilewidth;
        rect.bottom = rect.top + tileheight;
    }
}
