package kr.ac.tukorea.ge.spgp2023.framework.util;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IBoxCollidable;

public class CollisionHelper {
    public static boolean collides(IBoxCollidable obj1, IBoxCollidable obj2) {
        RectF r1 = obj1.getCollisionRect();
        RectF r2 = obj2.getCollisionRect();

        if (r1.left > r2.right) return false;
        if (r1.top > r2.bottom) return false;
        if (r1.right < r2.left) return false;
        if (r1.bottom < r2.top) return false;

        return true;
    }
}
