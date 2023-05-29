package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.objects.TiledBackground;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.scene.MainScene;

public class Selector extends Sprite {
    private static final String TAG = Selector.class.getSimpleName();
    private final TiledBackground bg;
    private final Paint candPaint;
    private float candidateX, candidateY;

    public Selector(TiledBackground tiledBg) {
        super(R.mipmap.selection, -1, -1, 2, 2);
        this.bg = tiledBg;

        candPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        candPaint.setColor(0x7FBF3F3F);
        candPaint.setStyle(Paint.Style.FILL);
    }

    public boolean onTouch(int action, float gx, float gy) {
        Cannon cannon = findCannonAt(gx, gy);
        if (cannon != null) {
            candidateX = candidateY = -1;
            if (action == MotionEvent.ACTION_UP) {
                cannon.upgrade();
            } else {
                moveTo(cannon.getX(), cannon.getY());
            }
            return true;
        }
        moveTo(gx, gy);
        if (intersectsIfInstalledAt(gx, gy)) {
            return true;
        }
        int x = Math.round(gx);
        int y = Math.round(gy);
        boolean canInstall = bg.canInstallAt(x, y);
        if (canInstall) {
            if (action != MotionEvent.ACTION_UP) {
                candidateX = x;
                candidateY = y;
                return true;
            }
            candidateX = candidateY = -1;
        } else {
            candidateX = candidateY = -1;
            return true;
        }
        cannon = new Cannon(1, x, y);
        BaseScene scene = BaseScene.getTopScene();
        scene.add(MainScene.Layer.cannon, cannon);
        moveTo(x, y);
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (candidateX >= 0) {
            canvas.drawRect(
                    candidateX - 1, candidateY - 1,
                    candidateX + 1, candidateY + 1,
                    candPaint);
        }
    }

    private RectF instRect = new RectF();
    private Cannon findCannonAt(float x, float y) {
        BaseScene scene = BaseScene.getTopScene();
        for (IGameObject obj: scene.getObjectsAt(MainScene.Layer.cannon)) {
            Cannon cannon = (Cannon) obj;
            float cx = cannon.getX(), cy = cannon.getY();
            instRect.set(cx - 1, cy - 1, cx + 1, cy + 1);
            if (instRect.contains(x, y)) {
                return cannon;
            }
        }
        return null;
    }
    private boolean intersectsIfInstalledAt(float x, float y) {
        instRect.set(x - 1, y - 1, x + 1, y + 1);
        BaseScene scene = BaseScene.getTopScene();
        for (IGameObject obj: scene.getObjectsAt(MainScene.Layer.cannon)) {
            Cannon cannon = (Cannon) obj;
            float cx = cannon.getX(), cy = cannon.getY();
            if (instRect.intersects(cx - 1, cy - 1, cx + 1, cy + 1)) {
                return true;
            }
        }
        return false;
    }
}
