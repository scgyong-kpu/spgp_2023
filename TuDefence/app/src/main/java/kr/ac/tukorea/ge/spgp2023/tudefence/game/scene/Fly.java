package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2023.framework.objects.SheetSprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;

public class Fly extends SheetSprite implements IRecyclable {

    private Type type;
    private float speed, distance;

    private static Path path;
    private static PathMeasure pm;
    private static final float length;
    private static Paint paint;

    static {
        path = new Path();
        path.moveTo(0, 18);
        path.lineTo(7, 0);
        path.lineTo(16, 18);
        path.lineTo(25, 0);
        path.lineTo(32, 18);

        pm = new PathMeasure(path, false);
        length = pm.getLength();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.1f);
        paint.setColor(Color.MAGENTA);
    }

    public static void drawPath(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public enum Type {
        boss, red, blue, cyan, dragon, COUNT;

        public static Type random(Random rand) {
            return Type.values()[rand.nextInt(COUNT.ordinal())];
        }
    }
    public static Fly get(Type type, float speed, float size) {
        Fly fly = (Fly) RecycleBin.get(Fly.class);
        if (fly == null) {
            fly = new Fly();
        }
        fly.init(type, speed, size);
        return fly;
    }

    private Fly() {
        super(R.mipmap.galaga_flies, 2.0f);
        if (rects_array == null) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            rects_array = new Rect[Type.COUNT.ordinal()][];
            int x = 0;
            for (int i = 0; i < Type.COUNT.ordinal(); i++) {
                rects_array[i] = new Rect[2];
                for (int j = 0; j < 2; j++) {
                    rects_array[i][j] = new Rect(x, 0, x+h, h);
                    x += h;
                }
            }
        }
    }

    private Rect[][] rects_array;
    private void init(Type type, float speed, float size) {
        this.type = type;
        this.speed = speed;
        this.width = this.height = size;
        this.distance = 0;
        srcRects = rects_array[type.ordinal()];
    }

    private float[] pos = new float[2];
    private float[] tan = new float[2];
    @Override
    public void update() {
        super.update();
        distance += speed * BaseScene.frameTime;
        pm.getPosTan(distance, pos, tan);
        moveTo(pos[0], pos[1]);
        if (distance > length) {
            BaseScene.getTopScene().remove(MainScene.Layer.enemy, this);
        }
    }

    @Override
    public void onRecycle() {
    }
}
