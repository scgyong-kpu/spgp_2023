package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.BaseScene;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.Metrics;

public class MainScene extends BaseScene {
    private final Fighter fighter;

    public MainScene() {
        fighter = new Fighter();
        add(fighter);

        for (int i = 0; i < 5; i++) {
            add(new Enemy(i, i));
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = Metrics.toGameX(event.getX());
                float y = Metrics.toGameY(event.getY());
                fighter.setTargetPosition(x, y);
                return true;
        }
        return super.onTouchEvent(event);
    }
}
