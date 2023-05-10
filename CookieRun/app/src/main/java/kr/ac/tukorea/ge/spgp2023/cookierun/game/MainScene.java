package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.content.Context;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2023.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public enum Layer {
        bg, platform, item, obstacle, player, ui, touch, controller, COUNT
    }
    public MainScene(Context context, int stage) {
        Metrics.setGameSize(16.0f, 9.0f);
        initLayers(Layer.COUNT);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_1, -0.2f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_2, -0.4f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_3, -0.6f));

        player = new Player();
        add(Layer.player, player);

        add(Layer.touch, new Button(R.mipmap.btn_slide_n, 1.5f, 8.0f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "Button: Slide");
                player.slide(action == Button.Action.pressed);
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_jump_n, 14.5f, 7.7f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    player.jump();
                }
                //Log.d(TAG, "Button: Jump");
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_fall_n, 14.5f, 8.5f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    player.fall();
                }
                //Log.d(TAG, "Button: Fall");
                return true;
            }
        }));
        add(Layer.controller, new MapLoader(context, stage));
        add(Layer.controller, new CollisionChecker(player));
    }

    @Override
    protected void onStart() {
        Sound.playMusic(R.raw.main);
    }

    @Override
    protected void onEnd() {
        Sound.stopMusic();
    }

    @Override
    protected void onPause() {
        Sound.pauseMusic();
    }

    @Override
    protected void onResume() {
        Sound.resumeMusic();
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
