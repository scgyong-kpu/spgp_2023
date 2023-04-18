package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.view.MotionEvent;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class MainScene extends BaseScene {
    private final Player player;

    public enum Layer {
        bg, platform, item, player, controller, COUNT
    }
    public MainScene() {
        Metrics.setGameSize(16.0f, 9.0f);
        initLayers(Layer.COUNT);

        add(Layer.bg, new VertScrollBackground(R.mipmap.cookie_run_bg_1, -0.2f));
        add(Layer.bg, new VertScrollBackground(R.mipmap.cookie_run_bg_2, -0.4f));
        add(Layer.bg, new VertScrollBackground(R.mipmap.cookie_run_bg_3, -0.6f));

        player = new Player();
        add(Layer.player, player);

        add(Layer.controller, new MapLoader());
        add(Layer.controller, new CollisionChecker(player));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            player.jump();
        }
        return super.onTouchEvent(event);
    }
}
