package kr.ac.tukorea.ge.spgp2023.taptu.game;

import android.app.GameState;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.taptu.R;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;

public class NoteSprite extends Sprite implements IRecyclable {
    private static final float NOTE_WIDTH = 1.0f / 7.0f;
    private static final float NOTE_RATIO = 1.0f / 3.0f;
    private PlayScene scene;


    public static NoteSprite get(Song.Note note) {
        NoteSprite ns = (NoteSprite) RecycleBin.get(NoteSprite.class);
        if (ns == null) {
            ns = new NoteSprite();
        }
        ns.init(note);
        return ns;
    }

    private void init(Song.Note note) {
        float x = (0.5f - 2 * NOTE_WIDTH) * Metrics.game_width;
        x += NOTE_WIDTH * Metrics.game_width * note.lane;
        moveTo(x, -note.msec / 1000.0f); // y coordinate 1.0 = 1.0sec
        scene = (PlayScene)BaseScene.getTopScene();
    }

    private NoteSprite() {
        bitmap = BitmapPool.get(R.mipmap.note_1);
        width = NOTE_WIDTH * Metrics.game_width;
        height = width * NOTE_RATIO;
    }

    @Override
    public void update() {
        super.update();
        y += scene.getSpeed() * BaseScene.frameTime;
        if (y > Metrics.game_height + height) {
            scene.remove(PlayScene.Layer.note, this);
            return;
        }
        moveTo(x, y);
    }

    @Override
    public void onRecycle() {}
}
