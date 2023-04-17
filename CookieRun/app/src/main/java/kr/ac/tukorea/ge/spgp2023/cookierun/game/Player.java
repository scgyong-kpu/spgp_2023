package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.objects.AnimSprite;

public class Player extends AnimSprite {
    public Player() {
        super(R.mipmap.cookie_player_jump, 2.0f, 7.0f, 2.0f, 2.0f, 8, 2);
    }

    protected enum State {
        running, jump
    }
    protected State state = State.jump;
    public void jump() {
        if (state == State.running) {
            setAnimationResource(R.mipmap.cookie_player_jump, 8, 2);
            state = State.jump;
        } else {
            setAnimationResource(R.mipmap.cookie_player_run, 8, 4);
            state = State.running;
        }
    }

}
