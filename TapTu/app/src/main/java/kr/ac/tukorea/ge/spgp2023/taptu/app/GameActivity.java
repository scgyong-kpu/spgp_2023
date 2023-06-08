package kr.ac.tukorea.ge.spgp2023.taptu.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import kr.ac.tukorea.ge.spgp2023.framework.activity.BaseGameActivity;
import kr.ac.tukorea.ge.spgp2023.taptu.game.PlayScene;

public class GameActivity extends BaseGameActivity {

    public static final String SONG_INDEX = "songIndex";
    private static final String TAG = GameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int index = extras.getInt(SONG_INDEX);
        Log.d(TAG, "Selected Song Index = " + index);
        new PlayScene(index).pushScene();
    }
}