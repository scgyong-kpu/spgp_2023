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
    private static final int DIRECTION_FACTOR = 6;
    private Paint paint;
//    private ArrayList<PointF> points = new ArrayList<>();
    public static class PathPoint {
        float x, y;
        float dx, dy;
    }
    ArrayList<PathPoint> points = new ArrayList<>();
    private Path path;

    private boolean closed;
    private Listener listener;

    public interface Listener {
        public void onAdd();
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public int getCount() {
        return points.size();
    }
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
        if (ptCount == 0) { return; }

        PathPoint first = points.get(0);
        if (ptCount == 1) {
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
            return;
        }
        canvas.drawPath(path, paint);
    }
    private void buildPath() {
        int ptCount = points.size();
        if (ptCount < 2) { return; }

        PathPoint pt, prev, next;
        for (int i = ptCount - 2; i < ptCount; i++) {
            pt = points.get(i);
            prev = null;
            next = null;
            if (!closed) {
                if (i == 0) {
                    prev = pt;
                } else if (i == ptCount - 1) {
                    next = pt;
                }
            }
            if (prev == null) {
                prev = points.get((i - 1 + ptCount) % ptCount);
            }
            if (next == null) {
                next = points.get((i + 1) % ptCount);
            }
            pt.dx = ((next.x - prev.x) / DIRECTION_FACTOR);
            pt.dy = ((next.y - prev.y) / DIRECTION_FACTOR);
        }

        pt = points.get(0);
        next = points.get(1);
        prev = closed ? points.get(points.size() - 1) : pt;
        pt.dx = ((next.x - prev.x) / DIRECTION_FACTOR);
        pt.dy = ((next.y - prev.y) / DIRECTION_FACTOR);

        path = new Path();
        prev = points.get(0);
        path.moveTo(prev.x, prev.y);

        for (int i = 1; i < ptCount; i++) {
            pt = points.get(i);
            path.cubicTo(
                    prev.x + prev.dx, prev.y + prev.dy,
                    pt.x - pt.dx, pt.y - pt.dy,
                    pt.x, pt.y);
            prev = pt;
        }
        if (closed) {
            PathPoint first = points.get(0);
            PathPoint last = points.get(points.size() - 1);
            path.cubicTo(last.x + last.dx, last.y + last.dy,
                    first.x - first.dx, first.y - first.dy,
                    first.x, first.y);
            path.close();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getPointerCount() > 1) {
                points.clear();
                return false;
            }
            PathPoint pt = new PathPoint();
            pt.x = event.getX();
            pt.y = event.getY();
            points.add(pt);
            buildPath();
            Log.d(TAG, "Points:" + points.size());
            if (listener != null) {
                listener.onAdd();
            }
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    public void clear() {
        points.clear();
        path = null;
        invalidate();
    }

    public void setClosed(boolean checked) {
        closed = checked;
        buildPath();
        invalidate();
    }
}
