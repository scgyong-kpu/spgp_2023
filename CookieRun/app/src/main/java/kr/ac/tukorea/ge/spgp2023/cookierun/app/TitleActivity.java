package kr.ac.tukorea.ge.spgp2023.cookierun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.cookierun.databinding.ActivityTitleBinding;
import kr.ac.tukorea.ge.spgp2023.cookierun.game.Player;

public class TitleActivity extends AppCompatActivity {

    private static final int MAX_STAGE = 3;
    private static final String TAG = TitleActivity.class.getSimpleName();
    private int stage;
    private ActivityTitleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStage(1);
        setCookieIndex(0);
    }

    private void setStage(int stage) {
        this.stage = stage;
        String text = getString(R.string.title_stage_number_fmt, stage);
        binding.stageTextView.setText(text);
        binding.prevButton.setEnabled(stage > 1);
        binding.nextButton.setEnabled(stage < MAX_STAGE);
    }

    public void onBtnPrevStage(View view) {
        setStage(stage - 1);
    }
    public void onBtnNextStage(View view) {
        setStage(stage + 1);
    }
    public void onBtnStartGame(View view) {
        Log.d(TAG, "Starting game stage: " + stage);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.PARAM_STAGE_INDEX, stage);
        intent.putExtra(MainActivity.PARAM_COOKIE_ID, cookieIds[cookieIndex]);
        startActivity(intent);
    }

    private static final int[] cookieIds = {
            107566, 107567, 107568, 107571, 107583,
    };
    private int cookieIndex = 0;
    public void onBtnPrevCookie(View view) {
        setCookieIndex((cookieIndex + cookieIds.length - 1) % cookieIds.length);
    }
    public void onBtnNextCookie(View view) {
        setCookieIndex((cookieIndex + 1) % cookieIds.length);
    }

    private void setCookieIndex(int index) {
        cookieIndex = index;
        int cookieId = cookieIds[index];

        try {
            Bitmap bmp = BitmapFactory.decodeStream(getAssets().open("cookies/" + cookieId + "_icon.png"));
            binding.cookieImageView.setImageBitmap(bmp);
            binding.cookieNameTextView.setText(Player.cookieInfoMap.get(cookieId).name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
