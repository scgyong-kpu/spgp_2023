package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.GameView;

public class Gauge {
    private Paint fgPaint = new Paint();
    private Paint bgPaint = new Paint();
    public Gauge(float width) {
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(width);
        bgPaint.setColor(ResourcesCompat.getColor(GameView.res, R.color.enemy_gauge_bg, null));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(width / 2);
        fgPaint.setColor(ResourcesCompat.getColor(GameView.res, R.color.enemy_gauge_fg, null));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
    }
    public void draw(Canvas canvas, float value) {
        canvas.drawLine(0, 0, 1.0f, 0.0f, bgPaint);
        if (value > 0) {
            canvas.drawLine(0, 0, value, 0.0f, fgPaint);
        }
    }
}
