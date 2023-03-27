package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private Bitmap soccerBitmap;
    private RectF soccer1Rect = new RectF();
    private RectF soccer2Rect = new RectF();
    private float ball1Dx, ball1Dy;
    private float ball2Dx, ball2Dy;
    private float scale;

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

        float cx = 5.0f, cy = 7.0f;
        float radius = 1.25f;
        soccer1Rect.set(cx - radius, cy - radius, cx + radius, cy + radius);
        ball1Dx = 0.04f;
        ball1Dy = 0.06f;

        soccer2Rect.set(cx - radius, cy - radius, cx + radius, cy + radius);
        ball2Dx = 0.075f;
        ball2Dy = 0.056f;

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long nanos) {
        update();
        invalidate();
        if (isShown()) {
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    private void update() {
        soccer1Rect.offset(ball1Dx, ball1Dy);
        if (ball1Dx > 0) {
            if (soccer1Rect.right > 10) {
                ball1Dx = -ball1Dx;
            }
        } else {
            if (soccer1Rect.left < 0) {
                ball1Dx = -ball1Dx;
            }
        }
        if (ball1Dy > 0) {
            if (soccer1Rect.bottom > 15.0f) {
                ball1Dy = -ball1Dy;
            }
        } else {
            if (soccer1Rect.top < 0) {
                ball1Dy = -ball1Dy;
            }
        }
        soccer2Rect.offset(ball2Dx, ball2Dy);
        if (ball2Dx > 0) {
            if (soccer2Rect.right > 10) {
                ball2Dx = -ball2Dx;
            }
        } else {
            if (soccer2Rect.left < 0) {
                ball2Dx = -ball2Dx;
            }
        }
        if (ball2Dy > 0) {
            if (soccer2Rect.bottom > 15.0f) {
                ball2Dy = -ball2Dy;
            }
        } else {
            if (soccer2Rect.top < 0) {
                ball2Dy = -ball2Dy;
            }
        }
//        Log.d(TAG, "soccerRect=" + soccerRect);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        scale = w / 10.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.scale(scale, scale);
        canvas.drawBitmap(soccerBitmap, null, soccer1Rect, null);
        canvas.drawBitmap(soccerBitmap, null, soccer2Rect, null);
    }
}