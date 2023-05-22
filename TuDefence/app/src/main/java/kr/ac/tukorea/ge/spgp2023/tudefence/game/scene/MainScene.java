package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import kr.ac.tukorea.ge.spgp2023.framework.map.MapLoader;
import kr.ac.tukorea.ge.spgp2023.framework.objects.TiledBackground;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;

public class MainScene extends BaseScene {
    private enum Layer {
        bg, COUNT
    }
    @Override
    protected void onStart() {
        super.onStart();
        initLayers(Layer.COUNT);
        add(Layer.bg, new TiledBackground("map", "earth.tmj").setFitWidth());
    }
}
