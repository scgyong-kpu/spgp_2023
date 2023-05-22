package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import kr.ac.tukorea.ge.spgp2023.framework.map.MapLoader;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;

public class MainScene extends BaseScene {
    @Override
    protected void onStart() {
        super.onStart();
        new MapLoader().loadAsset("map", "earth.tmj");
    }
}
