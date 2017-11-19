
package org.drulabs.picblast.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class ImgurAlbumDetails {

    @SerializedName("data")
    ImgurAlbum imgurAlbum;

    @SerializedName("status")
    Long mStatus;

    @SerializedName("success")
    Boolean mSuccess;

    public ImgurAlbum getImgurAlbum() {
        return imgurAlbum;
    }

    public void setImgurAlbum(ImgurAlbum imgurAlbum) {
        this.imgurAlbum = imgurAlbum;
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
