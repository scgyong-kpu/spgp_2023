package net.scgyong.and.cards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] BUTTON_IDS = new int[] {
            R.id.card_00,R.id.card_01,R.id.card_02,R.id.card_03,
            R.id.card_10,R.id.card_11,R.id.card_12,R.id.card_13,
            R.id.card_20,R.id.card_21,R.id.card_22,R.id.card_23,
            R.id.card_30,R.id.card_31,R.id.card_32,R.id.card_33,
    };
    private static HashMap<Integer, Integer> idMap;
    static { // static 블럭은 이 클래스가 최초 로드되는 시점에 한 번 실행된다
        idMap = new HashMap<>();
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            idMap.put(BUTTON_IDS[i], i);
        }
    }
    private int[] resIds = new int[] {
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_qh,R.mipmap.card_kd,
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_qh,R.mipmap.card_kd,
    };
    private ImageButton previousButton;
    private TextView scoreTextView;
    private int flips = 0;

    private static int getIndexWithId(int id) {
        Integer index = idMap.get(id);
        if (index == null) {
            Log.e(TAG, "Cannot find the button with id: " + id);
            return -1;
        }
        return index;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.scoreTextView);

        for (int i = 0; i < BUTTON_IDS.length; i++) {
            ImageButton btn = findViewById(BUTTON_IDS[i]);
            btn.setTag(resIds[i]);
        }
    }

    public void onBtnCard(View view) {
        // TAG 생성과정을 보여 줄 것. TAG 와 , 사이에서 Alt+Enter 를 눌러야 한다
//        Log.d(TAG, "Card ID = " + view.getId());

        int cardIndex = getIndexWithId(view.getId());
        Log.i(TAG, "Card Index = " + cardIndex);

        ImageButton btn = (ImageButton) view;
        if (btn == previousButton) {
            // 같은 카드가 눌리면 무시한다
            return;
        }

        int resId = (Integer)btn.getTag(); // 이미지 리소스 아이디가 Tag 로 매달려 있다
        btn.setImageResource(resId);

        if (previousButton != null) {
            int prevResId = (Integer)previousButton.getTag(); // 이전 카드의 tag 를 살펴본다
            if (resId == prevResId) {
                btn.setVisibility(View.INVISIBLE);
                previousButton.setVisibility(View.INVISIBLE);
                previousButton = null;
                return;
            } else {
                // 이전의 카드는 뒷면이 보이도록 되돌려둔다
                previousButton.setImageResource(R.mipmap.card_blue_back);
            }
        }
        setFlips(flips + 1);
        previousButton = btn;
    }

    private void setFlips(int flips) {
        this.flips = flips;
        scoreTextView.setText("Flips: " + flips);
    }

    public void onBtnRestart(View view) {
        askRetry();
    }

    private void askRetry() {
        new AlertDialog.Builder(this).setTitle("Restart")
                .setMessage("Do you really want to restart the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "Restart Here");
                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }
}