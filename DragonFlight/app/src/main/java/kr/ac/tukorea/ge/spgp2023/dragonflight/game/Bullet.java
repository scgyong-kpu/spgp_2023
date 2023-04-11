package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.BaseScene;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.IGameObject;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.IRecyclable;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.RecycleBin;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.Sprite;

public class Bullet extends Sprite implements IBoxCollidable, IRecyclable {
    private static final float BULLET_WIDTH = 28 * 0.0243f;
    private static final float BULLET_HEIGHT = 40 * 0.0243f;
    private static final String TAG = Bullet.class.getSimpleName();
    protected static float SPEED = 20.0f;
    protected static Paint paint;

    public static Bullet get(float x, float y) {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if (bullet != null) {
            bullet.x = x;
            bullet.y = y;
            return bullet;
        }
        return new Bullet(x, y);
    }
    private Bullet(float x, float y) {
        super(R.mipmap.laser_1, x, y, BULLET_WIDTH, BULLET_HEIGHT);
        this.x = x;
        this.y = y;
    }
    @Override
    public void update() {
        float frameTime = BaseScene.frameTime;
        y += -SPEED * frameTime;
        fixDstRect();

        if (dstRect.bottom < 0) {
            BaseScene.getTopScene().remove(this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {
    }

    public int getPower() {
        return 10;
    }
}
