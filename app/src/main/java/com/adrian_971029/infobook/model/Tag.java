package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {

    private int tag_id;
    private String name;
    private String nicename;

    public Tag() {
    }

    protected Tag(Parcel in) {
        tag_id = in.readInt();
        name = in.readString();
        nicename = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tag_id);
        dest.writeString(name);
        dest.writeString(nicename);
    }
}
