package org.drulabs.picblast.data.models;

import org.drulabs.picblast.utils.Constants;

/**
 * Created by kaushald on 17/11/17.
 */

public class ModelAdapter {

    public static PixyAlbum fromImgurToPixy(ImgurAlbum ia) {
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

}
