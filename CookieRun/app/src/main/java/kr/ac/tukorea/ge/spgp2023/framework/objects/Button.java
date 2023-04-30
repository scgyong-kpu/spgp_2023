package kr.ac.tukorea.ge.spgp2023.framework.objects;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.ITouchable;

public class Button extends Sprite implements ITouchable {
    private static final String TAG = Button.class.getSimpleName();

    public Button(int bitmapResId, float cx, float cy, float width, float height) {
        super(bitmapResId, cx, cy, width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        if (!dstRect.contains(x, y)) {
            return false;
        }
        Log.d(TAG, "Button.onTouch(" + System.identityHashCode(this) + ", " + e.getAction() + ", " + e.getX() + ", " + e.getY());
        return false;
    }
}
