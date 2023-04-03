package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class BaseScene {
    private static ArrayList<BaseScene> stack = new ArrayList<>();
    public static float frameTime;

    public static BaseScene getTopScene() {
        int top = stack.size() - 1;
        if (top < 0) return null;
        return stack.get(top);
    }
    public int pushScene() {
        stack.add(this);
        return stack.size();
    }

    public int add(IGameObject gobj) {
        objects.add(gobj);
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
}
