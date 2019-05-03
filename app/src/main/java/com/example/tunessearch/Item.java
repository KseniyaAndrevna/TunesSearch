package com.example.tunessearch;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

//this class is dataClass for all if items
class Item implements Parcelable {
    @SerializedName("artworkUrl100")
    private String imageRes;

    @SerializedName(value = "collectionName", alternate = "trackName")
    private String title;

    @SerializedName(value = "artistName", alternate = "trackNumber")
    private String subTitle;

    private String primaryGenreName;
    private String releaseDate;
    private int collectionId;
    private String trackCount;

    private Item(Parcel in) {
        imageRes = in.readString();
        title = in.readString();
        subTitle = in.readString();
        primaryGenreName = in.readString();
        releaseDate = in.readString();
        collectionId = in.readInt();
        trackCount = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    String getPrimaryGenreName() {
        return primaryGenreName;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    String getTrackCount() {
        return trackCount;
    }

    String getTitle() {
        return title;
    }

    int getCollectionId() {
        return collectionId;
    }

    String getSubTitle() {
        return subTitle;
    }

    String getImageRes() {
        return imageRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageRes);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(primaryGenreName);
        dest.writeString(releaseDate);
        dest.writeInt(collectionId);
        dest.writeString(trackCount);
    }
}
