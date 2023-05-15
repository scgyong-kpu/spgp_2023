package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2023.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class Player extends AnimSprite implements IBoxCollidable {
    private float jumpSpeed;
    private static final float JUMP_POWER = 9.0f;
    private static final float GRAVITY = 17.0f;
    private RectF collisionRect = new RectF();
    protected Obstacle obstacle;
    private int imageSize = 0;

    public Player(int cookieId) {
        super(2.0f, 3.0f, 3.86f, 3.86f, 8);
        fixDstRect();
        fixCollisionRect();
        loadAssetImage(cookieId);
    }

    private void loadAssetImage(int cookieId) {
        AssetManager assets = GameView.view.getContext().getAssets();
        String filename = "cookies/" + cookieId + "_sheet.png";
        Log.d("KKK", filename);
        try {
            InputStream is = assets.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            imageSize = (bitmap.getWidth() - 2) / 11 - 2;
            makeSourceRects();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected enum State {
        running, jump, doubleJump, falling, slide, hurt, COUNT
    }
//    protected Rect[] srcRects
    protected Rect[][] srcRects;
    private void makeSourceRects() {
        srcRects = new Rect[][] {
                makeRects(100, 101, 102, 103), // State.running
                makeRects(7, 8),               // State.jump
                makeRects(1, 2, 3, 4),         // State.doubleJump
                makeRects(0),                  // State.falling
                makeRects(9, 10),              // State.slide
                makeRects(503, 504),           // State.hurt
        };
    };
    protected static float[][] edgeInsets = {
            { 1.20f, 1.95f, 1.10f, 0.00f }, // State.running
            { 1.20f, 2.25f, 1.10f, 0.00f }, // State.jump
            { 1.20f, 2.20f, 1.10f, 0.00f }, // State.doubleJump
            { 1.20f, 1.80f, 1.20f, 0.00f }, // State.falling
            { 0.80f, 2.90f, 0.80f, 0.00f }, // slide
            { 1.20f, 1.95f, 1.10f, 0.00f }, // State.hurt
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = 2 + (idx % 100) * (imageSize + 2);
            int t = 2 + (idx / 100) * (imageSize + 2);
            rects[i] = new Rect(l, t, l + imageSize, t + imageSize);
        }
        return rects;
    }

    @Override
    public void update() {
        switch (state) {
        case jump:
        case doubleJump:
        case falling:
            float dy = jumpSpeed * BaseScene.frameTime;
            jumpSpeed += GRAVITY * BaseScene.frameTime;
            if (jumpSpeed >= 0) { // 낙하하고 있다면 발밑에 땅이 있는지 확인한다
                float foot = collisionRect.bottom;
                float floor = findNearestPlatformTop(foot);
                if (foot + dy >= floor) {
                    dy = floor - foot;
                    state = State.running;
                }
            }
            y += dy;
            //fixDstRect();
            dstRect.offset(0, dy);
            fixCollisionRect();
            break;
        case running:
        case slide:
            float foot = collisionRect.bottom;
            float floor = findNearestPlatformTop(foot);
            if (foot < floor) {
                state = State.falling;
                jumpSpeed = 0;
            }
            break;
        case hurt:
            if (!CollisionHelper.collides(this, obstacle)) {
                state = State.running;
                obstacle = null;
            }
            break;
        }
    }

    private float findNearestPlatformTop(float foot) {
        Platform platform = findNearestPlatform(foot);
        if (platform == null) return Metrics.game_height;
        return platform.getCollisionRect().top;
    }

    private Platform findNearestPlatform(float foot) {
        Platform nearest = null;
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<IGameObject> platforms = scene.getObjectsAt(MainScene.Layer.platform);
        float top = Metrics.game_height;
        for (IGameObject obj: platforms) {
            Platform platform = (Platform) obj;
            RectF rect = platform.getCollisionRect();
            if (rect.left > x || x > rect.right) {
                continue;
            }
            //Log.d(TAG, "foot:" + foot + " platform: " + rect);
            if (rect.top < foot) {
                continue;
            }
            if (top > rect.top) {
                top = rect.top;
                nearest = platform;
            }
            //Log.d(TAG, "top=" + top + " gotcha:" + platform);
        }
        return nearest;
    }

    private void fixCollisionRect() {
        float[] insets = edgeInsets[state.ordinal()];
        collisionRect.set(
                dstRect.left + insets[0],
                dstRect.top + insets[1],
                dstRect.right - insets[2],
                dstRect.bottom -  insets[3]);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        Rect[] rects = srcRects[state.ordinal()];
        int frameIndex = Math.round(time * fps) % rects.length;
        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);
    }

    protected State state = State.running;
    public void jump() {
        if (state == State.running) {
            state = State.jump;
            jumpSpeed = -JUMP_POWER;
        } else if (state == State.jump) {
            jumpSpeed = -JUMP_POWER;
            state = State.doubleJump;
        }
    }
    public void fall() {
        if (state != State.running) return;
        float foot = collisionRect.bottom;
        Platform platform = findNearestPlatform(foot);
        if (platform == null) return;
        if (!platform.canPass()) return;
        state = State.falling;
        dstRect.offset(0, 0.001f);
        collisionRect.offset(0, 0.001f);
        jumpSpeed = 0;
    }
    public void slide(boolean startsSlide) {
        if (state == State.running && startsSlide) {
            state = State.slide;
            fixCollisionRect();
            return;
        }
        if (state == State.slide && !startsSlide) {
            state = State.running;
            fixCollisionRect();
            return;
        }
    }

    public void hurt(Obstacle obstacle) {
        if (state == State.hurt) return;
        state = State.hurt;
        fixCollisionRect();
        this.obstacle = obstacle;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
