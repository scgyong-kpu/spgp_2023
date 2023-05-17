package kr.ac.tukorea.ge.spgp2023.smoothingpath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.ge.spgp2023.smoothingpath.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.pathView.setListener(new PathView.Listener() {
            @Override
            public void onAdd() {
                showCount();
            }
        });
        showCount();
    }
    private void showCount() {
        binding.countTextView.setText("Count: " + binding.pathView.getCount());
    }
    public void onBtnClear(View view) {
        binding.pathView.clear();
        showCount();
    }

    public void onCheckClosed(View view) {
        binding.pathView.setClosed(binding.closedCheckbox.isChecked());
    }
}