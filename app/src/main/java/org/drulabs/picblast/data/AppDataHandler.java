package org.drulabs.picblast.data;

import android.content.Context;

import org.drulabs.picblast.data.models.ImgurAlbum;
import org.drulabs.picblast.data.models.ImgurAlbumDetails;
import org.drulabs.picblast.data.models.ImgurPic;
import org.drulabs.picblast.data.models.ImgurResp;
import org.drulabs.picblast.data.models.ImgurUserAlbums;
import org.drulabs.picblast.data.models.ModelAdapter;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.data.models.PixyAlbum_;
import org.drulabs.picblast.data.models.PixyPic;
import org.drulabs.picblast.data.models.PixyPic_;
import org.drulabs.picblast.data.network.ImgurApi;
import org.drulabs.picblast.utils.Constants;
import org.drulabs.picblast.utils.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */

public class AppDataHandler implements DataHandler {

    private PrefsStorage mPrefsStorage;
    private ImgurApi mImgurApi;
    private Box<PixyAlbum> mAlbumBox;
    private Box<PixyPic> mPixBox;
    private Context mContext;

    @Inject
    public AppDataHandler(Context context, PrefsStorage prefsStorage, ImgurApi imgurApi,
                          Box<PixyAlbum> albumBox, Box<PixyPic> pixBox) {
        this.mContext = context;
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
    public Observable<List<PixyAlbum>> fetchAlbums() {

        boolean isNetworkAvailable = Utility.isNetworkAvailable(mContext);

        if (isNetworkAvailable) {
            String authHeader = "Bearer " + mPrefsStorage.getString("imgur-access-token");
            Observable<ImgurUserAlbums> imgurAlbumsObservable = mImgurApi.fetchUserAlbums
                    (authHeader, "me");
            return imgurAlbumsObservable
                    .map(ImgurUserAlbums::getAlbumList)
                    .compose(upstream -> upstream.subscribeOn(Schedulers.io())
                            .map(imgurAlbums -> {
                                List<PixyAlbum> pixyAlbums = new ArrayList<>();
                                for (ImgurAlbum singleImgurAlbum : imgurAlbums) {
                                    PixyAlbum soloPixyAlbum = ModelAdapter.from
                                            (singleImgurAlbum);
                                    pixyAlbums.add(soloPixyAlbum);
                                    mAlbumBox.query().equal(PixyAlbum_.id, singleImgurAlbum.getId())
                                            .build().remove();
                                }

                                mAlbumBox.put(pixyAlbums);

                                return pixyAlbums;
                            })
                            .doOnError(Throwable::printStackTrace)
                    );
        } else {
            return Observable.create(subscriber -> {
                try {
                    List<PixyAlbum> pixyAlbums = mAlbumBox.query().build().find(); // add pagination here if required
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
    public Observable<PixyAlbum> fetchAlbums(String searchText) {
        return Observable.create(subscriber -> {
            try {
                QueryBuilder<PixyAlbum> albumQry = mAlbumBox.query();
                albumQry.contains(PixyAlbum_.title, searchText)
                        .or().contains(PixyAlbum_.description, searchText)
                        .or().contains(PixyAlbum_.provider, searchText);
                List<PixyAlbum> pixyAlbums = albumQry.build().find(); // add pagination here if required
                for (PixyAlbum singlePixyAlbum : pixyAlbums) {
                    subscriber.onNext(singlePixyAlbum);
                }
                subscriber.onComplete();
            } catch (Exception e1) {
                e1.printStackTrace();
                subscriber.onError(e1);
            }
        });
    }

    @Override
    public Observable<List<PixyPic>> getAlbumImages(String provider, String albumId) {

        boolean isNetworkAvailable = Utility.isNetworkAvailable(mContext);

        if (isNetworkAvailable) {
            String authHeader = "Bearer " + mPrefsStorage.getString("imgur-access-token");
            Observable<ImgurAlbumDetails> albumDetailsObservable = mImgurApi.getAlbumDetails
                    (authHeader, albumId);
            return albumDetailsObservable
                    .map(ImgurAlbumDetails::getImgurAlbum)
                    .compose(upstream -> upstream.subscribeOn(Schedulers.io())
                            .map(imgurAlbum -> {
                                List<PixyPic> pixyPics = new ArrayList<>();
                                for (ImgurPic singleImg : imgurAlbum.getAlbumImages()) {
                                    PixyPic soloPic = ModelAdapter.from(imgurAlbum.getId(),
                                            singleImg);
                                    pixyPics.add(soloPic);

                                    mPixBox.query().equal(PixyPic_.id, singleImg.getId())
                                            .build().remove();
                                }

                                mPixBox.put(pixyPics);

                                return pixyPics;
                            })
                    );
        } else {
            return Observable.create(subscriber -> {
                try {
                    List<PixyPic> pixyPics = mPixBox.query().equal(PixyPic_.parentId, albumId).build()
                            .find();
                    subscriber.onNext(pixyPics);
                    subscriber.onComplete();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    subscriber.onError(e1);
                }
            });
        }
    }

    @Override
    public void fetchAlbums(Callback<List<PixyAlbum>> callback) {

        boolean isNetworkAvailable = Utility.isNetworkAvailable(mContext);

        if (isNetworkAvailable) {
            // Fetch data from third party service
            // Convert into desired format as we don't want to use service specific models
            // Save it in local database, make sure not to block main thread
            // return the data via callback
        } else {
            // Fetch from local db
            // Do not block the main thread
            // return the data via callback
        }

        callback.onError(0, "Not implemented");
    }

    @Override
    public Single<Void> createAlbum(String service, String name, String description, String
            privacy) {
        return null;
    }

    @Override
    public Single<PixyAlbum> getAlbum(String provider, String albumId) {
        return Single.create(e -> {

            if (provider == null || provider.isEmpty()) {
                e.onError(new Exception("Provider cannot be null or empty"));
                return;
            }

            if (provider.equalsIgnoreCase(Constants.PROVIDER_IMGUR)) {
                List<PixyAlbum> pixyAlbums = mAlbumBox.query().equal(PixyAlbum_.id, albumId)
                        .build().find();
                if (pixyAlbums != null && pixyAlbums.size() > 0) {
                    e.onSuccess(pixyAlbums.get(0));
                }
            } else {
                e.onError(new Exception("Invalid provider..."));
                return;
            }
        });
    }

    @Override
    public Single<ImgurResp> uploadImage(String provider, String albumId, String filePath, String
            title, String description, String name) {

        String authHeader = "Bearer " + mPrefsStorage.getString("imgur-access-token");

        File imageFile = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile
                .getName(), reqFile);

        RequestBody albumIdPart = RequestBody.create(MediaType.parse("text/plain"), albumId);

        RequestBody titlePart = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody descPart = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), name);

        return mImgurApi.uploadImage(authHeader, imagePart, albumIdPart,
                titlePart, descPart, namePart);
    }
}
