package kr.ac.tukorea.ge.spgp2023.cookierun.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.JsonReader;

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

    public MapLoader(Context context) {
        loadStage(context, 1);
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
        if ('1' <= tile && tile <= '9') {
            JellyItem item = JellyItem.get(tile - 1, left, top);
            scene.add(MainScene.Layer.item, item);
            return;
        }
        if ('O' <= tile && tile <= 'Q') {
            Platform.Type ptype = Platform.Type.values()[tile - 'O'];
            Platform platform = Platform.get(ptype, left, top);
            scene.add(MainScene.Layer.platform, platform);
            return;
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
