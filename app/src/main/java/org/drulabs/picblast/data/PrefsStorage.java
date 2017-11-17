package org.drulabs.picblast.data;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */
public class PrefsStorage {

    private static final String PREFS_NAME = "AppPrefs";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    private static PrefsStorage prefsStorage;

    private PrefsStorage(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public static PrefsStorage getInstance(Context context) {
        synchronized (PrefsStorage.class) {
            if (prefsStorage == null) {
                synchronized (PrefsStorage.class) {
                    prefsStorage = new PrefsStorage(context);
                }
            }
        }
        return prefsStorage;
    }

    void saveString(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    String getString(String key) {
        return prefs.getString(key, null);
    }

    void saveFloat(String key, float value) {
        prefsEditor.putFloat(key, value);
        prefsEditor.apply();
    }

    float getFloat(String key) {
        return prefs.getFloat(key, 0.0f);
    }

    void saveBoolean(String key, boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    void saveInt(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    int getInt(String key) {
        return prefs.getInt(key, -1);
    }

}
