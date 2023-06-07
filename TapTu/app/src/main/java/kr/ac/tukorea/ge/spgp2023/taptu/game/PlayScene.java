package kr.ac.tukorea.ge.spgp2023.taptu.game;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.taptu.R;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;

public class PlayScene extends BaseScene implements Pret.Listener {
    private static final String TAG = PlayScene.class.getSimpleName();
    final Song song;
    private int timeMsec;
    private float speed;

    public PlayScene(int index) {
        song = Song.get(index);
        song.loadNotes(GameView.view.getContext().getAssets());
        speed = 2.0f;
    }

    public enum Layer {
        bg, pret, note, controller, COUNT
    }

    @Override
    protected void onStart() {
        initLayers(Layer.COUNT);
        float w = Metrics.game_width, h = Metrics.game_height;
        add(Layer.bg, new Sprite(R.mipmap.bg, w/2, h/2, w, h));
        for (int i = 0; i < 5; i++) {
            add(Layer.pret, new Pret(i, this));
        }
        add(Layer.controller, new NoteGen(this));
        song.play();
    }

    @Override
    public void onPret(int lane, boolean pressed) {
//        Log.d(TAG, "onPret: lane=" + lane + " pressed=" + pressed);
        if (!pressed) return;
        NoteSprite ns = findNearestNote(lane);
        if (ns == null) return;
        int diff = ns.note.msec - timeMsec;
//        Log.d(TAG, " onPret diff=" + diff);
        if (diff < 0) diff = -diff;
        Call.Type type = Call.Type.miss;
        if (diff < 100) {
            type = Call.Type.perfect;
        } else if (diff < 200) {
            type = Call.Type.great;
        } else if (diff < 300) {
            type = Call.Type.good;
        } else if (diff < 500) {
            type = Call.Type.bad;
        }
        Log.d(TAG, "Call: [" + type + "] " + diff);
        remove(Layer.note, ns);
    }

    private NoteSprite findNearestNote(int lane) {
        float dist = Float.MAX_VALUE;
        NoteSprite nearest = null;
        ArrayList<IGameObject> notes = getObjectsAt(Layer.note);
        for (IGameObject go : notes) {
            if (!(go instanceof NoteSprite)) continue;
            NoteSprite ns = (NoteSprite) go;
            if (ns.note.lane != lane) continue;
            int diff = ns.note.msec - timeMsec;
            if (diff < 0) diff = -diff;
            if (dist > diff) {
//                Log.d(TAG, "= dist=" + dist + " diff=" + diff);
                dist = diff;
                nearest = ns;
            }
        }
        return (dist < 1000) ? nearest : null;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float gx = Metrics.toGameX(event.getX());
        float gy = Metrics.toGameY(event.getY());
        ArrayList<IGameObject> prets = getObjectsAt(Layer.pret);
        for (IGameObject gobj: prets) {
            Pret pret = (Pret) gobj;
            if (pret.onTouchEvent(event.getAction(), gx, gy)) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public int getCurrentTime() {
        return timeMsec;
    }
    public float getSpeed() {
        return speed;
    }
}
