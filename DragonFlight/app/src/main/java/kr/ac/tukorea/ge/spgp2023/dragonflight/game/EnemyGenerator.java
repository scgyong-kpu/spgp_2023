package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.BaseScene;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.IGameObject;

public class EnemyGenerator implements IGameObject {
    private static final float GEN_INTERVAL = 5.0f;
    private static final String TAG = Enemy.class.getSimpleName();
    private float time;
    private int wave;

    @Override
    public void update() {
        time += BaseScene.frameTime;
        if (time > GEN_INTERVAL) {
            generate();
            time -= GEN_INTERVAL;
        }
    }

    private void generate() {
        wave++;
        Log.v(TAG, "Generating: wave " + wave);
        Random r = new Random();
        BaseScene scene = BaseScene.getTopScene();
        for (int i = 0; i < 5; i++) {
            int level = (wave + 15) / 10 - r.nextInt(3);
            if (level < 0) level = 0;
            if (level > Enemy.MAX_LEVEL) level = Enemy.MAX_LEVEL;
            scene.add(new Enemy(i, level));
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
