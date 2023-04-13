package kr.ac.tukorea.ge.spgp2023.dragonflight.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class Sprite implements IGameObject {
    private static final String TAG = Sprite.class.getSimpleName();
    protected Bitmap bitmap;
    protected RectF dstRect = new RectF();
    protected float x, y, width, height;
    public Sprite(int bitmapResId, float cx, float cy, float width, float height) {
        this.x = cx;
        this.y = cy;
        this.width = width;
        this.height = height;
        if (bitmapResId != 0) {
            setBitmapResource(bitmapResId);
        }
        fixDstRect();

        Log.v(TAG, "Created " + this.getClass().getSimpleName() + "@" + System.identityHashCode(this));
    }

    protected void setBitmapResource(int bitmapResId) {
        bitmap = BitmapPool.get(bitmapResId);
    }

    protected void fixDstRect() {
        setSize(width, height);
    }

    protected void setSize(float width, float height) {
        float half_width = width / 2;
        float half_height = height / 2;
        dstRect.set(x - half_width, y - half_height, x + half_width, y + half_height);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

}
