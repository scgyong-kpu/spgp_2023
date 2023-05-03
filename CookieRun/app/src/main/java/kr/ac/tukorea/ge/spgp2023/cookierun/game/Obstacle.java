package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.util.Log;

import kr.ac.tukorea.ge.spgp2023.cookierun.R;
import kr.ac.tukorea.ge.spgp2023.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2023.framework.scene.RecycleBin;

public class Obstacle extends MapObject {
    protected Obstacle() {
        super(MainScene.Layer.obstacle);
    }
    public static Obstacle get(int index, float unitLeft, float unitTop) {
        Obstacle obs = (Obstacle) RecycleBin.get(Obstacle.class);
        if (obs == null) {
            obs = new Obstacle();
        }
        obs.init(index, unitLeft, unitTop);
        return obs;
    }

    private static int[][] BITMAP_ID_ARRAYS = {
            new int[] {
                    R.mipmap.epn01_tm01_jp1a,
            },
            new int[] {
                    R.mipmap.epn01_tm01_jp1up_01,
                    R.mipmap.epn01_tm01_jp1up_02,
                    R.mipmap.epn01_tm01_jp1up_03,
                    R.mipmap.epn01_tm01_jp1up_04,
            },
            new int[] {
                    R.mipmap.epn01_tm01_jp2up_01,
                    R.mipmap.epn01_tm01_jp2up_02,
                    R.mipmap.epn01_tm01_jp2up_03,
                    R.mipmap.epn01_tm01_jp2up_04,
                    R.mipmap.epn01_tm01_jp2up_05,
            },
            new int[] {
                    R.mipmap.epn01_tm01_sda,
            }
    };

    private static float sizes[][] = {
            {63f,99f}, {81f,131f}, {87f,222f}, {86f,482f},
    };

    public static final int COUNT = BITMAP_ID_ARRAYS.length;

    private void init(int index, float unitLeft, float unitTop) {
        bitmap = BitmapPool.get(BITMAP_ID_ARRAYS[index][BITMAP_ID_ARRAYS[index].length - 1]);
        float[] size = sizes[index];
        float width = size[0] / 80;
        float height = size[1] / 80;
        dstRect.set(unitLeft, unitTop + 1 - height, unitLeft + width, unitTop + 1);
    }
}
