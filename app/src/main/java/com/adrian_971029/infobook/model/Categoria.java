package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Categoria implements Parcelable {

    private int category_id;
    private String name;
    private String nicename;

    public Categoria() {
    }

    protected Categoria(Parcel in) {
        category_id = in.readInt();
        name = in.readString();
        nicename = in.readString();
    }

    public static final Creator<Categoria> CREATOR = new Creator<Categoria>() {
        @Override
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }

        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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
        dest.writeInt(category_id);
        dest.writeString(name);
        dest.writeString(nicename);
    }
}
