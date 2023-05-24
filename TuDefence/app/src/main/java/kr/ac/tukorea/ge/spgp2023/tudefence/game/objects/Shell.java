package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2023.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.scene.MainScene;

public class Shell extends Sprite implements IRecyclable {
    private Rect srcRect = new Rect();
    private float dx, dy, radius;
    private Fly target;
    private float power;

    private Shell() {
        super(R.mipmap.shells, 0, 0, 0.5f, 0.5f);
    }

    public static Shell get(Cannon cannon, Fly target) {
        Shell shell = (Shell) RecycleBin.get(Shell.class);
        if (shell == null) {
            shell = new Shell();
        }
        shell.init(cannon, target);
        return shell;
    }

    private void init(Cannon cannon, Fly target) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int maxLevel = w / h;
        int level = cannon.level;
        if (level < 1) level = 1;
        if (level > maxLevel) level = maxLevel;
        srcRect.set(h * (level - 1), 0, h * level, h);
        //Log.d("CannonFire", "shell rect: " + srcRect);
        this.x = cannon.getX();
        this.y = cannon.getY();
        this.target = target;
        double radian = cannon.angle * Math.PI / 180;
        double speed = 10.0 + level;
        dx = (float) (speed * Math.cos(radian));
        dy = (float) (speed * Math.sin(radian));
        this.power = level;
        radius = 0.2f + level * 0.02f;
        setSize(2 * radius, 2 * radius);
    }

    @Override
    public void update() {
        BaseScene scene = BaseScene.getTopScene();
        x += dx * BaseScene.frameTime;
        y += dy * BaseScene.frameTime;
        fixDstRect();
        if (x < -radius || x > Metrics.game_width + radius ||
                y < -radius || y > Metrics.game_height + radius) {
            //Log.d("CannonFire", "Remove(" + x + "," + y + ") " + this);
            scene.remove(MainScene.Layer.shell, this);
            return;
        }
        if (target == null) {
            return;
        }
        float dx = x - target.getX();
        float dy = y - target.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        float flyRadius = target.getWidth() / 2;
        if (dist < radius + flyRadius) {
            scene.remove(MainScene.Layer.shell, this);
            scene.remove(MainScene.Layer.enemy, target);
            target = null;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    @Override
    public void onRecycle() {

    }
}
