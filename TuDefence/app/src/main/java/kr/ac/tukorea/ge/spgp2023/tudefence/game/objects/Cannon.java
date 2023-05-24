package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.scene.MainScene;

public class Cannon extends Sprite {
    private int level;
    private float power, interval;
    private float angle;
    private Bitmap barrelBitmap;
    private RectF barrelRect;
    public Cannon(int level, int x, int y) {
        super(BITMAP_IDS[level - 1], x, y, 2, 2);
        this.level = level;
        this.power = level * 2;
        this.interval = 5.0f - level / 2.0f;
        barrelBitmap = BitmapPool.get(R.mipmap.tank_barrel);
        barrelRect = new RectF(dstRect);
        float barrelSize = 0.5f + level * 0.1f;
        barrelRect.inset(-barrelSize, -barrelSize);
    }
    private static int[] BITMAP_IDS = {
            R.mipmap.f_01_01,R.mipmap.f_02_01,R.mipmap.f_03_01,R.mipmap.f_04_01,R.mipmap.f_05_01,
            R.mipmap.f_06_01,R.mipmap.f_07_01,R.mipmap.f_08_01,R.mipmap.f_09_01,R.mipmap.f_10_01,
    };

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(barrelBitmap, null, barrelRect, null);
        canvas.restore();
    }

    @Override
    public void update() {
        super.update();
        Fly fly = findNearestFly();
        if (fly != null) {
            angle = (float) (Math.atan2(fly.getY() - y, fly.getX() - x) / Math.PI * 180);
        }
    }

    public Fly findNearestFly() {
        float dist = Float.MAX_VALUE;
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
}
