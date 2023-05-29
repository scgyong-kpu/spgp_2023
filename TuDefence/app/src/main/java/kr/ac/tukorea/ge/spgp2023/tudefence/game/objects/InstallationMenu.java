package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;

public class InstallationMenu extends Sprite {
    public static final int MENU_ITEM_SIZE = 2;
    public static final int[] BLANK_INT_ARRAY = {};
    private int[] items;
    private RectF itemRect = new RectF();

    public InstallationMenu() {
        bitmap = BitmapPool.get(R.mipmap.menu_bg);
        items = BLANK_INT_ARRAY;
    }

    public void setMenu(float x, float y, int... items) {
        float left = x + MENU_ITEM_SIZE / 2;
        float top = y - MENU_ITEM_SIZE / 2;
        this.items = items;
        dstRect.set(left, top, left + items.length * MENU_ITEM_SIZE, top + MENU_ITEM_SIZE);
    }

    public void hide() {
        this.items = BLANK_INT_ARRAY;
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.items.length == 0) return;
        super.draw(canvas);
        itemRect.set(dstRect);
        itemRect.right = itemRect.left + MENU_ITEM_SIZE;
        for (int item: items) {
            Bitmap bitmap = BitmapPool.get(item);
            canvas.drawBitmap(bitmap, null, itemRect, null);
            itemRect.offset(MENU_ITEM_SIZE, 0);
        }
    }
}
