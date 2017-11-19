package org.drulabs.picblast.data.network;

import org.drulabs.picblast.data.models.ImgurAlbumDetails;
import org.drulabs.picblast.data.models.ImgurResp;
import org.drulabs.picblast.data.models.ImgurUserAlbums;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by kaushald.
 */

public interface ImgurApi {

    @GET("/3/account/{username}/albums")
    Call<ImgurUserAlbums> getUserAlbums(@Header("Authorization") String authHeader, @Path("username")
            String username);

    @GET("/3/account/{username}/albums")
    Observable<ImgurUserAlbums> fetchUserAlbums(@Header("Authorization") String authHeader, @Path
            ("username") String username);

    @Multipart
    @POST("/3/image")
    Single<ImgurResp> uploadImage(@Header("Authorization") String authHeader,
                                  @Part MultipartBody.Part image,
                                  @Part("album") RequestBody albumId,
                                  @Part("title") RequestBody title,
                                  @Part("description") RequestBody description,
                                  @Part("name") RequestBody name);

    @Multipart
    @POST
    Single<ImgurResp> createAlbum(@Header("Authorization") String authHeader,
                       @Part("title") RequestBody title,
                       @Part("description") RequestBody description,
                       @Part("privacy") RequestBody privacy);

    @GET("/3/account/me/album/{albumHash}")
    Observable<ImgurAlbumDetails> getAlbumDetails(@Header("Authorization") String authHeader,
                                                  @Path("albumHash") String albumHash);

}
