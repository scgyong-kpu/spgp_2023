package kr.ac.tukorea.ge.spgp2023.framework.objects;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.map.MapLoader;
import kr.ac.tukorea.ge.spgp2023.framework.map.TiledMap;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class TiledBackground implements IGameObject {
    private final TiledMap map;
    private float x, y, dx, dy;

    public TiledBackground(String folder, String tmjFile) {
        this.map = new MapLoader().loadAsset(folder, tmjFile);
    }

    public TiledBackground setFitWidth() {
        map.scale = Metrics.game_width / map.width;
        return this;
    }

    public TiledBackground setFitHeight() {
        map.scale = Metrics.game_height / map.height;
        return this;
    }

    public TiledBackground setScroll(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        return this;
    }

    public TiledBackground setWraps(boolean wraps) {
        map.wraps = wraps;
        return this;
    }

    @Override
    public void update() {
        this.x += dx * BaseScene.frameTime;
        this.y += dy * BaseScene.frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        map.draw(canvas, x, y);
    }
}
