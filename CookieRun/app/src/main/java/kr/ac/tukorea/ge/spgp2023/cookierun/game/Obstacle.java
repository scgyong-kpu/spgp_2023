package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.graphics.RectF;
import android.util.Log;

import java.util.AbstractList;

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
            new Modifier(1, 482/86f, R.mipmap.epn01_tm01_sda),
    } ;
    public static final int COUNT = MODIFIERS.length;

    private void init(int index, float unitLeft, float unitTop) {
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
}
