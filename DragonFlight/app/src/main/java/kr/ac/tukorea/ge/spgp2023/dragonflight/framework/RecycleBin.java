package kr.ac.tukorea.ge.spgp2023.dragonflight.framework;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleBin {
    private static final String TAG = RecycleBin.class.getSimpleName();
    protected static HashMap<Class, ArrayList<IRecyclable>> recycleBin = new HashMap<>();

    public static void collect(IRecyclable object) {
        Class clazz = object.getClass();
        ArrayList<IRecyclable> bin = recycleBin.get(clazz);
        if (bin == null) {
            bin = new ArrayList<>();
            recycleBin.put(clazz, bin);
        }
        object.onRecycle(); // 객체가 재활용통에 들어가기 전에 정리해야 할 것이 있다면 여기서 한다
        bin.add(object);
        Log.d(TAG, "collect(): " + clazz.getSimpleName() + " : " + bin.size() + " objects");
    }

    public static IRecyclable get(Class clazz) {
        ArrayList<IRecyclable> bin = recycleBin.get(clazz);
        if (bin == null) return null;
        if (bin.size() == 0) return null;
        Log.d(TAG, "get(): " + clazz.getSimpleName() + " : " + (bin.size() - 1) + " objects");
        return bin.remove(0);
    }}
