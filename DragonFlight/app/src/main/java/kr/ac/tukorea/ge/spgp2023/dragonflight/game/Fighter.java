package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.BaseScene;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.Sprite;

public class Fighter extends Sprite {
    private static final float FIGHTER_X = 4.5f;
    private static final float FIGHTER_Y = 14.8f;
    private static final float FIGHTER_SIZE = 1.75f;
    private static final float TARGET_RADIUS = 0.5f;
    private static final float SPEED = 10.0f;
    private static final float FIGHTER_LEFT = FIGHTER_SIZE / 2;
    private static final float FIGHTER_RIGHT = 9.0f - FIGHTER_SIZE / 2;

    private float tx;
    private Bitmap targetBitmap;
    private RectF targetRect = new RectF();

    public Fighter() {
        super(R.mipmap.fighter, FIGHTER_X, FIGHTER_Y, FIGHTER_SIZE, FIGHTER_SIZE);
        targetBitmap = BitmapPool.get(R.mipmap.target);
        tx = x;
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        targetRect.set(
                tx - TARGET_RADIUS, FIGHTER_Y - TARGET_RADIUS,
                tx + TARGET_RADIUS, FIGHTER_Y + TARGET_RADIUS);
    }

    @Override
    public void update() {
        super.update();

        float time = BaseScene.frameTime;
        if (tx >= x) {
            x += SPEED * time;
            if (x > tx) {
                x = tx;
            }
        } else {
            x -= SPEED * time;
            if (x < tx) {
                x = tx;
            }
        }
        fixDstRect();
        if (x < FIGHTER_LEFT) x = tx = FIGHTER_LEFT;
        if (x > FIGHTER_RIGHT) x = tx = FIGHTER_RIGHT;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (tx != x) {
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }
}
