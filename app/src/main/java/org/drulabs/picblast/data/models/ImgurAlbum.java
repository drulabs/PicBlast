package org.drulabs.picblast.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImgurAlbum {

    @SerializedName("id")
    private String mId;

    @SerializedName("account_id")
    private Long mAccountId;

    @SerializedName("account_url")
    private String mAccountUrl;

    @SerializedName("cover")
    private String mCover;

    @SerializedName("cover_height")
    private Long mCoverHeight;

    @SerializedName("cover_width")
    private Long mCoverWidth;

    @SerializedName("datetime")
    private Long mDatetime;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("favorite")
    private Boolean mFavorite;

    @SerializedName("images_count")
    private Long mImagesCount;

    @SerializedName("in_gallery")
    private Boolean mInGallery;

    @SerializedName("is_ad")
    private Boolean mIsAd;

    @SerializedName("layout")
    private String mLayout;

    @SerializedName("link")
    private String mLink;

    @SerializedName("nsfw")
    private String mNsfw;

    @SerializedName("order")
    private Long mOrder;

    @SerializedName("privacy")
    private String mPrivacy;

    @SerializedName("section")
    private String mSection;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("views")
    private Long mViews;

    @SerializedName("images")
    private List<ImgurPic> albumImages;

    public Long getAccountId() {
        return mAccountId;
    }

    public void setAccountId(Long accountId) {
        mAccountId = accountId;
    }

    public String getAccountUrl() {
        return mAccountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        mAccountUrl = accountUrl;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }

    public Long getCoverHeight() {
        return mCoverHeight;
    }

    public void setCoverHeight(Long coverHeight) {
        mCoverHeight = coverHeight;
    }

    public Long getCoverWidth() {
        return mCoverWidth;
    }

    public void setCoverWidth(Long coverWidth) {
        mCoverWidth = coverWidth;
    }

    public Long getDatetime() {
        return mDatetime;
    }

    public void setDatetime(Long datetime) {
        mDatetime = datetime;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Long getImagesCount() {
        return mImagesCount;
    }

    public void setImagesCount(Long imagesCount) {
        mImagesCount = imagesCount;
    }

    public Boolean getInGallery() {
        return mInGallery;
    }

    public void setInGallery(Boolean inGallery) {
        mInGallery = inGallery;
    }

    public Boolean getIsAd() {
        return mIsAd;
    }

    public void setIsAd(Boolean isAd) {
        mIsAd = isAd;
    }

    public String getLayout() {
        return mLayout;
    }

    public void setLayout(String layout) {
        mLayout = layout;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public Object getNsfw() {
        return mNsfw;
    }

    public void setNsfw(String nsfw) {
        mNsfw = nsfw;
    }

    public Long getOrder() {
        return mOrder;
    }

    public void setOrder(Long order) {
        mOrder = order;
    }

    public String getPrivacy() {
        return mPrivacy;
    }

    public void setPrivacy(String privacy) {
        mPrivacy = privacy;
    }

    public Object getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Long getViews() {
        return mViews;
    }

    public void setViews(Long views) {
        mViews = views;
    }

    public List<ImgurPic> getAlbumImages() {
        return albumImages;
    }

    public void setAlbumImages(List<ImgurPic> albumImages) {
        this.albumImages = albumImages;
    }

    @Override
    public String toString() {
        Gson gson = (new GsonBuilder()).setExclusionStrategies(new FieldExclusionStrategy())
                .create();
        return gson.toJson(this);
    }
}
