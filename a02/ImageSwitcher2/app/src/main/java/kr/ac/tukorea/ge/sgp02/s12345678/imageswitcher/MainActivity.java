package kr.ac.tukorea.ge.sgp02.s12345678.imageswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnPrev(View view) {
        Log.d(TAG, "Prev pressed");
        setPage(page - 1);
    }

    public void onBtnNext(View view) {
        Log.d(TAG, "Next pressed");
        setPage(page + 1);
    }

    private static final int[] IMG_RES_IDS = new int[] {
        R.mipmap.cat_1,
        R.mipmap.cat_2,
        R.mipmap.cat_3,
        R.mipmap.cat_4,
        R.mipmap.cat_5,
    };

    private void setPage(int page) {
        if (page < 1 || page > 5) return;
        int resId = IMG_RES_IDS[page - 1];
        ImageView iv = findViewById(R.id.mainImageView);
        iv.setImageResource(resId);
        TextView tv = findViewById(R.id.pageTextView);
        tv.SetText(page + " / 5");
        this.page = page;
    }
}