package kr.ac.tukorea.ge.spgp2023.framework.objects;

import android.telecom.Call;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class Button extends Sprite implements ITouchable {
    private static final String TAG = Button.class.getSimpleName();
    private final Callback callback;

    public interface Callback {
        public boolean onTouch();
    }
    public Button(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {
        super(bitmapResId, cx, cy, width, height);
        this.callback = callback;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = Metrics.toGameX(e.getX());
        float y = Metrics.toGameY(e.getY());
        if (!dstRect.contains(x, y)) {
            return false;
        }
        //Log.d(TAG, "Button.onTouch(" + System.identityHashCode(this) + ", " + e.getAction() + ", " + e.getX() + ", " + e.getY());
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            callback.onTouch();
        }
        return true;
    }
}
