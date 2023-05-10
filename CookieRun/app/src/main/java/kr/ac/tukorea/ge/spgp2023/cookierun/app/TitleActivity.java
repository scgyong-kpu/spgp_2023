package kr.ac.tukorea.ge.spgp2023.cookierun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.cookierun.databinding.ActivityTitleBinding;

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
        startActivity(intent);
    }
}