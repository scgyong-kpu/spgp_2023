package kr.ac.tukorea.ge.spgp2023.taptu.json;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;

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
}
