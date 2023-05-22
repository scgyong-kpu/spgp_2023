package kr.ac.tukorea.ge.spgp2023.framework.objects;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.map.MapLoader;
import kr.ac.tukorea.ge.spgp2023.framework.map.TiledMap;

public class TiledBackground implements IGameObject {
    private final TiledMap map;

    public TiledBackground(String folder, String tmjFile) {
        this.map = new MapLoader().loadAsset(folder, tmjFile);
    }
    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
