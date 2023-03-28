package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

import java.util.Random;

public class MainScene extends BaseScene {
    private Fighter fighter;
    public MainScene() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            float dx = r.nextFloat() * 0.05f + 0.03f;
            float dy = r.nextFloat() * 0.05f + 0.03f;
            add(new Ball(dx, dy));
        }

        fighter = new Fighter();
        add(fighter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float scale = GameView.scale;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = (float) event.getX() / scale;
                float y = (float) event.getY() / scale;
                fighter.setPosition(x, y);
                return true;
        }
        return super.onTouchEvent(event);
    }
}
