package kr.ac.tukorea.ge.spgp2023.taptu.game;

import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2023.taptu.R;

public class Pret extends Sprite {
    public Pret(int index) {
        bitmap = BitmapPool.get(R.mipmap.trans_50p);
        float width = NoteSprite.NOTE_WIDTH * Metrics.game_width;
        float center = Metrics.game_width / 2;
        float x = center + (index - 2.5f) * width;
        //Log.d("Lane", "index="+index+" x=" + x);
        dstRect.set(x, 0, x + width, Metrics.game_height);
    }
}
