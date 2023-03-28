package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Ball implements IGameObject {
    private static Bitmap bitmap;
    private RectF dstRect = new RectF();
    private float dx, dy;

    public Ball(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        dstRect.set(0, 0, 2.5f, 2.5f);

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(GameView.res, R.mipmap.soccer_ball_240);
        }
    }

    @Override
    public void update() {

        dstRect.offset(dx * BaseScene.frameTime, dy * BaseScene.frameTime);
        if (dx > 0) {
            if (dstRect.right > 9.0f) {
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > 16.0) {
                dy = -dy;
            }
        } else {
            if (dstRect.top < 0) {
                dy = -dy;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
