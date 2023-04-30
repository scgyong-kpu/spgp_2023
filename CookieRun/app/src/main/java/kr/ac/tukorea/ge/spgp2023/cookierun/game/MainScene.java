package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public enum Layer {
        bg, platform, item, player, ui, touch, controller, COUNT
    }
    public MainScene() {
        Metrics.setGameSize(16.0f, 9.0f);
        initLayers(Layer.COUNT);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_1, -0.2f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_2, -0.4f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_3, -0.6f));

        player = new Player();
        add(Layer.player, player);

        add(Layer.touch, new Button(R.mipmap.btn_slide_n, 1.5f, 8.0f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch() {
                Log.d(TAG, "Button: Slide");
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_jump_n, 14.5f, 7.7f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch() {
                player.jump();
                //Log.d(TAG, "Button: Jump");
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_fall_n, 14.5f, 8.5f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch() {
                //Log.d(TAG, "Button: Fall");
                player.fall();
                return true;
            }
        }));
        add(Layer.controller, new MapLoader());
        add(Layer.controller, new CollisionChecker(player));
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            player.jump();
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
