package kr.ac.tukorea.ge.spgp2023.dragonflight.game;

import kr.ac.tukorea.ge.spgp2023.dragonflight.framework.BaseScene;

public class MainScene extends BaseScene {
    private final Fighter fighter;

    public MainScene() {
        fighter = new Fighter();
        add(fighter);
    }
}
