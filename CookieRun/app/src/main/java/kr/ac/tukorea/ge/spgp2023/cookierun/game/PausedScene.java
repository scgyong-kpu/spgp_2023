package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;

public class PausedScene extends BaseScene {
    public enum Layer {
        bg, title, touch, COUNT
    }
    public PausedScene() {
        initLayers(Layer.COUNT);
        add(Layer.bg, new Sprite(R.mipmap.bg_city_landscape, 8.0f, 4.5f, 16, 9));
        add(Layer.bg, new Sprite(R.mipmap.cookie_run_title, 8.0f, 4.5f, 3.69f, 1.36f));
        add(Layer.touch, new Button(R.mipmap.btn_resume_n, 14.5f, 1.0f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    popScene();
                }
                return false;
            }
        }));
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
