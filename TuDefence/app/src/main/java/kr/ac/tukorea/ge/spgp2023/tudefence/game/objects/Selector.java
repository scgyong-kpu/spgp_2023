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

public class Selector extends Sprite implements InstallationMenu.Listener {
    private static final String TAG = Selector.class.getSimpleName();
    private final TiledBackground bg;
    private final Paint candPaint;
    private float candidateX, candidateY;
    private InstallationMenu menu;
    private Cannon cannon;

    public Selector(TiledBackground tiledBg) {
        super(R.mipmap.selection, -1, -1, 2, 2);
        this.bg = tiledBg;

        menu = new InstallationMenu(this);
        candPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        candPaint.setColor(0x7FBF3F3F);
        candPaint.setStyle(Paint.Style.FILL);
    }

    public boolean onTouch(int action, float gx, float gy) {
        if (menu.handleTouch(action, gx, gy)) {
            return false;
        }
        Cannon cannon = findCannonAt(gx, gy);
        if (cannon != null) {
            candidateX = candidateY = -1;
            float cx = cannon.getX(), cy = cannon.getY();
            if (action == MotionEvent.ACTION_UP) {
                //cannon.upgrade();
                this.cannon = cannon;
                menu.setMenu(cx, cy, R.mipmap.upgrade, R.mipmap.uninstall);
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
        if (!canInstall) {
            candidateX = candidateY = -1;
            if (action == MotionEvent.ACTION_UP) {
                moveTo(-1, -1);
                menu.hide();
            }
            return true;
        }
        if (action != MotionEvent.ACTION_UP) {
            candidateX = x;
            candidateY = y;
            return true;
        }
        moveTo(x, y);
        menu.setMenu(x, y, R.mipmap.f_01_01, R.mipmap.f_02_01, R.mipmap.f_03_01);
        candidateX = candidateY = -1;
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
        menu.draw(canvas);
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

    @Override
    public void onMenu(int menuId) {
        switch (menuId) {
            case R.mipmap.f_01_01: install(1); break;
            case R.mipmap.f_02_01: install(2); break;
            case R.mipmap.f_03_01: install(3); break;
            case R.mipmap.upgrade: cannon.upgrade(); break;
            case R.mipmap.uninstall: cannon.uninstall(); break;
        }
        moveTo(-1, -1);
    }
    private void install(int cannonLevel) {
        Cannon cannon = new Cannon(cannonLevel, (int)x, (int)y);
        BaseScene scene = BaseScene.getTopScene();
        scene.add(MainScene.Layer.cannon, cannon);
    }
}
