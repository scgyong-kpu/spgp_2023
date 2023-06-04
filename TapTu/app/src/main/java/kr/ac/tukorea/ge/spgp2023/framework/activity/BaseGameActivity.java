package kr.ac.tukorea.ge.spgp2023.framework.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.GameView;

public class BaseGameActivity extends AppCompatActivity {

    private static final String TAG = BaseGameActivity.class.getSimpleName();
    protected GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        gameView = new GameView(this);
        gameView.setFullScreen();
        setContentView(gameView);
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
