package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;

public class MapObject extends Sprite implements IBoxCollidable {
    private static final String TAG = MapObject.class.getSimpleName();
    @Override
    public void update() {
        float speed = -2.0f;
        float dx = speed * BaseScene.frameTime;
        dstRect.offset(dx, 0);
        if (dstRect.right < 0) {
            Log.d(TAG, "Removing:" + this);
            BaseScene.getTopScene().remove(getLayer(), this);
        }
    }

    protected MainScene.Layer getLayer() {
        return MainScene.Layer.platform;
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }
}
