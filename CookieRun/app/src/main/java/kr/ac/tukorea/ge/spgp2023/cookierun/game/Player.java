package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.objects.AnimSprite;

public class Player extends AnimSprite {
    public Player() {
        super(R.mipmap.cookie_player_sheet, 2.0f, 7.0f, 2.0f, 2.0f, 8, 1);
    }

    protected enum State {
        running, jump
    }
//    protected Rect[] srcRects
    protected static Rect[][] srcRects = {
            new Rect[] {
                    new Rect(72 + 0 * 272, 404, 72+140 + 0 * 272, 404+140),
                    new Rect(72 + 1 * 272, 404, 72+140 + 1 * 272, 404+140),
                    new Rect(72 + 2 * 272, 404, 72+140 + 2 * 272, 404+140),
                    new Rect(72 + 3 * 272, 404, 72+140 + 3 * 272, 404+140)
            },
            new Rect[] {
                    new Rect(72 + 7 * 272, 132, 72+140 + 7 * 272, 132+140),
                    new Rect(72 + 8 * 272, 132, 72+140 + 8 * 272, 132+140),
            },
    };

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
        } else {
            state = State.running;
        }
    }

}
