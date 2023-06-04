package kr.ac.tukorea.ge.spgp2023.taptu.game;

import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;

public class PlayScene extends BaseScene {
    private final Song song;

    public PlayScene(int index) {
        song = Song.get(index);
    }

    @Override
    protected void onStart() {
        song.play();
    }

    @Override
    protected void onEnd() {
        song.stop();
    }
}
