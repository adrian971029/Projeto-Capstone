package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ListPrice implements Parcelable {

    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    public ListPrice() {
    }

    protected ListPrice(Parcel in) {
        amount = in.readDouble();
        currencyCode = in.readString();
    }

    public static final Creator<ListPrice> CREATOR = new Creator<ListPrice>() {
        @Override
        public ListPrice createFromParcel(Parcel in) {
            return new ListPrice(in);
        }

        @Override
        public ListPrice[] newArray(int size) {
            return new ListPrice[size];
        }
    };

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeString(currencyCode);
    }
}
