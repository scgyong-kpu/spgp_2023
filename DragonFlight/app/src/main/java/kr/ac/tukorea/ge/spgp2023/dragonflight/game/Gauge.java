package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.GameView;

public class Gauge {
    private float value = 1.0f;
    private Paint fgPaint = new Paint();
    private Paint bgPaint = new Paint();
    public Gauge() {
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(0.2f);
        bgPaint.setColor(Color.YELLOW);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(0.1f);
        fgPaint.setColor(Color.BLUE);
    }
    public void draw(Canvas canvas) {
        canvas.drawLine(0, 0, 1.0f, 0.0f, bgPaint);
        if (value > 0) {
            canvas.drawLine(0, 0, value, 0.0f, fgPaint);
        }
    }
}
