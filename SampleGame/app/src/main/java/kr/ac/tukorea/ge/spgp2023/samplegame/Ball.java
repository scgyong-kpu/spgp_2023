package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Ball {
    private static Bitmap bitmap;
    private RectF dstRect = new RectF();
    private float dx, dy;

    public Ball(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        dstRect.set(0, 0, 2.5f, 2.5f);
    }

    public static void setBitmap(Bitmap bitmap) {
        Ball.bitmap = bitmap;
    }

    public void update() {
        dstRect.offset(dx, dy);
        if (dx > 0) {
            if (dstRect.right > 10.0f) {
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > 15.0) {
                dy = -dy;
            }
        } else {
            if (dstRect.top < 0) {
                dy = -dy;
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }}
