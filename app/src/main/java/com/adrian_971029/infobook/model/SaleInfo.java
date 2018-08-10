package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class SaleInfo implements Parcelable {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("saleability")
    @Expose
    private String saleability;
    @SerializedName("onSaleDate")
    @Expose
    private String onSaleDate;
    @SerializedName("isEbook")
    @Expose
    private boolean isEbook;
    @SerializedName("listPrice")
    @Expose
    private ListPrice listPrice;
    @SerializedName("retailPrice")
    @Expose
    private RetailPrice retailPrice;
    @SerializedName("buyLink")
    @Expose
    private String buyLink;

    public SaleInfo() {
    }

    protected SaleInfo(Parcel in) {
        country = in.readString();
        saleability = in.readString();
        onSaleDate = in.readString();
        isEbook = in.readByte() != 0;
        listPrice = in.readParcelable(ListPrice.class.getClassLoader());
        retailPrice = in.readParcelable(RetailPrice.class.getClassLoader());
        buyLink = in.readString();
    }

    public static final Creator<SaleInfo> CREATOR = new Creator<SaleInfo>() {
        @Override
        public SaleInfo createFromParcel(Parcel in) {
            return new SaleInfo(in);
        }

        @Override
        public SaleInfo[] newArray(int size) {
            return new SaleInfo[size];
        }
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public String getOnSaleDate() {
        return onSaleDate;
    }

    public void setOnSaleDate(String onSaleDate) {
        this.onSaleDate = onSaleDate;
    }

    public static Creator<SaleInfo> getCREATOR() {
        return CREATOR;
    }

    public boolean isEbook() {
        return isEbook;
    }

    public void setEbook(boolean ebook) {
        isEbook = ebook;
    }

    public ListPrice getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrice listPrice) {
        this.listPrice = listPrice;
    }

    public RetailPrice getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(RetailPrice retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(saleability);
        dest.writeString(onSaleDate);
        dest.writeByte((byte) (isEbook ? 1 : 0));
        dest.writeParcelable(listPrice, flags);
        dest.writeParcelable(retailPrice, flags);
        dest.writeString(buyLink);
    }
}
