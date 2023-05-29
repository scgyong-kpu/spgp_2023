package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.scene.MainScene;

public class InstallationMenu extends Sprite {
    public interface Listener {
        public void onMenu(int menuId);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private Listener listener;
    public static final int MENU_ITEM_SIZE = 2;
    public static final int[] BLANK_INT_ARRAY = {};
    private int[] items;
    private RectF itemRect = new RectF();
    private Paint alphaPaint = new Paint();
    public InstallationMenu(Listener listener) {
        this.listener = listener;
        bitmap = BitmapPool.get(R.mipmap.menu_bg);
        items = BLANK_INT_ARRAY;
    }

    public void setMenu(float x, float y, int... items) {
        float left = x + MENU_ITEM_SIZE / 2;
        float top = y - MENU_ITEM_SIZE / 2;
        this.items = items;
        dstRect.set(left, top, left + items.length * MENU_ITEM_SIZE, top + MENU_ITEM_SIZE);

        ValueAnimator animator = ValueAnimator
                .ofInt(0, 192)
                .setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                alphaPaint.setAlpha((Integer)valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void hide() {
        this.items = BLANK_INT_ARRAY;
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.items.length == 0) return;
//        super.draw(canvas);
        canvas.drawBitmap(bitmap, null, dstRect, alphaPaint);
        itemRect.set(dstRect);
        itemRect.right = itemRect.left + MENU_ITEM_SIZE;
        for (int item: items) {
            Bitmap bitmap = BitmapPool.get(item);
            canvas.drawBitmap(bitmap, null, itemRect, alphaPaint);
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
        listener.onMenu(foundItem);
        return true;
    }
}
