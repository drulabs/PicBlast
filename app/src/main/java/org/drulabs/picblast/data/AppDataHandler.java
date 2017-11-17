package org.drulabs.picblast.data;

import org.drulabs.picblast.data.models.ImgurAlbum;
import org.drulabs.picblast.data.models.ImgurUserAlbums;
import org.drulabs.picblast.data.models.ModelAdapter;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.data.models.PixyAlbum_;
import org.drulabs.picblast.data.models.PixyPic;
import org.drulabs.picblast.data.network.ImgurApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */

public class AppDataHandler implements DataHandler {

    private PrefsStorage mPrefsStorage;
    private ImgurApi mImgurApi;
    private Box<PixyAlbum> mAlbumBox;
    private Box<PixyPic> mPixBox;

    @Inject
    public AppDataHandler(PrefsStorage prefsStorage, ImgurApi imgurApi, Box<PixyAlbum> albumBox,
                          Box<PixyPic> pixBox) {
        this.mPrefsStorage = prefsStorage;
        this.mImgurApi = imgurApi;
        this.mAlbumBox = albumBox;
        this.mPixBox = pixBox;
    }

    @Override
    public void saveString(String key, String value) {
        mPrefsStorage.saveString(key, value);
    }

    @Override
    public String getString(String key) {
        return mPrefsStorage.getString(key);
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        mPrefsStorage.saveBoolean(key, value);
    }

    @Override
    public boolean getBoolean(String key) {
        return mPrefsStorage.getBoolean(key);
    }

    @Override
    public void saveInt(String key, int value) {
        mPrefsStorage.saveInt(key, value);
    }

    @Override
    public int getInt(String key) {
        return mPrefsStorage.getInt(key);
    }

    @Override
    public void saveDouble(String key, double value) {
        mPrefsStorage.saveFloat(key, (float) value);
    }

    @Override
    public double getDouble(String key) {
        return mPrefsStorage.getFloat(key);
    }

    @Override
    public void fetchAlbums(Callback<List<ImgurAlbum>> callback) {
        // TODO("Write code to fetch albums from local db");
    }

    @Override
    public Observable<List<PixyAlbum>> fetchAlbums(boolean fromCache) {
        if (!fromCache) {
            String authHeader = "Bearer " + mPrefsStorage.getString("imgur-access-token");
            Observable<ImgurUserAlbums> imgurAlbumsObservable = mImgurApi.fetchUserAlbums
                    (authHeader, "me");

            return imgurAlbumsObservable
                    .map(imgurUserAlbums -> imgurUserAlbums.getAlbumList())
                    .compose(upstream -> upstream.subscribeOn(Schedulers.io())
                            .map(imgurAlbums -> {
                                List<PixyAlbum> pixyAlbums = new ArrayList<>();
                                for (ImgurAlbum singleImgurAlbum : imgurAlbums) {
                                    PixyAlbum soloPixyAlbum = ModelAdapter.fromImgurToPixy
                                            (singleImgurAlbum);
                                    pixyAlbums.add(soloPixyAlbum);
                                }
                                mAlbumBox.remove(pixyAlbums);
                                mAlbumBox.put(pixyAlbums);

                                return pixyAlbums;
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                    );
        } else {
            // Read from local db and return
            return Observable.create((ObservableOnSubscribe<List<PixyAlbum>>) subscriber -> {
                try {
                    List<PixyAlbum> pixyAlbums = mAlbumBox.query().notNull(PixyAlbum_.id).build()
                            .find();
                    subscriber.onNext(pixyAlbums);
                    subscriber.onComplete();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    subscriber.onError(e1);
                }
            });
        }
    }

    @Override
    public void fetchAlbums(boolean fromCache, final Callback<List<ImgurAlbum>> callback) {

        if (!fromCache) {
            String authHeader = "Bearer " + mPrefsStorage.getString("imgur-access-token");

            mImgurApi.getUserAlbums(authHeader, "me").enqueue(new retrofit2.Callback<ImgurUserAlbums>() {
                @Override
                public void onResponse(Call<ImgurUserAlbums> call, Response<ImgurUserAlbums> response) {
                    callback.onResult(response.body().getAlbumList());
                }

                @Override
                public void onFailure(Call<ImgurUserAlbums> call, Throwable t) {
                    callback.onError(call.hashCode(), "Something went wrong");
                }
            });
        } else {
            fetchAlbums(callback);
        }
    }

    @Override
    public void fetchAlbums(String searchText, Callback<List<ImgurAlbum>> callback) {
        // TODO("Write code to search for albums in local db");
    }
}
