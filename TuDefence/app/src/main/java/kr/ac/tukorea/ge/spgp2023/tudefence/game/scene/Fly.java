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
        path.moveTo(-1.28f, 18.176f);
        path.cubicTo(1.984f, 17.856f, 3.84f, 15.584f, 3.84f, 12.8f);
        path.cubicTo(3.84f, 10.016f, 0.864f, 9.568f, 0.896f, 6.56f);
        path.cubicTo(0.928f, 3.552f, 3.328f, 0.544f, 6.4f, 0.512f);
        path.cubicTo(9.472f, 0.48f, 11.776f, 3.392f, 11.84f, 6.496f);
        path.cubicTo(11.904f, 9.6f, 9.888f, 9.248f, 9.92f, 12.512f);
        path.cubicTo(9.952f, 15.776f, 14.4f, 16.928f, 16.096f, 16.928f);
        path.cubicTo(17.792f, 16.928f, 22.08f, 14.752f, 22.112f, 12.224f);
        path.cubicTo(22.144f, 9.696f, 19.936f, 8.832f, 19.936f, 6.048f);
        path.cubicTo(19.936f, 3.264f, 23.488f, 0.864f, 25.76f, 0.864f);
        path.cubicTo(28.032f, 0.864f, 31.232f, 4.064f, 31.392f, 6.336f);
        path.cubicTo(31.552f, 8.608f, 28.064f, 11.392f, 28.192f, 13.408f);
        path.cubicTo(28.32f, 15.424f, 32f, 18.208f, 32.32f, 17.568f);

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
