package org.drulabs.picblast.data;

import org.drulabs.picblast.data.models.ImgurAlbum;
import org.drulabs.picblast.data.models.ImgurUserAlbums;
import org.drulabs.picblast.data.models.PixyAlbum;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */

public interface DataHandler {

    void saveString(String key, String value);

    String getString(String key);

    void saveBoolean(String key, boolean value);

    boolean getBoolean(String key);

    void saveInt(String key, int value);

    int getInt(String key);

    void saveDouble(String key, double value);

    double getDouble(String key);

    void fetchAlbums(Callback<List<ImgurAlbum>> callback);

    Observable<List<PixyAlbum>> fetchAlbums(boolean fromCache);

    void fetchAlbums(boolean fromCache, Callback<List<ImgurAlbum>> callback);

    void fetchAlbums(String searchText, Callback<List<ImgurAlbum>> callback);

    interface Callback<T> {
        void onResult(T result);

        void onError(int code, String message);
    }
}
