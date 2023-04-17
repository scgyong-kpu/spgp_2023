package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.objects.Sprite;

public class Platform extends Sprite {
    public enum Type {
        T_10x2, T_2x2, T_3x1
    }
    protected static int[] resIds = {
            R.mipmap.cookierun_platform_480x48,
            R.mipmap.cookierun_platform_124x120,
            R.mipmap.cookierun_platform_120x40,
    };
    protected static int[] widths = { 10, 2, 3 };
    protected static int[] heights = { 2, 2, 1 };
    public Platform(Type type, float left, float top) {
        int ord = type.ordinal();
        setBitmapResource(resIds[ord]);
        width = widths[ord];
        height = heights[ord];
        dstRect.set(left, top, left + width, top + height);
    }
}
