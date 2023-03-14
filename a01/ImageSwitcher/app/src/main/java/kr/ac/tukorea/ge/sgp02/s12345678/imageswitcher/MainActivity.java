package kr.ac.tukorea.ge.sgp02.s12345678.imageswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPage(1);
    }

    public void onBtnPrev(View view) {
        Log.d(TAG, "Prev button clicked!!");
        setPage(page - 1);
    }

    public void onBtnNext(View view) {
        Log.d(TAG, "Next button clicked!!");
        setPage(page + 1);
    }

    private void setPage(int page) {
        if (page < 1 || page > 5) return;

        int[] resIds = new int[] {
                R.mipmap.cat_1,
                R.mipmap.cat_2,
                R.mipmap.cat_3,
                R.mipmap.cat_4,
                R.mipmap.cat_5,
        };
        int resId = resIds[page - 1];
        ImageView mainImageView = findViewById(R.id.mainImageView);
        mainImageView.setImageResource(resId);
        TextView pageTextView = findViewById(R.id.pageTextView);
        pageTextView.setText(page + " / 5");

        this.page = page;
    }
}