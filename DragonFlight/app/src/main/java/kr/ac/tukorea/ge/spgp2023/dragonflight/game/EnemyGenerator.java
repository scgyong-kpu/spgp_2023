package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.BaseScene;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.IGameObject;

public class EnemyGenerator implements IGameObject {
    private static final float GEN_INTERVAL = 5.0f;
    private float time;
    @Override
    public void update() {
        time += BaseScene.frameTime;
        if (time > GEN_INTERVAL) {
            generate();
            time -= GEN_INTERVAL;
        }
    }

    private void generate() {
        Random r = new Random();
        BaseScene scene = BaseScene.getTopScene();
        for (int i = 0; i < 5; i++) {
            scene.add(new Enemy(i, r.nextInt(10)));
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
