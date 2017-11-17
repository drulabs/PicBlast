package org.drulabs.picblast.data.network;

import org.drulabs.picblast.data.models.ImgurUserAlbums;
import org.drulabs.picblast.data.models.ImgurAlbum;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by kaushald.
 */

public interface ImgurApi {

    @GET("/3/account/{username}/albums")
    Call<ImgurUserAlbums> getUserAlbums(@Header("Authorization") String authHeader, @Path("username")
            String username);

    @GET("/3/account/{username}/albums")
    Observable<ImgurUserAlbums> fetchUserAlbums(@Header("Authorization") String authHeader
            , @Path("username") String username);

    @GET("/3/account/me/album/{albumHash}")
    Call<ImgurAlbum> getAlbumDetails(@Header("Authorization") String authHeader, @Path("albumHash")
            String albumHash);

}
