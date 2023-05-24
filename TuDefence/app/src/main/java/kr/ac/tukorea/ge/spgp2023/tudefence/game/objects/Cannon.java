package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;

public class Cannon extends Sprite {
    private int level;
    private float power, interval;
    private Bitmap barrelBitmap;
    private RectF barrelRect;
    public Cannon(int level, int x, int y) {
        super(BITMAP_IDS[level - 1], x, y, 2, 2);
        this.level = level;
        this.power = level * 2;
        this.interval = 5.0f - level / 2.0f;
        barrelBitmap = BitmapPool.get(R.mipmap.tank_barrel);
        barrelRect = new RectF(dstRect);
        barrelRect.inset(-1.5f, -1.5f);
    }
    private static int[] BITMAP_IDS = {
            R.mipmap.f_01_01,R.mipmap.f_02_01,R.mipmap.f_03_01,R.mipmap.f_04_01,R.mipmap.f_05_01,
            R.mipmap.f_06_01,R.mipmap.f_07_01,R.mipmap.f_08_01,R.mipmap.f_09_01,R.mipmap.f_10_01,
    };

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(barrelBitmap, null, barrelRect, null);
    }
}
