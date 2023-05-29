package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.scene.MainScene;

public class InstallationMenu extends Sprite {
    public static final int MENU_ITEM_SIZE = 2;
    public static final int[] BLANK_INT_ARRAY = {};
    private int[] items;
    private RectF itemRect = new RectF();
    private Cannon cannon;
    private int installX, installY;

    public InstallationMenu() {
        bitmap = BitmapPool.get(R.mipmap.menu_bg);
        items = BLANK_INT_ARRAY;
    }

    public void setMenu(Cannon cannon, float x, float y, int... items) {
        this.cannon = cannon;
        this.installX = (int)x;
        this.installY = (int)y;
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

    public boolean handleTouch(int action, float gx, float gy) {
        if (this.items.length == 0) return false;
        if (!dstRect.contains(gx, gy)) {
            hide();
            return false;
        }
        itemRect.set(dstRect);
        itemRect.right = itemRect.left + MENU_ITEM_SIZE;
        int foundItem = 0;
        for (int item: items) {
            if (itemRect.contains(gx, gy)) {
                foundItem = item;
                break;
            }
            itemRect.offset(MENU_ITEM_SIZE, 0);
        }
        hide();
        int cannonLevel = 0;
        switch (foundItem) {
            case R.mipmap.f_01_01: install(1); break;
            case R.mipmap.f_02_01: install(2); break;
            case R.mipmap.f_03_01: install(3); break;
            case R.mipmap.upgrade: cannon.upgrade(); break;
            case R.mipmap.uninstall: cannon.uninstall(); break;
        }
        return true;
    }

    private void install(int cannonLevel) {
        Cannon cannon = new Cannon(cannonLevel, installX, installY);
        BaseScene scene = BaseScene.getTopScene();
        scene.add(MainScene.Layer.cannon, cannon);
    }
}
