package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class UserInfo implements Parcelable {

    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("readingPosition")
    @Expose
    private String readingPosition;
    @SerializedName("isPurchased")
    @Expose
    private boolean isPurchased;
    @SerializedName("isPreordered")
    @Expose
    private boolean isPreordered;
    @SerializedName("updated")
    @Expose
    private String updated;

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        review = in.readString();
        readingPosition = in.readString();
        isPurchased = in.readByte() != 0;
        isPreordered = in.readByte() != 0;
        updated = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReadingPosition() {
        return readingPosition;
    }

    public void setReadingPosition(String readingPosition) {
        this.readingPosition = readingPosition;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public boolean isPreordered() {
        return isPreordered;
    }

    public void setPreordered(boolean preordered) {
        isPreordered = preordered;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(review);
        dest.writeString(readingPosition);
        dest.writeByte((byte) (isPurchased ? 1 : 0));
        dest.writeByte((byte) (isPreordered ? 1 : 0));
        dest.writeString(updated);
    }
}
