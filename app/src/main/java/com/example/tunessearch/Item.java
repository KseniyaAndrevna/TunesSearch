package com.example.tunessearch;

import com.google.gson.annotations.SerializedName;


class Item {
    @SerializedName("artworkUrl60")
    private String imageRes;

    @SerializedName("collectionName")
    private String title;

    @SerializedName("artistName")
    private String subTitle;


    Item(String imageRes, String title, String subTitle) {
        this.imageRes = imageRes;
        this.title = title;
        this.subTitle = subTitle;
    }

    String getTitle() {
        return title;
    }

    String getSubTitle() {
        return subTitle;
    }

    String getImageRes() {
        return imageRes;
    }

}
