package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Pdf implements Parcelable {

    @SerializedName("isAvailable")
    @Expose
    private boolean isAvailable;
    @SerializedName("downloadLink")
    @Expose
    private String downloadLink;
    @SerializedName("acsTokenLink")
    @Expose
    private String acsTokenLink;

    public Pdf() {
    }

    protected Pdf(Parcel in) {
        isAvailable = in.readByte() != 0;
        downloadLink = in.readString();
        acsTokenLink = in.readString();
    }

    public static final Creator<Pdf> CREATOR = new Creator<Pdf>() {
        @Override
        public Pdf createFromParcel(Parcel in) {
            return new Pdf(in);
        }

        @Override
        public Pdf[] newArray(int size) {
            return new Pdf[size];
        }
    };

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getAcsTokenLink() {
        return acsTokenLink;
    }

    public void setAcsTokenLink(String acsTokenLink) {
        this.acsTokenLink = acsTokenLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isAvailable ? 1 : 0));
        dest.writeString(downloadLink);
        dest.writeString(acsTokenLink);
    }
}
