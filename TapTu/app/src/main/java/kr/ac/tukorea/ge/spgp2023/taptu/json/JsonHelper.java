package kr.ac.tukorea.ge.spgp2023.taptu.json;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class JsonHelper {
    private static final String TAG = JsonHelper.class.getSimpleName();

    public static boolean readProperty(Object object, String name, JsonReader reader) throws IOException {
        try {
            Field field = object.getClass().getField(name);
            Class<?> type = field.getType();
            if (type == int.class) {
                int value = reader.nextInt();
                Log.d(TAG, "Int " + name + ": " + value + " - " + object);
                field.setInt(object, value);
            } else if (type == String.class) {
                String value = reader.nextString();
                Log.d(TAG, "String " + name + ": " + value + " - " + object);
                field.set(object, value);
            } else if (type == int[].class) {
                int[] value = readIntArray(reader);
                Log.d(TAG, "int[] " + name + ": [" + value.length + "] - " + object);
                field.set(object, value);
            } else {
                Log.e(TAG, "Not handling " + name + ". type: " + type + " - " + object);
                return false;
            }
            return true;
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "No field \"" + name + "\" in " + object);
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
    private static int[] readIntArray(JsonReader reader) throws IOException {
        ArrayList<Integer> integers = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            int value = reader.nextInt();
            integers.add(value);
        }
        reader.endArray();

        int[] ints = new int[integers.size()];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = integers.get(i);
        }

        return ints;
    }
}
