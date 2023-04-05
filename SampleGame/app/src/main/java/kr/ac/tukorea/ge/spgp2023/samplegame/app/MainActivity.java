package kr.ac.tukorea.ge.spgp2023.samplegame.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp2023.samplegame.R;
import kr.ac.tukorea.ge.spgp2023.samplegame.game.MainScene;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MainScene().pushScene();
    }
}