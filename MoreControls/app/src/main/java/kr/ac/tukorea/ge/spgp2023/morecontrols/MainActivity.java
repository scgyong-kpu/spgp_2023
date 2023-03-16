package kr.ac.tukorea.ge.spgp2023.morecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private CheckBox goodProgrammerCheckbox;
    private TextView outputTextView;
    private TextView reactionTextView;
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goodProgrammerCheckbox = findViewById(R.id.goodProgrammerCheckbox);
        outputTextView = findViewById(R.id.outputTextView);
        reactionTextView = findViewById(R.id.reactionTextView);
        nameEditText = findViewById(R.id.nameEditText);
    }

    public void onBtnDoIt(View view) {
        Log.d(TAG, "Do It");
        int fmtResId = goodProgrammerCheckbox.isChecked() ? R.string.pay_for_good_fmt : R.string.pay_for_not_good_fmt;
        String name = nameEditText.getText().toString();
        if (name.trim().length() == 0) {
            name = "NoName";
        }
        String text = getString(fmtResId, name);
        outputTextView.setText(text);
    }

    public void onCheckboxGoodProgrammer(View view) {
        String emoji = goodProgrammerCheckbox.isChecked() ? "🤩" : "😮‍💨";
        reactionTextView.setText(emoji);
    }
}