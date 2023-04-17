package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.objects.AnimSprite;

public class Player extends AnimSprite {
    public Player() {
        super(R.mipmap.cookie_player_sheet, 2.0f, 6.0f, 2.0f, 2.0f, 8, 1);
    }

    protected enum State {
        running, jump, doubleJump, falling, COUNT
    }
//    protected Rect[] srcRects
    protected static Rect[][] srcRects = {
            makeRects(100, 101, 102, 103), // State.running
            makeRects(7, 8),               // State.jump
            makeRects(1, 2, 3, 4),         // State.doubleJump
            makeRects(0),                  // State.falling
    };
    protected static Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = 72 + (idx % 100) * 272;
            int t = 132 + (idx / 100) * 272;
            rects[i] = new Rect(l, t, l + 140, t + 140);
        }
        return rects;
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
        int ord = state.ordinal() + 1;
        if (ord == State.COUNT.ordinal()) {
            ord = 0;
        }
        state = State.values()[ord]; // int 로부터 enum 만들기
    }
}
