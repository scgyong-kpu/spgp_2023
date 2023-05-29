package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2023.framework.objects.TiledBackground;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.objects.FlyGen;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.objects.Selector;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();

    protected Selector selector;
    public Score score;

    public enum Layer {
        bg, enemy, shell, explosion, cannon, score, selection, controller, COUNT
    }

    public MainScene() {
        Metrics.setGameSize(32, 18);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLayers(Layer.COUNT);
        TiledBackground tiledBg = new TiledBackground("map", "desert.tmj");
        add(Layer.bg, tiledBg);
        score = new Score(R.mipmap.gold_number, 31.5f, 0.5f, 1);
        score.setScore(100);
        add(Layer.score, score);
        selector = new Selector(tiledBg);
        add(Layer.selection, selector);
        add(Layer.controller, new FlyGen());
    }

    @Override
    public boolean handleBackKey() {
        new BaseScene() {
            @Override
            public boolean isTransparent() { return true; }
        }.pushScene();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float gx = Metrics.toGameX(event.getX());
        float gy = Metrics.toGameY(event.getY());
        return selector.onTouch(action, gx, gy);
    }
}
