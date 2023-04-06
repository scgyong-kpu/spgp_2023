package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.Sprite;

public class Enemy extends Sprite {
    public Enemy(int index, int level) {
        super(R.mipmap.f_01_01, 0.9f * (2 * index + 1), 3.0f, 1.5f, 1.5f);
    }
}
