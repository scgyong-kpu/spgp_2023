package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2023.framework.scene.BaseScene;
import kr.ac.tukorea.ge.spgp2023.framework.view.Metrics;

public class MapLoader implements IGameObject {
    private static final String TAG = MapLoader.class.getSimpleName();
    private Random random = new Random();
    private float x;
    private int index, mapLength;
    private int columns;
    private ArrayList<String> lines = new ArrayList<>();

    public MapLoader(Context context, int stage) {
        loadStage(context, stage);
    }

    public static final int STAGE_HEIGHT = 9;

    private void loadStage(Context context, int stage) {
        AssetManager assets = context.getAssets();
        try {
            String file = String.format("stage_%02d.txt", stage);
            InputStream is = assets.open(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            lines.add(line);
            columns = line.indexOf('|');
            if (columns <= 0) return;

            while (true) {
                line = reader.readLine();
                if (line == null) break;
                lines.add(line);
            }

            int pages = lines.size() / STAGE_HEIGHT;
            int lastCol = lines.get(lines.size() - 1).length();
            mapLength = (pages - 1) * columns + lastCol;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
        x -= MapObject.SPEED * BaseScene.frameTime;
        float left = x + index;
        while (left < Metrics.game_width) {
            createColumn(left);
            index++;
            left += 1.0f;
        }
    }

    private void createColumn(float left) {
        for (int row = 0; row < STAGE_HEIGHT; row++) {
            int tile = getAt(index, row);
            createObject(tile, left, row);
        }
    }

    private void createObject(int tile, float left, int top) {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        MapObject mobj = null;
        if ('0' <= tile && tile <= '9') {
            mobj = JellyItem.get(tile - '0', left, top);
        } else if (tile == '@') {
            mobj = JellyItem.get(26, left, top);
        } else if ('O' <= tile && tile <= 'Q') {
            mobj = Platform.get(tile - 'O', left, top);
        } else switch (tile) {
            case 'X': case 'Y': case 'Z':
                mobj = Obstacle.get(tile - 'X', left, top);
                break;
            case 'W':
                mobj = Obstacle.get(3, left, top);
                break;
        }

        if (mobj != null) {
            scene.add(mobj.layer, mobj);
        }
    }

    private int getAt(int col, int row) {
        try {
            int lineIndex = col / columns * STAGE_HEIGHT + row;
            String line = lines.get(lineIndex);
            return line.charAt(col % columns);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
