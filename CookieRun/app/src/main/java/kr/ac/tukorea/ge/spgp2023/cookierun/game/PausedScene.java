package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;

public class PausedScene extends BaseScene {
    private final Sprite title;
    private float angle;

    public enum Layer {
        bg, title, touch, COUNT
    }
    public PausedScene() {
        initLayers(Layer.COUNT);
        add(Layer.bg, new Sprite(R.mipmap.bg_city_landscape, 8.0f, 4.5f, 12, 6.75f));
        title = new Sprite(R.mipmap.cookie_run_title, 8.0f, 4.5f, 3.69f, 1.36f);
        add(Layer.bg, title);
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
    public void update(long elapsedNanos) {
        super.update(elapsedNanos);
        angle += BaseScene.frameTime * Math.PI / 4;
        float x = (float) (8.0f + 4.0f * Math.cos(angle));
        float y = (float) (4.5f + 2.0f * Math.sin(angle));
        title.moveTo(x, y);
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
