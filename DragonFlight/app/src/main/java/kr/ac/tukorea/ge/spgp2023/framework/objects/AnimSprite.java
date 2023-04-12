package kr.ac.tukorea.ge.spgp2023.framework.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimSprite extends Sprite {
    private static final String TAG = AnimSprite.class.getSimpleName();
    protected Rect srcRect = new Rect();
    protected int frameCount;
    protected float fps;
    protected long createdOn;
    protected int frameWidth, frameHeight;
    public AnimSprite(int bitmapResId, float cx, float cy, float width, float height, float fps, int frameCount) {
        super(bitmapResId, cx, cy, width, height);
        this.fps = fps;
        int imageWidth = bitmap.getWidth();
        frameHeight = bitmap.getHeight();
        if (frameCount == 0) {
            frameWidth = frameHeight;
            this.frameCount = imageWidth / frameHeight;
        } else {
            frameWidth = imageWidth / frameCount;
            this.frameCount = frameCount;
        }
        srcRect.set(0, 0, frameWidth, frameHeight);
        createdOn = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % frameCount;
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
