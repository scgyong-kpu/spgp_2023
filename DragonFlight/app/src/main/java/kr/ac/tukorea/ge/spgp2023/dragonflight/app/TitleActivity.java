package kr.ac.tukorea.ge.spgp2023.dragonflight.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.databinding.ActivityTitleBinding;

public class TitleActivity extends AppCompatActivity {

    private ActivityTitleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void onBtnStart(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    private float progress;
    public void onBtnProgress(View view) {
        progress += 0.05f;

        float tx = -1 * binding.treeImageView.getWidth() * progress;
        binding.treeImageView.setTranslationX(tx);
    }
}