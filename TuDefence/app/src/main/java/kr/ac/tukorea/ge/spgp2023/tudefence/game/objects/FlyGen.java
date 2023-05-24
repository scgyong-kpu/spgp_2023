package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.scene.MainScene;

public class FlyGen implements IGameObject {

    private static final float GEN_INTERVAL = 2.0f;
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
        float size = rand.nextFloat() + 1.5f;
        float speed = rand.nextFloat() * 0.5f + 1.0f;
        Fly fly = Fly.get(Fly.Type.RANDOM, speed, size);
        MainScene scene = (MainScene) BaseScene.getTopScene();
        scene.add(MainScene.Layer.enemy, fly);
    }

    @Override
    public void draw(Canvas canvas) {
//        Fly.drawPath(canvas);
    }
}
