package kr.ac.tukorea.ge.spgp2023.taptu.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.taptu.R;

public class Pret extends Sprite {
    public interface Listener {
        public void onPret(int lane, boolean pressed);
    }
    private Listener listener;
    private boolean captures, shows;
    private int lane;
    public Pret(int index, Listener listener) {
        this.lane = index;
        this.listener = listener;
        bitmap = BitmapPool.get(R.mipmap.trans_50p);
        float width = NoteSprite.NOTE_WIDTH * Metrics.game_width;
        float center = Metrics.game_width / 2;
        float x = center + (index - 2.5f) * width;
        //Log.d("Lane", "index="+index+" x=" + x);
        dstRect.set(x, 0, x + width, Metrics.game_height);
    }
    public boolean onTouchEvent(int action, float x, float y) {
        boolean in = dstRect.contains(x, y);
        if (action == MotionEvent.ACTION_DOWN) {
            if (!in) return false;
            captures = true;
            shows = true;
            listener.onPret(lane, true);
        } else if (action == MotionEvent.ACTION_UP) {
            shows = false;
            if (!captures) return false;
            captures = false;
            listener.onPret(lane, false);
            return true;
        } else {
            if (!captures) return false;
            shows = in;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if (shows) {
            super.draw(canvas);
        }
    }
}
