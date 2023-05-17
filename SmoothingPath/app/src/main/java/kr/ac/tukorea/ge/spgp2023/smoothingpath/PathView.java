package kr.ac.tukorea.ge.spgp2023.smoothingpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class PathView extends View {

    private static final String TAG = PathView.class.getSimpleName();
    private Paint paint;
    ArrayList<PointF> points = new ArrayList<>();

    public PathView(Context context) {
        super(context);
        init(null, 0);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int ptCount = points.size();
        if (ptCount < 2) {
            return;
        }
        Path path = new Path();
        path.moveTo(points.get(0).x, points.get(0).y);
        for (int i = 1; i < ptCount; i++) {
            PointF pt = points.get(i);
            path.lineTo(pt.x, pt.y);
        }
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getPointerCount() > 1) {
                points.clear();
                return false;
            }
            PointF pt = new PointF();
            pt.x = event.getX();
            pt.y = event.getY();
            points.add(pt);
            Log.d(TAG, "Points:" + points.size());

            invalidate();
        }
        return super.onTouchEvent(event);
    }

    public void clear() {
        points.clear();
        invalidate();
    }
}
