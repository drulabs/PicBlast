package org.drulabs.picblast.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToOne;

@Entity
public class PixyPic {

    @Id
    long oID;

    @Index
    @SerializedName("id")
    String id;

    @SerializedName("animated")
    Boolean animated;

    @SerializedName("bandwidth")
    Long bandwidth;

    @SerializedName("datetime")
    Long datetime;

    @SerializedName("hash")
    String hash;

    @SerializedName("description")
    String description;

    @SerializedName("favorite")
    Boolean favorite;

    @SerializedName("has_sound")
    Boolean hasSound;

    @SerializedName("height")
    Long height;

    @SerializedName("provider_url")
    String providerUrl;

    @SerializedName("provider")
    String provider;

    @SerializedName("name")
    String name;

    @SerializedName("size")
    Long size;

    @SerializedName("title")
    String title;

    @SerializedName("type")
    String mimeType;

    @SerializedName("views")
    Long views;

    @SerializedName("width")
    Long width;

    @Exclude
    ToOne<PixyAlbum> parentAlbum;

    public long getoID() {
        return oID;
    }

    public void setoID(long oID) {
        this.oID = oID;
    }


    public Boolean getAnimated() {
        return animated;
    }

    public void setAnimated(Boolean animated) {
        this.animated = animated;
    }

    public Long getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Long bandwidth) {
        this.bandwidth = bandwidth;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public Boolean getHasSound() {
        return hasSound;
    }

    public void setHasSound(Boolean hasSound) {
        this.hasSound = hasSound;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String type) {
        mimeType = type;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        Gson gson = (new GsonBuilder()).setExclusionStrategies(new FieldExclusionStrategy())
                .create();
        return gson.toJson(this);
    }

}
