package kr.ac.tukorea.ge.spgp2023.dragonflight.app;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.databinding.ActivityTitleBinding;

public class TitleActivity extends AppCompatActivity {

    private static final String TAG = TitleActivity.class.getSimpleName();
    private ActivityTitleBinding binding;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createAnimator();
    }
    private void createAnimator() {
        animator = ValueAnimator.ofFloat(0.0f, 0.5f);
        animator.setDuration(5000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (Float)valueAnimator.getAnimatedValue();
                Log.d(TAG, "Progress: " + progress);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
    }

    @Override
    protected void onPause() {
        stopAnimation();
        super.onPause();
    }

    protected void startAnimation() {
        animator.start();
    }

    protected void stopAnimation() {
        animator.end();
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