package org.drulabs.picblast.data.models;

/**
 * Created by kaushald on 21/11/17.
 *
 * @Author kaushald
 */

public class PixyStatsHolder {

    private long views;
    private long imageCount;
    private String albumId;

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getImageCount() {
        return imageCount;
    }

    public void setImageCount(long imageCount) {
        this.imageCount = imageCount;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
