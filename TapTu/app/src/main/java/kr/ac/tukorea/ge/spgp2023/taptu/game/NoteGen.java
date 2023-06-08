package kr.ac.tukorea.ge.spgp2023.taptu.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;

public class NoteGen implements IGameObject {
    private final PlayScene scene;
    private int noteIndex;

    public NoteGen(PlayScene scene) {
        this.scene = scene;
        this.noteIndex = 0;
    }
    @Override
    public void update() {
        if (noteIndex >= scene.song.notes.size()) return;
        Song.Note note = scene.song.notes.get(noteIndex);
        float speed = scene.getSpeed();
        int msecDiff = note.msec - scene.getCurrentTime();
        if (!NoteSprite.shouldAppear(speed, msecDiff)) {
            return;
        }
        NoteSprite ns = NoteSprite.get(note);
        scene.add(PlayScene.Layer.note, ns);
        noteIndex++;
    }

    @Override
    public void draw(Canvas canvas) {}
}
