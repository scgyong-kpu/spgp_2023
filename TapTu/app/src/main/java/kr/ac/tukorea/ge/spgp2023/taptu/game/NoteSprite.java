package kr.ac.tukorea.ge.spgp2023.taptu.game;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2023.taptu.R;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;

public class NoteSprite extends Sprite implements IRecyclable {
    public static NoteSprite get(Song.Note note, int msec) {
        NoteSprite ns = (NoteSprite) RecycleBin.get(NoteSprite.class);
        if (ns == null) {
            ns = new NoteSprite();
        }
        ns.init(note, msec);
        return ns;
    }

    private void init(Song.Note note, int msec) {
    }

    private NoteSprite() {
        bitmap = BitmapPool.get(R.mipmap.note_1);
    }
    @Override
    public void onRecycle() {}
}
