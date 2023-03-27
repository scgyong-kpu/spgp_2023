package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View {
    private Bitmap soccerBitmap;
    private RectF soccerRect = new RectF();
    public GameView(Context context) {
        super(context);
        init(null, 0);
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scale = getWidth() / 10.0f;
        canvas.scale(scale, scale);

        float height = getHeight() / scale;

        float cx = 5.0f, cy = height / 2.0f;
        float radius = 1.25f;
        soccerRect.set(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawBitmap(soccerBitmap, null, soccerRect, null);
    }
}