package org.drulabs.picblast.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class ImgurPic {

    @SerializedName("id")
    private String mId;

    @SerializedName("account_id")
    private String mAccountId;

    @SerializedName("account_url")
    private String mAccountUrl;

    @SerializedName("ad_type")
    private Long mAdType;

    @SerializedName("ad_url")
    private String mAdUrl;

    @SerializedName("animated")
    private Boolean mAnimated;

    @SerializedName("bandwidth")
    private Long mBandwidth;

    @SerializedName("datetime")
    private Long mDatetime;

    @SerializedName("deletehash")
    private String mDeletehash;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("favorite")
    private Boolean mFavorite;

    @SerializedName("has_sound")
    private Boolean mHasSound;

    @SerializedName("height")
    private Long mHeight;

    @SerializedName("in_gallery")
    private Boolean mInGallery;

    @SerializedName("in_most_viral")
    private Boolean mInMostViral;

    @SerializedName("is_ad")
    private Boolean mIsAd;

    @SerializedName("link")
    private String mLink;

    @SerializedName("name")
    private String mName;

    @SerializedName("nsfw")
    private Boolean mNsfw;

    @SerializedName("section")
    private String mSection;

    @SerializedName("size")
    private Long mSize;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("type")
    private String mType;

    @SerializedName("views")
    private Long mViews;

    @SerializedName("width")
    private Long mWidth;

    public String getAccountId() {
        return mAccountId;
    }

    public void setAccountId(String accountId) {
        mAccountId = accountId;
    }

    public String getAccountUrl() {
        return mAccountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        mAccountUrl = accountUrl;
    }

    public Long getAdType() {
        return mAdType;
    }

    public void setAdType(Long adType) {
        mAdType = adType;
    }

    public String getAdUrl() {
        return mAdUrl;
    }

    public void setAdUrl(String adUrl) {
        mAdUrl = adUrl;
    }

    public Boolean getAnimated() {
        return mAnimated;
    }

    public void setAnimated(Boolean animated) {
        mAnimated = animated;
    }

    public Long getBandwidth() {
        return mBandwidth;
    }

    public void setBandwidth(Long bandwidth) {
        mBandwidth = bandwidth;
    }

    public Long getDatetime() {
        return mDatetime;
    }

    public void setDatetime(Long datetime) {
        mDatetime = datetime;
    }

    public String getDeletehash() {
        return mDeletehash;
    }

    public void setDeletehash(String deletehash) {
        mDeletehash = deletehash;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Boolean getFavorite() {
        return mFavorite;
    }

    public void setFavorite(Boolean favorite) {
        mFavorite = favorite;
    }

    public Boolean getHasSound() {
        return mHasSound;
    }

    public void setHasSound(Boolean hasSound) {
        mHasSound = hasSound;
    }

    public Long getHeight() {
        return mHeight;
    }

    public void setHeight(Long height) {
        mHeight = height;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Boolean getInGallery() {
        return mInGallery;
    }

    public void setInGallery(Boolean inGallery) {
        mInGallery = inGallery;
    }

    public Boolean getInMostViral() {
        return mInMostViral;
    }

    public void setInMostViral(Boolean inMostViral) {
        mInMostViral = inMostViral;
    }

    public Boolean getIsAd() {
        return mIsAd;
    }

    public void setIsAd(Boolean isAd) {
        mIsAd = isAd;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Boolean getNsfw() {
        return mNsfw;
    }

    public void setNsfw(Boolean nsfw) {
        mNsfw = nsfw;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public Long getSize() {
        return mSize;
    }

    public void setSize(Long size) {
        mSize = size;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public Long getViews() {
        return mViews;
    }

    public void setViews(Long views) {
        mViews = views;
    }

    public Long getWidth() {
        return mWidth;
    }

    public void setWidth(Long width) {
        mWidth = width;
    }

    @Override
    public String toString() {
        Gson gson = (new GsonBuilder()).setExclusionStrategies(new FieldExclusionStrategy())
                .create();
        return gson.toJson(this);
    }


}
