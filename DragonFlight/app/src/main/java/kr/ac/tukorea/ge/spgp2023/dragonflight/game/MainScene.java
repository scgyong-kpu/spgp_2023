package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.AnimSprite;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.BaseScene;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.IGameObject;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Fighter fighter;

    enum Layer {
        enemy, bullet, player, controller, COUNT
    }
    public MainScene() {
        initLayers(Layer.COUNT.ordinal());
        fighter = new Fighter();
        add(Layer.player.ordinal(), fighter);

//        AnimSprite animSprite = new AnimSprite(R.mipmap.enemy_01, 4.5f, 5.0f, 1.8f, 1.8f, 10, 0);
//        add(animSprite);
        add(Layer.controller.ordinal(), new EnemyGenerator());
    }

    @Override
    public void update(long elapsedNanos) {
        super.update(elapsedNanos);
        checkCollision();
    }

    private void checkCollision() {
        for (IGameObject o1 : layers.get(Layer.enemy.ordinal())) {
            if (!(o1 instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) o1;
//            boolean removed = false;
            for (IGameObject o2 : layers.get(Layer.bullet.ordinal())) {
                if (!(o2 instanceof Bullet)) {
                    continue;
                }
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    Log.d(TAG, "Collision !!");
                    remove(bullet); // is this recyclable?
                    remove(enemy); // is this recyclable?
//                    removed = true;
                    break;
                }
            }
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
