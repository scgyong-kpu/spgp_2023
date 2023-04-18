package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class MapLoader implements IGameObject {
    private static final String TAG = MapLoader.class.getSimpleName();
    private Random random = new Random();
    private float platformX, itemX;
    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        platformX -= MapObject.SPEED * BaseScene.frameTime;
        while (platformX < Metrics.game_width) {
            Platform platform = Platform.get(Platform.Type.random(random), platformX, 7);
            scene.add(MainScene.Layer.platform, platform);
            platformX += platform.getWidth();
        }
        itemX -= MapObject.SPEED * BaseScene.frameTime;
        while (itemX < Metrics.game_width) {
            JellyItem jellyItem = JellyItem.get(JellyItem.getRandomIndex(random), itemX, random.nextInt(7));
            scene.add(MainScene.Layer.item, jellyItem);
            itemX += jellyItem.getWidth();
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
