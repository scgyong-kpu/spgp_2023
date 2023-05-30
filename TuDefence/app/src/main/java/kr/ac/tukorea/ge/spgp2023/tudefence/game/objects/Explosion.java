package kr.ac.tukorea.ge.spgp2023.tudefence.game.objects;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2023.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2023.tudefence.R;
import kr.ac.tukorea.ge.spgp2023.tudefence.game.scene.MainScene;

public class Explosion extends AnimSprite implements IRecyclable {
    public static Explosion get(float x, float y, float radius) {
        Explosion ex = (Explosion) RecycleBin.get(Explosion.class);
        if (ex == null) {
            ex = new Explosion();
        }
        ex.init(x, y, radius);
        return ex;
    }
    private Explosion() {
        super(R.mipmap.explosion, 0, 0, 0, 0, 20, 0);
    }
    private void init(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        float size = 2 * radius;
        setSize(size, size);
        createdOn = System.currentTimeMillis();
    }

    @Override
    public void update() {
        super.update();
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        if (time > 19.0f/20.0f) {
            BaseScene.getTopScene().remove(MainScene.Layer.explosion, this);
        }
    }
    @Override
    public void onRecycle() {

    }
}
