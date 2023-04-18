package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;

public class JellyItem extends MapObject {
    private static final int SIZE = 66;
    private static final int BORDER = 2;
    protected Rect srcRect = new Rect();
    public JellyItem() {
        setBitmapResource(R.mipmap.jelly);
        setSrcRect(0);
        dstRect.set(15, 5, 16, 6);
    }

    private void setSrcRect(int index) {
        int x = index % 100;
        int y = index / 100;
        int left = x * SIZE + BORDER;
        int top = y * SIZE + BORDER;
        srcRect.set(left, top, left + SIZE, top + SIZE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
