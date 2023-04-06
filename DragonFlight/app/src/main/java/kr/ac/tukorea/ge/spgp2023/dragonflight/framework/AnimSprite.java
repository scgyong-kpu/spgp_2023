package kr.ac.tukorea.ge.spgp2023.dragonflight.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimSprite extends Sprite {
    protected Rect srcRect = new Rect();
    protected int frameCount;
    protected float time, fps;
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
    }

    @Override
    public void update() {
        super.update();
        time += BaseScene.frameTime;
        int frameIndex = Math.round(time * fps) % frameCount;
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
