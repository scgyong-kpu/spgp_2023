package kr.ac.tukorea.ge.spgp2023.cookierun.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp2023.cookierun.game.MainScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.GameView;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        gameView.setFullScreen();
        setContentView(gameView);

        new MainScene().pushScene();
    }
}