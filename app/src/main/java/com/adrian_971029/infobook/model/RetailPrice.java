package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class RetailPrice implements Parcelable {

    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    public RetailPrice() {
    }

    protected RetailPrice(Parcel in) {
        amount = in.readDouble();
        currencyCode = in.readString();
    }

    public static final Creator<RetailPrice> CREATOR = new Creator<RetailPrice>() {
        @Override
        public RetailPrice createFromParcel(Parcel in) {
            return new RetailPrice(in);
        }

        @Override
        public RetailPrice[] newArray(int size) {
            return new RetailPrice[size];
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
