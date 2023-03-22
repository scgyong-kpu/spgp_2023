package kr.ac.tukorea.ge.spgp2023.morecontrols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class BallView extends View {

    private Bitmap ballBitmap;

    public BallView(Context context) {
        super(context);
        init(null, 0);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BallView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);

    }

    private void init(AttributeSet attrs, int defStyle) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);

        Resources res = getResources();
        ballBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
    }

    private Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        canvas.drawRect(paddingLeft, paddingTop, paddingLeft + contentWidth, paddingTop + contentHeight, paint);
        canvas.drawText("Hello, Custom View", paddingLeft + 100, paddingTop + 100, paint);

        int cx = paddingLeft + contentWidth / 2;
        int cy = paddingTop + contentHeight / 2;
        canvas.drawBitmap(ballBitmap, cx, cy, null);
    }
}
