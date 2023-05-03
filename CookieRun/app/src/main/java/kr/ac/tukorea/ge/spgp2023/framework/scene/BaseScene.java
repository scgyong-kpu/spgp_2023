package kr.ac.tukorea.ge.spgp2023.framework.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.cookierun.BuildConfig;
import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2023.framework.interfaces.ITouchable;

public class BaseScene {
    private static ArrayList<BaseScene> stack = new ArrayList<>();
    public static float frameTime;
    protected static Handler handler = new Handler();
    private static Paint bboxPaint;

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

    protected <E extends Enum<E>> void initLayers(E countEnum) {
        int layerCount = countEnum.ordinal();
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public <E extends Enum<E>> void add(E layerEnum, IGameObject gobj, boolean immediate) {
        if (immediate) {
            add(layerEnum, gobj);
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                add(layerEnum, gobj);
            }
        });
    }
    public <E extends Enum<E>> void add(E layerEnum, IGameObject gobj) {
        ArrayList<IGameObject> objects = layers.get(layerEnum.ordinal());
        objects.add(gobj);
    }
    public <E extends Enum> void remove(E layerEnum, IGameObject gobj, boolean immediate) {
        if (immediate) {
            remove(layerEnum, gobj);
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                remove(layerEnum, gobj);
            }
        });
    }

    public <E extends Enum> void remove(E layerEnum, IGameObject gobj) {
        boolean removed = getObjectsAt(layerEnum).remove(gobj);
        if (removed && gobj instanceof IRecyclable) {
            RecycleBin.collect((IRecyclable) gobj);
        }
    }

    public int count() {
        int count = 0;
        for (ArrayList<IGameObject> objects: layers) {
            count += objects.size();
        }
        return count;
    }
    public void update(long elapsedNanos) {
        frameTime = elapsedNanos / 1_000_000_000f;
        for (ArrayList<IGameObject> objects: layers) {
            for (int i = objects.size() - 1; i >= 0; i--) {
                IGameObject gobj = objects.get(i);
                gobj.update();
            }
        }
    }

    public void draw(Canvas canvas) {
        for (ArrayList<IGameObject> objects: layers) {
            for (IGameObject gobj : objects) {
                gobj.draw(canvas);
            }
        }

        if (BuildConfig.DEBUG) {
            if (bboxPaint == null) {
                bboxPaint = new Paint();
                bboxPaint.setStyle(Paint.Style.STROKE);
                bboxPaint.setColor(Color.RED);
            }
            for (ArrayList<IGameObject> objects: layers) {
                for (IGameObject gobj : objects) {
                    if (gobj instanceof IBoxCollidable) {
                        RectF rect = ((IBoxCollidable) gobj).getCollisionRect();
                        canvas.drawRect(rect, bboxPaint);
                    }
                }
            }
        }
    }

    protected ArrayList<ArrayList<IGameObject>> layers = new ArrayList<>();
    public <E extends Enum> ArrayList<IGameObject> getObjectsAt(E layerEnum) {
        return layers.get(layerEnum.ordinal());
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touchLayer = getTouchLayerIndex();
        if (touchLayer < 0) return false;
        ArrayList<IGameObject> gameObjects = layers.get(touchLayer);
        for (IGameObject gobj : gameObjects) {
            if (!(gobj instanceof ITouchable)) {
                continue;
            }
            boolean processed = ((ITouchable) gobj).onTouchEvent(event);
            if (processed) return true;
        }
        return false;
    }

    protected int getTouchLayerIndex() {
        return -1;
    }

    public boolean clipsRect() {
        return true;
    }
}
