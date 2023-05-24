package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.framework.objects.TiledBackground;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.objects.Cannon;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.objects.FlyGen;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();

    protected TiledBackground tiledBg;

    public enum Layer {
        bg, enemy, shell, cannon, controller, COUNT
    }

    public MainScene() {
        Metrics.setGameSize(32, 18);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLayers(Layer.COUNT);
        tiledBg = new TiledBackground("map", "desert.tmj");
        add(Layer.bg, tiledBg);
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
        if (event.getAction() != MotionEvent.ACTION_DOWN) return false;
        float x = (float) (Math.round(Metrics.toGameX(event.getX()) / 0.5) * 0.5);
        float y = (float) (Math.round(Metrics.toGameY(event.getY()) / 0.5) * 0.5);
        boolean canInstall = tiledBg.canInstallAt((int)x, (int)y);
        Log.d(TAG, "Touch Event: " + x + "," + y + " Install=" + canInstall);
        return true;
    }
}
