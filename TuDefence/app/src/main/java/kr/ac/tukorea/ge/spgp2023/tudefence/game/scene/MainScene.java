package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import kr.ac.tukorea.ge.spgp2023.framework.objects.TiledBackground;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.objects.FlyGen;

public class MainScene extends BaseScene {
    public enum Layer {
        bg, enemy, controller, COUNT
    }

    public MainScene() {
        Metrics.setGameSize(32, 18);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLayers(Layer.COUNT);
        add(Layer.bg,
                new TiledBackground("map", "desert.tmj")
        );
        add(Layer.controller, new FlyGen());
    }
}
