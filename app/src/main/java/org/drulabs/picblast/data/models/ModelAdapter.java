package org.drulabs.picblast.data.models;

import org.drulabs.picblast.utils.Constants;

/**
 * Created by kaushald on 17/11/17.
 */

public class ModelAdapter {

    public static PixyAlbum from(ImgurAlbum ia) {
        PixyAlbum pa = new PixyAlbum();
        pa.setId(ia.getId());
        pa.setAccountId(ia.getAccountId());
        pa.setAccountUrl(String.format("https://imgur.com/user/%s", ia.getAccountUrl()));
        pa.setCover(String.format("https://i.imgur.com/%s.jpg", ia.getCover()));
        pa.setCoverHeight(ia.getCoverHeight());
        pa.setCoverWidth(ia.getCoverWidth());
        pa.setDatetime(ia.getDatetime());
        pa.setDescription(ia.getDescription());
        pa.setFavorite(ia.getFavorite());
        pa.setImagesCount(ia.getImagesCount());
        pa.setTitle(ia.getTitle());
        pa.setViews(ia.getViews());
        pa.setProvider(Constants.PROVIDER_IMGUR);
        pa.setProviderUrl(ia.getLink());
        pa.setShared(ia.getPrivacy().equalsIgnoreCase("public"));

        return pa;
    }

    public static PixyPic from(String parentAlbumId, ImgurPic pic) {

        PixyPic pixyPic = new PixyPic();

        pixyPic.setAnimated(pic.getAnimated());
        pixyPic.setBandwidth(pic.getBandwidth());
        pixyPic.setDatetime(pic.getDatetime());
        pixyPic.setDescription(pic.getDescription());
        pixyPic.setFavorite(pic.getFavorite());
        pixyPic.setHasSound(pic.getHasSound());
        pixyPic.setHeight(pic.getHeight());
        pixyPic.setWidth(pic.getWidth());
        pixyPic.setId(pic.getId());
        pixyPic.setMimeType(pic.getType());
        pixyPic.setName(pic.getName());
        pixyPic.setProvider(Constants.PROVIDER_IMGUR);
        pixyPic.setProviderUrl(pic.getLink());
        pixyPic.setSize(pic.getSize());
        pixyPic.setTitle(pic.getTitle());
        pixyPic.setViews(pic.getViews());
        pixyPic.setParentId(parentAlbumId);


        return pixyPic;

    }

}
