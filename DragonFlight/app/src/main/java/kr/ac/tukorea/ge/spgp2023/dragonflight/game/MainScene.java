package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.AnimSprite;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.BaseScene;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.IGameObject;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Fighter fighter;

    public enum Layer {
        enemy, bullet, player, ui, controller, COUNT
    }

    private Score score;
    public MainScene() {
        initLayers(Layer.COUNT);
        fighter = new Fighter();
        add(Layer.player, fighter);
        score = new Score();
        add(Layer.ui, score);
        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());
    }

    public void addScore(int amount) {
        score.add(amount);
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
