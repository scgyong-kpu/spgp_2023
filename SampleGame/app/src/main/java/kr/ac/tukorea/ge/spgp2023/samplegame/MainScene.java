package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.view.MotionEvent;

import java.util.Random;

public class MainScene extends BaseScene {
    private Fighter fighter;
    public MainScene() {
//        Metrics.setGameSize(10.0f, 10.0f);
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            float dx = r.nextFloat() * 5.0f + 3.0f;
            float dy = r.nextFloat() * 5.0f + 3.0f;
            add(new Ball(dx, dy));
        }

        fighter = new Fighter();
        add(fighter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = Metrics.toGameX(event.getX());
                float y = Metrics.toGameY(event.getY());
                fighter.setTargetPosition(x, y);
                if (action == MotionEvent.ACTION_DOWN) {
                    fighter.fire();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }
}
