package net.scgyong.and.cards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnCard(View view) {
        // TAG 생성과정을 보여 줄 것. TAG 와 , 사이에서 Alt+Enter 를 눌러야 한다
        Log.d(TAG, "Card ID = " + view.getId());
    }
}