
package org.drulabs.picblast.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImgurUserAlbums {

    @SerializedName("data")
    List<ImgurAlbum> albumList;

    @SerializedName("status")
    Long mStatus;

    @SerializedName("success")
    Boolean mSuccess;

//    @Exclude
//    @Backlink
//    ToMany<ImgurAlbum> albumListObj;

    public List<ImgurAlbum> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<ImgurAlbum> data) {
        albumList = data;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

    @Override
    public String toString() {
        Gson gson = (new GsonBuilder()).setExclusionStrategies(new FieldExclusionStrategy())
                .create();
        return gson.toJson(this);
    }
}
