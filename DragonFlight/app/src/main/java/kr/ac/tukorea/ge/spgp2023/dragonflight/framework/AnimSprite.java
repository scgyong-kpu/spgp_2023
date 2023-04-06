package kr.ac.tukorea.ge.spgp2023.dragonflight.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimSprite extends Sprite {
    protected Rect srcRect = new Rect();
    public AnimSprite(int bitmapResId, float cx, float cy, float width, float height) {
        super(bitmapResId, cx, cy, width, height);
//        int w = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        srcRect.set(0, 0, srcHeight, srcHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
