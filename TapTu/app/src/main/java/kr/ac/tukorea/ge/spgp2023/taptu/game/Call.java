package kr.ac.tukorea.ge.spgp2023.taptu.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.taptu.R;

public class Call extends Sprite {
    public enum Type {
        perfect, great, good, bad, miss, none
    }
    public Call() {
        super(R.mipmap.calls,
                Metrics.game_width/2, Metrics.game_height/3,
                Metrics.game_width/3, Metrics.game_height/15);
        originalY = dstRect.top;
        srcHeight = bitmap.getHeight() / 5;
        srcRect.set(0, 0, bitmap.getWidth(), srcHeight);
        set(Type.none);
    }
    public void set(Type type) {
        int index = type.ordinal();
        if (type == Type.none) {
            index = -1;
        } else {
            setOn = System.currentTimeMillis();
        }
        srcRect.offsetTo(0, index * srcHeight);
    }

    private Rect srcRect = new Rect();
    private int srcHeight;
    private long setOn;
    private float originalY;

    @Override
    public void draw(Canvas canvas) {
        int elapsed = (int) (System.currentTimeMillis() - setOn);
        if (elapsed < 1000) {
            if (elapsed > 500) elapsed = 500;
            float y = originalY - elapsed * dstRect.height() / 1000.0f;
            dstRect.offsetTo(dstRect.left, y);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        }
    }
}
