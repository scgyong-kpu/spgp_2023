package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.animation.ValueAnimator;
import android.graphics.RectF;
import android.view.animation.BounceInterpolator;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;

public class Obstacle extends MapObject {
    protected Obstacle() {
        super(MainScene.Layer.obstacle);
    }
    public static Obstacle get(int index, float unitLeft, float unitTop) {
        Obstacle obs = (Obstacle) RecycleBin.get(Obstacle.class);
        if (obs == null) {
            obs = new Obstacle();
        }
        obs.init(index, unitLeft, unitTop);
        return obs;
    }

    protected RectF collisionRect = new RectF();
    protected long createdOn;
    protected Modifier modifier;
    protected ValueAnimator animator;

    protected static class Modifier {
        protected final float width, height;
        protected int mipmapResId;
        public Modifier(float width, float height, int mipmapResId) {
            this.width = width;
            this.height = height;
            this.mipmapResId = mipmapResId;
        }
        public void init(Obstacle obstacle, float left, float top) {
            top -= this.height - 1;
            obstacle.dstRect.set(left, top, left + width, top + height);
            if (mipmapResId != 0) {
                obstacle.bitmap = BitmapPool.get(mipmapResId);
            }
        }
        public void update(Obstacle obstacle) {
            obstacle.collisionRect.set(obstacle.dstRect);
        }
    }
    protected static class AnimModifier extends Modifier {
        private final float transparentTop;
        protected int[] mipmapResIds;
        public AnimModifier(float width, float height, int[] mipmapResIds, float transparentTop) {
            super(width, height, 0);
            this.mipmapResIds = mipmapResIds;
            this.transparentTop = transparentTop;
        }
        @Override
        public void init(Obstacle obstacle, float unitLeft, float unitTop) {
            super.init(obstacle, unitLeft, unitTop);
            obstacle.bitmap = BitmapPool.get(mipmapResIds[0]);
            obstacle.createdOn = System.currentTimeMillis();
        }
        @Override
        public void update(Obstacle obstacle) {
            RectF rect = obstacle.dstRect;
            float height = rect.height();
            float trans = height * transparentTop;
            obstacle.collisionRect.set(
                    rect.left,
                    rect.top + trans,
                    rect.right,
                    rect.bottom
            );

            float elapsed = (System.currentTimeMillis() - obstacle.createdOn) / 1000.f;
            final float start = 2.0f;
            if (elapsed > start) {
                final float fps = 8.0f;
                int index = Math.round((elapsed - start) * fps);
                if (index < mipmapResIds.length) {
                    obstacle.bitmap = BitmapPool.get(mipmapResIds[index]);
                }
            }
        }
    }

    private static class MoveModifier extends Modifier {
        public MoveModifier(float widthUnit, float heightUnit, int mipmapResId) {
            super(widthUnit, heightUnit, mipmapResId);
        }
        protected static Random random = new Random();

        @Override
        public void init(Obstacle obstacle, float unitLeft, float unitTop) {
            super.init(obstacle, unitLeft, unitTop);
            float height = obstacle.getDstHeight();
            float bottom = obstacle.dstRect.bottom;
            obstacle.dstRect.offset(0, -bottom);
            ValueAnimator animator = ValueAnimator.ofFloat(0, bottom);
            animator.setStartDelay(random.nextInt(800) + 2000);
            animator.setDuration(random.nextInt(800) + 600);
            animator.setInterpolator(new BounceInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator va) {
                    float value = (Float)va.getAnimatedValue();
                    obstacle.dstRect.set(
                            obstacle.dstRect.left,
                            value - height,
                            obstacle.dstRect.right,
                            value
                    );
                    obstacle.collisionRect.set(obstacle.dstRect);
                }
            });
            animator.start();
            obstacle.animator = animator;
        }
    }

    protected static Modifier[] MODIFIERS = {
            new Modifier(0.78f, 0.78f*99/63f, R.mipmap.epn01_tm01_jp1a),
            new AnimModifier(1, 131/81f, new int[] {
                    R.mipmap.trans_00p,
                    R.mipmap.epn01_tm01_jp1up_01,
                    R.mipmap.epn01_tm01_jp1up_02,
                    R.mipmap.epn01_tm01_jp1up_03,
                    R.mipmap.epn01_tm01_jp1up_04,
            }, 64/131f),
            new AnimModifier(1.1f, 1.1f*222/87f, new int[] {
                    R.mipmap.trans_00p,
                    R.mipmap.epn01_tm01_jp2up_01,
                    R.mipmap.epn01_tm01_jp2up_02,
                    R.mipmap.epn01_tm01_jp2up_03,
                    R.mipmap.epn01_tm01_jp2up_04,
                    R.mipmap.epn01_tm01_jp2up_05,
            }, 68/222f),
            new MoveModifier(1, 482/86f, R.mipmap.epn01_tm01_sda),
    } ;
    public static final int COUNT = MODIFIERS.length;

    private void init(int index, float unitLeft, float unitTop) {
        animator = null;
        bitmap = null;
        modifier = MODIFIERS[index];
        modifier.init(this, unitLeft, unitTop);
    }

    @Override
    public void update() {
        super.update();
        modifier.update(this);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void onRecycle() {
        super.onRecycle();
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}
