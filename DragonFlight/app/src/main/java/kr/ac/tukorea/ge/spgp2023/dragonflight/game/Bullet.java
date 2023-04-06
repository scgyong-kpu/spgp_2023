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
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.Sprite;

public class Bullet extends Sprite implements IBoxCollidable {
    private static final float BULLET_WIDTH = 28 * 0.0243f;
    private static final float BULLET_HEIGHT = 40 * 0.0243f;
    private static final String TAG = Bullet.class.getSimpleName();
    protected static float SPEED = 20.0f;
    protected static Paint paint;

    protected static ArrayList<Bullet> recycleBin = new ArrayList<>();

    public static Bullet get(float x, float y) {
        if (recycleBin.size() > 0) {
//            Log.d(TAG, "get(): Recycle Bin has " + recycleBin.size() + " bullets");
            Bullet bullet = recycleBin.remove(0);
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
            recycleBin.add(this);
//            Log.d(TAG, "remove(): Recycle Bin has " + recycleBin.size() + " bullets");
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }
}
