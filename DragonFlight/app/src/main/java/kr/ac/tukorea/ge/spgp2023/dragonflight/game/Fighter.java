package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.Sprite;

public class Fighter extends Sprite {
    private static final float FIGHTER_X = 4.5f;
    private static final float FIGHTER_Y = 14.8f;
    private static final float FIGHTER_SIZE = 1.75f;

    public Fighter() {
        super(R.mipmap.fighter, FIGHTER_X, FIGHTER_Y, FIGHTER_SIZE, FIGHTER_SIZE);
    }
}
