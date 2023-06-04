package kr.ac.tukorea.ge.spgp2023.taptu.game;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.taptu.R;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;

public class PlayScene extends BaseScene {
    private final Song song;

    public PlayScene(int index) {
        song = Song.get(index);
    }

    public enum Layer {
        bg, COUNT
    }

    @Override
    protected void onStart() {
        initLayers(Layer.COUNT);
        float w = Metrics.game_width, h = Metrics.game_height;
        add(Layer.bg, new Sprite(R.mipmap.bg, w/2, h/2, w, h));
        song.play();
    }

    @Override
    protected void onEnd() {
        song.stop();
    }
}
