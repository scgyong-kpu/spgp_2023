package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2023.framework.objects.SheetSprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.scene.MainScene;

public class Fly extends SheetSprite implements IRecyclable {

    private Type type;
    private float speed, distance;
    private float angle;
    private float dx, dy;


    private static Random random = new Random();
    private static Path path;
    private static PathMeasure pm;
    private static final float length;
//    private static Paint paint;

    static {
        path = new Path();
        path.moveTo(-1.28f, 18.176f);
        path.cubicTo(1.984f, 17.856f, 3.84f, 15.584f, 3.84f, 12.8f);
        path.cubicTo(3.84f, 10.016f, 0.864f, 9.568f, 0.896f, 6.56f);
        path.cubicTo(0.928f, 3.552f, 3.328f, 0.544f, 6.4f, 0.512f);
        path.cubicTo(9.472f, 0.48f, 11.776f, 3.392f, 11.84f, 6.496f);
        path.cubicTo(11.904f, 9.6f, 9.888f, 9.248f, 9.92f, 12.512f);
        path.cubicTo(9.952f, 15.776f, 14.4f, 16.928f, 16.096f, 16.928f);
        path.cubicTo(17.792f, 16.928f, 22.176f, 15.168f, 22.208f, 12.64f);
        path.cubicTo(22.24f, 10.112f, 19.936f, 9.408f, 19.936f, 6.624f);
        path.cubicTo(19.936f, 3.84f, 22.368f, 0.832f, 25.76f, 0.864f);
        path.cubicTo(29.152f, 0.896f, 31.2f, 4.192f, 31.104f, 6.752f);
        path.cubicTo(31.008f, 9.312f, 28.16f, 10.784f, 28.192f, 13.408f);
        path.cubicTo(28.224f, 16.032f, 31.552f, 17.824f, 33.6f, 17.664f);


        pm = new PathMeasure(path, false);
        length = pm.getLength();

//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(0.1f);
//        paint.setColor(Color.MAGENTA);
    }

//    public static void drawPath(Canvas canvas) {
//        canvas.drawPath(path, paint);
//    }

    public enum Type {
        boss, red, blue, cyan, dragon, COUNT, RANDOM;
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
        if (type == Type.RANDOM) {
            type = Type.values()[random.nextInt(Type.COUNT.ordinal())];
        }
        this.type = type;
        this.speed = speed;
        this.width = this.height = size;
        this.distance = 0;
        dx = dy = 0;
        srcRects = rects_array[type.ordinal()];

        pm.getPosTan(0, pos, tan);
        moveTo(pos[0], pos[1]);
    }

    private float[] pos = new float[2];
    private float[] tan = new float[2];
    @Override
    public void update() {
        super.update();
        distance += speed * BaseScene.frameTime;
        float maxDiff = width / 5;
        dx += (2 * maxDiff * random.nextFloat() - maxDiff) * BaseScene.frameTime;
        if (dx < -maxDiff) dx = -maxDiff;
        else if (dx > maxDiff) dx = maxDiff;
        dy += (2 * maxDiff * random.nextFloat() - maxDiff) * BaseScene.frameTime;
        if (dy < -maxDiff) dy = -maxDiff;
        else if (dy > maxDiff) dy = maxDiff;

        pm.getPosTan(distance, pos, tan);
        moveTo(pos[0] + dx, pos[1] + dy);
        angle = (float)(Math.atan2(tan[1], tan[0]) * 180 / Math.PI) ;
        if (distance > length) {
            BaseScene.getTopScene().remove(MainScene.Layer.enemy, this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        super.draw(canvas);
        canvas.restore();
    }

    @Override
    public void onRecycle() {
    }
}
