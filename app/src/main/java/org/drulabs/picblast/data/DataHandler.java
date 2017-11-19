package org.drulabs.picblast.data;

import org.drulabs.picblast.data.models.ImgurResp;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.data.models.PixyPic;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

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

    Observable<List<PixyAlbum>> fetchAlbums();

    Observable<PixyAlbum> fetchAlbums(String searchText);

    Single<Void> createAlbum(String service, String name, String description, String privacy);

    Single<PixyAlbum> getAlbum(String provider, String albumId);

    Single<ImgurResp> uploadImage(String provider, String albumId, String filePath, String title,
                                  String description, String name);

    void fetchAlbums(Callback<List<PixyAlbum>> callback);

    Observable<List<PixyPic>> getAlbumImages(String provider, String albumId);

    interface Callback<T> {
        void onResult(T result);

        void onError(int code, String message);
    }
}
