package kr.ac.tukorea.ge.spgp2023.cookierun.app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp2023.cookierun.game.MainScene;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.GameView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String PARAM_STAGE_INDEX = "stageIndex";
    public static final String PARAM_COOKIE_ID = "cookieId";
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        gameView = new GameView(this);
        gameView.setFullScreen();
        setContentView(gameView);

        new MainScene(this, extras).pushScene();
    }


    @Override
    protected void onPause() {
        gameView.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        BaseScene.popAll();
        GameView.clear();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (gameView.handleBackKey()) {
            return;
        }
        super.onBackPressed();
    }
}
