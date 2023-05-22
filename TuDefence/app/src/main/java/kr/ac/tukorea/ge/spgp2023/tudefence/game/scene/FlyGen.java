package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class FlyGen implements IGameObject {
    public FlyGen() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            Fly fly = Fly.get(Fly.Type.blue, 0, 3);
            fly.moveTo(
                    rand.nextFloat() * Metrics.game_width,
                    rand.nextFloat() * Metrics.game_height);
            scene.add(MainScene.Layer.enemy, fly);
        }
    }
    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
