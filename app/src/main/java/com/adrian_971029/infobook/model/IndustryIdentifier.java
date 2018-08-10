package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class IndustryIdentifier implements Parcelable {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("identifier")
    @Expose
    private String identifier;

    public IndustryIdentifier() {
    }

    protected IndustryIdentifier(Parcel in) {
        type = in.readString();
        identifier = in.readString();
    }

    public static final Creator<IndustryIdentifier> CREATOR = new Creator<IndustryIdentifier>() {
        @Override
        public IndustryIdentifier createFromParcel(Parcel in) {
            return new IndustryIdentifier(in);
        }

        @Override
        public IndustryIdentifier[] newArray(int size) {
            return new IndustryIdentifier[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(identifier);
    }
}
