package kr.ac.tukorea.ge.spgp2023.taptu.game;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.taptu.R;
import kr.ac.tukorea.ge.spgp2023.taptu.data.Song;

public class PlayScene extends BaseScene {
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
            add(Layer.pret, new Pret(i));
        }
        add(Layer.controller, new NoteGen(this));
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
