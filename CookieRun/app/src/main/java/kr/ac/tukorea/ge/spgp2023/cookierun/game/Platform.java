package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;

public class Platform extends Sprite {
    public enum Type {
        T_10x2, T_2x2, T_3x1;
        int resId() { return resIds[this.ordinal()]; }
        int width() { return widths[this.ordinal()]; }
        int height() { return heights[this.ordinal()]; }
        static int[] resIds = {
                R.mipmap.cookierun_platform_480x48,
                R.mipmap.cookierun_platform_124x120,
                R.mipmap.cookierun_platform_120x40,
        };
        static int[] widths = { 10, 2, 3 };
        static int[] heights = { 2, 2, 1 };
    }
    public Platform(Type type, float left, float top) {
        setBitmapResource(type.resId());
        width = type.width();
        height = type.height();
        // Platform 은 x,y 를 사용하지 않고 dstRect 만을 사용하도록 한다.
        dstRect.set(left, top, left + width, top + height);
    }
}
