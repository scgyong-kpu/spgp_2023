package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
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
        float gx = Metrics.toGameX(event.getX());
        float gy = Metrics.toGameY(event.getY());
        Cannon cannon = findCannonAt(gx, gy);
        if (cannon != null) {
            cannon.upgrade();
            return false;
        }
        if (intersectsIfInstalledAt(gx, gy)) {
            return false;
        }
        int x = Math.round(gx);
        int y = Math.round(gy);
        boolean canInstall = tiledBg.canInstallAt(x, y);
        if (!canInstall) return false;
        Log.d(TAG, "Touch Event: " + x + "," + y + " Install=" + canInstall);
        cannon = new Cannon(1, x, y);
        add(Layer.cannon, cannon);
        return true;
    }

    private RectF instRect = new RectF();
    private Cannon findCannonAt(float x, float y) {
        for (IGameObject obj: getObjectsAt(Layer.cannon)) {
            Cannon cannon = (Cannon) obj;
            float cx = cannon.getX(), cy = cannon.getY();
            instRect.set(cx - 1, cy - 1, cx + 1, cy + 1);
            if (instRect.contains(x, y)) {
                return cannon;
            }
        }
        return null;
    }
    private boolean intersectsIfInstalledAt(float x, float y) {
        instRect.set(x - 1, y - 1, x + 1, y + 1);
        for (IGameObject obj: getObjectsAt(Layer.cannon)) {
            Cannon cannon = (Cannon) obj;
            float cx = cannon.getX(), cy = cannon.getY();
            if (instRect.intersects(cx - 1, cy - 1, cx + 1, cy + 1)) {
                return true;
            }
        }
        return false;
    }
}
