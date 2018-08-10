package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class AccessInfo implements Parcelable {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("viewability")
    @Expose
    private String viewability;
    @SerializedName("embeddable")
    @Expose
    private boolean embeddable;
    @SerializedName("publicDomain")
    @Expose
    private boolean publicDomain;
    @SerializedName("textToSpeechPermission")
    @Expose
    private String textToSpeechPermission;
    @SerializedName("epub")
    @Expose
    private Epub epub;
    @SerializedName("pdf")
    @Expose
    private Pdf pdf;
    @SerializedName("webReaderLink")
    @Expose
    private String webReaderLink;
    @SerializedName("accessViewStatus")
    @Expose
    private String accessViewStatus;
    @SerializedName("downloadAccess")
    @Expose
    private DownloadAccess downloadAccess;
    @SerializedName("searchInfo")
    @Expose
    private SearchInfo searchInfo;

    public AccessInfo() {
    }

    protected AccessInfo(Parcel in) {
        country = in.readString();
        viewability = in.readString();
        embeddable = in.readByte() != 0;
        publicDomain = in.readByte() != 0;
        textToSpeechPermission = in.readString();
        epub = in.readParcelable(Epub.class.getClassLoader());
        pdf = in.readParcelable(Pdf.class.getClassLoader());
        webReaderLink = in.readString();
        accessViewStatus = in.readString();
        downloadAccess = in.readParcelable(DownloadAccess.class.getClassLoader());
        searchInfo = in.readParcelable(SearchInfo.class.getClassLoader());
    }

    public static final Creator<AccessInfo> CREATOR = new Creator<AccessInfo>() {
        @Override
        public AccessInfo createFromParcel(Parcel in) {
            return new AccessInfo(in);
        }

        @Override
        public AccessInfo[] newArray(int size) {
            return new AccessInfo[size];
        }
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getViewability() {
        return viewability;
    }

    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    public boolean isEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

    public boolean isPublicDomain() {
        return publicDomain;
    }

    public void setPublicDomain(boolean publicDomain) {
        this.publicDomain = publicDomain;
    }

    public String getTextToSpeechPermission() {
        return textToSpeechPermission;
    }

    public void setTextToSpeechPermission(String textToSpeechPermission) {
        this.textToSpeechPermission = textToSpeechPermission;
    }

    public Epub getEpub() {
        return epub;
    }

    public void setEpub(Epub epub) {
        this.epub = epub;
    }

    public Pdf getPdf() {
        return pdf;
    }

    public void setPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public String getAccessViewStatus() {
        return accessViewStatus;
    }

    public void setAccessViewStatus(String accessViewStatus) {
        this.accessViewStatus = accessViewStatus;
    }

    public DownloadAccess getDownloadAccess() {
        return downloadAccess;
    }

    public void setDownloadAccess(DownloadAccess downloadAccess) {
        this.downloadAccess = downloadAccess;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(viewability);
        dest.writeByte((byte) (embeddable ? 1 : 0));
        dest.writeByte((byte) (publicDomain ? 1 : 0));
        dest.writeString(textToSpeechPermission);
        dest.writeParcelable(epub, flags);
        dest.writeParcelable(pdf, flags);
        dest.writeString(webReaderLink);
        dest.writeString(accessViewStatus);
        dest.writeParcelable(downloadAccess, flags);
        dest.writeParcelable(searchInfo, flags);
    }
}
