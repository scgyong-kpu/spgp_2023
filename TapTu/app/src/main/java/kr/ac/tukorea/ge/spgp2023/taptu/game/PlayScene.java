package kr.ac.tukorea.ge.spgp2023.taptu.game;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.taptu.R;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;

public class PlayScene extends BaseScene {
    private final Song song;
    private int timeMsec;
    private float speed;

    public PlayScene(int index) {
        song = Song.get(index);
        song.loadNotes(GameView.view.getContext().getAssets());
        speed = 2.0f;
    }

    public float getNoteYCoord(Song.Note note) {
        float y = NoteSprite.LINE_Y * Metrics.game_height;
        return y;
    }

    public enum Layer {
        bg, note, COUNT
    }

    @Override
    protected void onStart() {
        initLayers(Layer.COUNT);
        float w = Metrics.game_width, h = Metrics.game_height;
        add(Layer.bg, new Sprite(R.mipmap.bg, w/2, h/2, w, h));
        for (Song.Note note: song.notes) {
            // temporary
            add(Layer.note, NoteSprite.get(note));
        }
        song.play();
    }

    @Override
    protected void onEnd() {
        song.stop();
    }

    @Override
    public void update(long nanos) {
        timeMsec = song.getCurrentTime();
        super.update(nanos);
    }

    public int getCurrentTime() {
        return timeMsec;
    }
    public float getSpeed() {
        return speed;
    }
}
