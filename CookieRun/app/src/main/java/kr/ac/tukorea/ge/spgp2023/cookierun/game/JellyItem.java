package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;

public class JellyItem extends MapObject {
    public static final int JELLY_COUNT = 60;
    private static final int ITEMS_IN_A_ROW = 30;
    private static final int SIZE = 66;
    private static final int BORDER = 2;
    public static final float INSET = 0.20f;
    protected Rect srcRect = new Rect();
    protected RectF collisionRect = new RectF();

    JellyItem() {
        setBitmapResource(R.mipmap.jelly);
        width = height = 1;
    }
    public static JellyItem get(int index, float left, float top) {
        JellyItem item = (JellyItem) RecycleBin.get(JellyItem.class);
        if (item == null) {
            item = new JellyItem();
        }
        item.init(index, left, top);
        return item;
    }

    public static int getRandomIndex(Random random) {
        return random.nextInt(JELLY_COUNT);
    }

    private void init(int index, float left, float top) {
        setSrcRect(index);
        dstRect.set(left, top, left + width, top + height);
    }

    private void setSrcRect(int index) {
        int x = index % ITEMS_IN_A_ROW;
        int y = index / ITEMS_IN_A_ROW;
        int left = x * (SIZE + BORDER) + BORDER;
        int top = y * (SIZE + BORDER) + BORDER;
        srcRect.set(left, top, left + SIZE, top + SIZE);
    }

    @Override
    public void update() {
        super.update();
        collisionRect.set(
                dstRect.left + width * INSET,
                dstRect.top + height * INSET,
                dstRect.right - width * INSET,
                dstRect.bottom - height * INSET);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    protected MainScene.Layer getLayer() {
        return MainScene.Layer.item;
    }
}
