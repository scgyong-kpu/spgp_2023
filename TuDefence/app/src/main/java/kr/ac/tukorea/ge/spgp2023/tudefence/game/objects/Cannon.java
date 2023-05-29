package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.scene.MainScene;

public class Cannon extends Sprite {
    int level;
    float power, interval, range;
    float angle = -90;
    private float time;
    private Bitmap barrelBitmap;
    private RectF barrelRect = new RectF();
    public Cannon(int level, int x, int y) {
        super(0, x, y, 2, 2);
        barrelBitmap = BitmapPool.get(R.mipmap.tank_barrel);
        setLevel(level);
    }

    private static int[] COSTS = {
            10, 30, 70, 150, 300, 700, 1500, 3000, 7000, 15000, 100000000
    };
    public static int getInstallCost(int level) {
        return COSTS[level - 1];
    }
    public static int getUpgradeCost(int level) {
        return Math.round((COSTS[level] - COSTS[level - 1]) * 1.1f);
    }
    public int getUpgradeCost() {
        return getUpgradeCost(level);
    }
    public static int getSellPrice(int level) {
        return COSTS[level - 1] / 2;
    }
    public int getSellPrice() {
        return getSellPrice(level);
    }
    private void setLevel(int level) {
        this.level = level;
        this.power = (float) (10 * Math.pow(1.2, level - 1));
        this.interval = 5.5f - level / 2.0f;
        this.range = 2 + level * 2;
        float barrelSize = 0.5f + level * 0.1f;
        barrelRect.set(dstRect);
        barrelRect.inset(-barrelSize, -barrelSize);
        bitmap = BitmapPool.get(BITMAP_IDS[level - 1]);
    }

    private static int[] BITMAP_IDS = {
            R.mipmap.f_01_01,R.mipmap.f_02_01,R.mipmap.f_03_01,R.mipmap.f_04_01,R.mipmap.f_05_01,
            R.mipmap.f_06_01,R.mipmap.f_07_01,R.mipmap.f_08_01,R.mipmap.f_09_01,R.mipmap.f_10_01,
    };

    private static Paint rangePaint;
    private void drawRange(Canvas canvas) {
        if (rangePaint == null) {
            rangePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            rangePaint.setStyle(Paint.Style.STROKE);
            rangePaint.setStrokeWidth(0.1f);
            rangePaint.setPathEffect(new DashPathEffect(new float[]{0.1f, 0.2f}, 0));
            rangePaint.setColor(0x7F7F0000);
        }
        canvas.drawCircle(x, y, range, rangePaint);
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(barrelBitmap, null, barrelRect, null);
        canvas.restore();
        drawRange(canvas);
    }

    @Override
    public void update() {
        super.update();
        Fly fly = findNearestFly();
        if (fly != null) {
            angle = (float) (Math.atan2(fly.getY() - y, fly.getX() - x) / Math.PI * 180);
        }
        time += BaseScene.frameTime;
        if (time > interval && fly != null) {
            Shell shell = Shell.get(this, fly);
            BaseScene.getTopScene().add(MainScene.Layer.shell, shell);
            time = 0;
        }
    }

    public Fly findNearestFly() {
        float dist = range;
        Fly nearest = null;
        ArrayList<IGameObject> flies = BaseScene.getTopScene().getObjectsAt(MainScene.Layer.enemy);
        for (IGameObject gameObject: flies) {
            if (!(gameObject instanceof Fly)) continue;
            Fly fly = (Fly) gameObject;
            float fx = fly.getX();
            float fy = fly.getY();
            float dx = x - fx;
            if (dx > dist) continue;
            float dy = y - fy;
            if (dy > dist) continue;
            float d = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist > d) {
                dist = d;
                nearest = fly;
            }
        }
        return nearest;
    }

    public void upgrade() {
        if (level == BITMAP_IDS.length) {
            uninstall();
            return;
        }

        setLevel(level + 1);
    }

    public void uninstall() {
        BaseScene.getTopScene().remove(MainScene.Layer.cannon, this);
    }
}
