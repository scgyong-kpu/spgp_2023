package kr.ac.tukorea.ge.spgp2023.tudefence.game.scene;

import android.graphics.Rect;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2023.framework.objects.SheetSprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;

public class Fly extends SheetSprite implements IRecyclable {

    private Type type;

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
        this.type = type;
        this.width = this.height = size;
        srcRects = rects_array[type.ordinal()];
    }

    @Override
    public void onRecycle() {
    }
}
