package kr.ac.tukorea.ge.spgp2023.taptu.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;

public class CircleNoteSprite extends NoteSprite {
    public static CircleNoteSprite getCircleNoteSprite(Song.Note note) {
        CircleNoteSprite ns = (CircleNoteSprite) RecycleBin.get(CircleNoteSprite.class);
        if (ns == null) {
            ns = new CircleNoteSprite();
        }
        ns.init(note);
        return ns;
    }

    @Override
    protected void init(Song.Note note) {
        this.note = note;
        scene = (PlayScene) BaseScene.getTopScene();
        float x = (0.5f - 2 * NOTE_WIDTH) * Metrics.game_width;
        x += NOTE_WIDTH * Metrics.game_width * note.lane;
        float y = 3 + (note.msec % 10000) / 1000.0f;
        moveTo(x, y);
    }

    @Override
    public void update() {
        int msecDiff = scene.getCurrentTime() - note.msec;
        if (msecDiff > 1000) {
            scene.remove(PlayScene.Layer.note, this);
            scene.call.set(Call.Type.miss);
            return;
        }
    }

    protected static Paint linePaint, circlePaint;
    @Override
    public void draw(Canvas canvas) {
        int msecDiff = note.msec - scene.getCurrentTime();
        float radius = (msecDiff + 1000) / 2000.0f;
        if (linePaint == null) {
            linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(0.02f);
            linePaint.setColor(Color.BLUE);
            circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setColor(0x7fBF0000);
        }
        canvas.drawCircle(x, y, radius, linePaint);
        canvas.drawCircle(x, y, 0.5f, circlePaint);
    }
}
