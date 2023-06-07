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
        srcHeight = bitmap.getHeight() / 5;
        srcRect.set(0, 0, bitmap.getWidth(), srcHeight);
        set(Type.none);
    }
    public void set(Type type) {
        int index = type.ordinal();
        if (type == Type.none) {
            index = -1;
        }
        srcRect.offsetTo(0, index * srcHeight);
        //Log.d("Call", "type=" + type + " index=" + index + " rect=" + srcRect);
        //setOn = System.currentTimeMillis();
    }

    private Rect srcRect = new Rect();
    private int srcHeight;

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
