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
    static final float NOTE_WIDTH = 1.0f / 7.0f;
    static final float NOTE_RATIO = 1.0f / 3.0f;
    public static final float LINE_Y = 7.0f / 8.0f;
    protected PlayScene scene;
    Song.Note note;


    public static NoteSprite get(Song.Note note) {
        return CircleNoteSprite.getCircleNoteSprite(note);
//        NoteSprite ns = (NoteSprite) RecycleBin.get(NoteSprite.class);
//        if (ns == null) {
//            ns = new NoteSprite();
//        }
//        ns.init(note);
//        return ns;
    }

    protected void init(Song.Note note) {
        this.note = note;
        float x = (0.5f - 2 * NOTE_WIDTH) * Metrics.game_width;
        x += NOTE_WIDTH * Metrics.game_width * note.lane;
        scene = (PlayScene)BaseScene.getTopScene();
        moveTo(x, -scene.getSpeed() * note.msec / 1000.0f);
    }

    protected NoteSprite() {
        bitmap = BitmapPool.get(R.mipmap.note_1);
        width = NOTE_WIDTH * Metrics.game_width;
        height = width * NOTE_RATIO;
    }

    @Override
    public void update() {
        super.update();

        int msecDiff = note.msec - scene.getCurrentTime();
        y = Metrics.game_height * LINE_Y - msecDiff * scene.getSpeed() / 1000.0f;
        fixDstRect();
        if (dstRect.top > Metrics.game_height) {
            scene.remove(PlayScene.Layer.note, this);
            scene.call.set(Call.Type.miss);
            return;
        }
    }

    public static boolean shouldAppear(float speed, int msecDiff) {
        //return msecDiff / 1000.0f < Metrics.game_height / speed;
        return msecDiff < 16000 / speed;
    }

    @Override
    public void onRecycle() {}
}
