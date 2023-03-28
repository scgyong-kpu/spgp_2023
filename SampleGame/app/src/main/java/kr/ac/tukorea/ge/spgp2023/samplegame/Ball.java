package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class Ball extends Sprite {
    private float dx, dy;

    public Ball(float dx, float dy) {
        super(R.mipmap.soccer_ball_240, 2.0f, 2.0f, 2.5f, 2.5f);
        this.dx = dx;
        this.dy = dy;

        fixDstRect();
    }

    @Override
    public void update() {
        dstRect.offset(dx * BaseScene.frameTime, dy * BaseScene.frameTime);
        if (dx > 0) {
            if (dstRect.right > Metrics.game_width) {
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > Metrics.game_height) {
                dy = -dy;
            }
        } else {
            if (dstRect.top < 0) {
                dy = -dy;
            }
        }
    }
}
