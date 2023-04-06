package kr.ac.tukorea.ge.spgp2023.dragonflight.framework;

import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.dragonflight.game.Bullet;

public class BaseScene {
    private static ArrayList<BaseScene> stack = new ArrayList<>();
    public static float frameTime;
    protected static Handler handler = new Handler();

    public static BaseScene getTopScene() {
        int top = stack.size() - 1;
        if (top < 0) return null;
        return stack.get(top);
    }

    public static void popAll() {
        while (!stack.isEmpty()) {
            BaseScene scene = getTopScene();
            scene.popScene();
        }
    }

    public int pushScene() {
        stack.add(this);
        return stack.size();
    }

    public void popScene() {
        stack.remove(this);
        // TODO: additional callback should be called
    }

    public int add(IGameObject gobj) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                objects.add(gobj);
            }
        });
        return objects.size();
    }

    public int count() {
        return objects.size();
    }
    public void update(long elapsedNanos) {
        frameTime = elapsedNanos / 1_000_000_000f;
        for (IGameObject gobj : objects) {
            gobj.update();
        }
    }

    public void draw(Canvas canvas) {
        for (IGameObject gobj : objects) {
            gobj.draw(canvas);
        }
    }

    private ArrayList<IGameObject> objects = new ArrayList<>();

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void remove(IGameObject gobj) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                objects.remove(gobj);
            }
        });
    }
}
