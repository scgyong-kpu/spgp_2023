package kr.ac.tukorea.ge.spgp2023.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Fighter {
    private static Bitmap bitmap;
    private RectF dstRect = new RectF();

    public Fighter() {
        float cx = 5.0f, y = 12.0f;
        float r = 1.25f;
        dstRect.set(cx-r, y, cx+r, y+2*r);
    }

    public static void setBitmap(Bitmap bitmap) {
        Fighter.bitmap = bitmap;
    }

    public void update() {
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void setPosition(float x, float y) {
        float r = 1.25f;
        dstRect.set(x-r, y-r, x+r, y+r);
    }
}
