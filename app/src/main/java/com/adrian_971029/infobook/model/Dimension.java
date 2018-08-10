package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Dimension  implements Parcelable{

    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("thickness")
    @Expose
    private String thickness;


    public Dimension() {
    }

    protected Dimension(Parcel in) {
        height = in.readString();
        width = in.readString();
        thickness = in.readString();
    }

    public static final Creator<Dimension> CREATOR = new Creator<Dimension>() {
        @Override
        public Dimension createFromParcel(Parcel in) {
            return new Dimension(in);
        }

        @Override
        public Dimension[] newArray(int size) {
            return new Dimension[size];
        }
    };

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(height);
        dest.writeString(width);
        dest.writeString(thickness);
    }
}
