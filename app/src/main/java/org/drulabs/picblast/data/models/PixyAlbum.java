package org.drulabs.picblast.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToMany;

@Entity
public class PixyAlbum implements Serializable {

    @Id
    long oID;

    @Index
    @SerializedName("id")
    String id;

    @SerializedName("provider_id")
    Long accountId;

    @SerializedName("provider_url")
    String accountUrl;

    @SerializedName("cover_pic")
    String cover;

    @SerializedName("cover_height")
    Long coverHeight;

    @SerializedName("cover_width")
    Long coverWidth;

    @SerializedName("datetime")
    Long datetime;

    @SerializedName("description")
    String description;

    @SerializedName("favorite")
    Boolean favorite;

    @SerializedName("images_count")
    Long imagesCount;

    @SerializedName("provider")
    String provider;

    @SerializedName("provider_url")
    String providerUrl;

    @SerializedName("title")
    String title;

    @SerializedName("is_shared")
    Boolean shared;

    @SerializedName("views")
    Long views;

    @Exclude
    @Backlink
    ToMany<PixyPic> pics;

    public long getoID() {
        return oID;
    }

    public void setoID(long oID) {
        this.oID = oID;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(Long coverHeight) {
        this.coverHeight = coverHeight;
    }

    public Long getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(Long coverWidth) {
        this.coverWidth = coverWidth;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(Long imagesCount) {
        this.imagesCount = imagesCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    @Override
    public String toString() {
        Gson gson = (new GsonBuilder()).setExclusionStrategies(new FieldExclusionStrategy())
                .create();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PixyAlbum pixyAlbum = (PixyAlbum) o;

        if (!id.equals(pixyAlbum.id)) return false;
        if (!accountId.equals(pixyAlbum.accountId)) return false;
        if (!accountUrl.equals(pixyAlbum.accountUrl)) return false;
        if (!provider.equals(pixyAlbum.provider)) return false;
        return providerUrl.equals(pixyAlbum.providerUrl);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + accountId.hashCode();
        result = 31 * result + accountUrl.hashCode();
        result = 31 * result + provider.hashCode();
        result = 31 * result + providerUrl.hashCode();
        return result;
    }
}
