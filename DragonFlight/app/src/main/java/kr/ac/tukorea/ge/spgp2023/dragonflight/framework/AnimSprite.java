package kr.ac.tukorea.ge.spgp2023.dragonflight.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimSprite extends Sprite {
    protected Rect srcRect = new Rect();
    protected int frameIndex, frameCount;
    protected int frameWidth, frameHeight;
    public AnimSprite(int bitmapResId, float cx, float cy, float width, float height) {
        super(bitmapResId, cx, cy, width, height);
        int imageWidth = bitmap.getWidth();
        frameHeight = bitmap.getHeight();
        frameWidth = frameHeight;
        frameCount = imageWidth / frameHeight;
        srcRect.set(0, 0, frameWidth, frameHeight);
    }

    @Override
    public void update() {
        super.update();
        frameIndex = (frameIndex + 1) % frameCount;
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
