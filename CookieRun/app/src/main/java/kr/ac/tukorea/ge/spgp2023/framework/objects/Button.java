package kr.ac.tukorea.ge.spgp2023.framework.objects;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class Button extends Sprite implements ITouchable {
    private static final String TAG = Button.class.getSimpleName();

    public Button(int bitmapResId, float cx, float cy, float width, float height) {
        super(bitmapResId, cx, cy, width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = Metrics.toGameX(e.getX());
        float y = Metrics.toGameY(e.getY());
        if (!dstRect.contains(x, y)) {
            return false;
        }
        Log.d(TAG, "Button.onTouch(" + System.identityHashCode(this) + ", " + e.getAction() + ", " + e.getX() + ", " + e.getY());
        return true;
    }
}
