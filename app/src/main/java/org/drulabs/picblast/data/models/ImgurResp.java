package org.drulabs.picblast.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kaushald on 19/11/17.
 */

public class ImgurResp {
    @SerializedName("status")
    Long mStatus;

    @SerializedName("success")
    Boolean mSuccess;

    public Long getmStatus() {
        return mStatus;
    }

    public void setmStatus(Long mStatus) {
        this.mStatus = mStatus;
    }

    public Boolean getmSuccess() {
        return mSuccess;
    }

    public void setmSuccess(Boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
