package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;

public class FlyGen implements IGameObject {

    private static final float GEN_INTERVAL = 1.0f;
    private Random rand = new Random();
    private float time;

    @Override
    public void update() {
        time += BaseScene.frameTime;
        if (time >= GEN_INTERVAL) {
            spawn();
            time -= GEN_INTERVAL;
        }
    }

    private void spawn() {
        float size = rand.nextFloat() + 2;
        float speed = rand.nextFloat() * 0.5f + 1.0f;
        Fly.Type type = Fly.Type.random(rand);
        Fly fly = Fly.get(type, speed, size);
        MainScene scene = (MainScene) BaseScene.getTopScene();
        scene.add(MainScene.Layer.enemy, fly);
    }

    @Override
    public void draw(Canvas canvas) {
        Fly.drawPath(canvas);
    }
}
