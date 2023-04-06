package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import kr.ac.tukorea.ge.spgp2023.dragonflight.R;
import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.Sprite;

public class Enemy extends Sprite {

    private static final int[] resIds = {
            R.mipmap.f_01_01, R.mipmap.f_02_01, R.mipmap.f_03_01, R.mipmap.f_04_01, R.mipmap.f_05_01,
            R.mipmap.f_06_01, R.mipmap.f_07_01, R.mipmap.f_08_01, R.mipmap.f_09_01, R.mipmap.f_10_01,
    };
    public Enemy(int index, int level) {
        super(resIds[level], 0.9f * (2 * index + 1), 3.0f, 1.8f, 1.8f);
    }
}
