package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Fighter implements IGameObject {
    private static final float RADIUS = 1.25f;

    private Bitmap bitmap, targetBitmap;
    private RectF dstRect = new RectF();
    private RectF targetRect = new RectF();

    private float x, y; // 현재 위치
    private float tx, ty; // touch event 를 받은 위치. 이 위치를 향해서 움직인다
    private float dx, dy; // 1초간 움직여야 할 양: dx = SPEED*cos(r); dy = SPEED*sin(r);
    private static float SPEED = 10.0f;
    private float angle;
    public Fighter() {
        x = tx = 4.5f;
        y = ty = 12.0f;
        dx = dy = 0;
        dstRect.set(x-RADIUS, y-RADIUS, x+RADIUS, y+RADIUS);

        bitmap = BitmapFactory.decodeResource(GameView.res, R.mipmap.plane_240);
        targetBitmap = BitmapFactory.decodeResource(GameView.res, R.mipmap.target);
    }

    @Override
    public void update() {
        float time = BaseScene.frameTime;
        x += dx * time;
        if ((dx > 0 && x > tx) || (dx < 0 && x < tx)) {
            x = tx; dx = 0;
        }
        this.y += this.dy * time;
        if ((dy > 0 && y > ty) || (dy < 0 && y < ty)) {
            y = ty; dy = 0;
        }
        dstRect.set(x-RADIUS, y-RADIUS, x+RADIUS, y+RADIUS);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
        if (dx != 0 || dy != 0) {
            float r = 1.0f;
            targetRect.set(tx - r, ty - r, tx + r, ty + r);
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        float dx = tx - this.x;
        float dy = ty - this.y;
        double radian = Math.atan2(dy, dx);
        this.dx = (float) (SPEED * Math.cos(radian));
        this.dy = (float) (SPEED * Math.sin(radian));
        angle = (float) Math.toDegrees(radian) + 90;
//        dstRect.set(x-RADIUS, y-RADIUS, x+RADIUS, y+RADIUS);
    }
}
