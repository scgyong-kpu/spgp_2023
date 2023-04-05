package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet implements IGameObject {
    protected float x, y, dx, dy, ex, ey;
    protected static float SPEED = 20.0f;
    protected static float LENGTH = 2.0f;
    protected static Paint paint;

    public Bullet(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        double radian = Math.toRadians(angle);
        double cos = Math.cos(radian);
        double sin = Math.sin(radian);

        this.dx = (float) (SPEED * cos);
        this.dy = (float) (SPEED * sin);
        this.ex = (float) (LENGTH * cos);
        this.ey = (float) (LENGTH * sin);

        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(0.1f);
        }
    }
    @Override
    public void update() {
        float frameTime = BaseScene.frameTime;
        x += dx * frameTime;
        y += dy * frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x + ex, y + ey, paint);
    }
}
