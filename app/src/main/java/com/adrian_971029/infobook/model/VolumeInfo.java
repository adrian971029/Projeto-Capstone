
package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeInfo implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("authors")
    @Expose
    private ArrayList<String> authors;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("publishedDate")
    @Expose
    private String publishedDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("industryIdentifiers")
    @Expose
    private ArrayList<IndustryIdentifier> industryIdentifiers;
    @SerializedName("pageCount")
    @Expose
    private int pageCount;
    @SerializedName("dimensions")
    @Expose
    private Dimension dimensions;
    @SerializedName("printType")
    @Expose
    private String printType;
    @SerializedName("mainCategory")
    @Expose
    private String mainCategory;
    @SerializedName("categories")
    @Expose
    private ArrayList<String> categories;
    @SerializedName("averageRating")
    @Expose
    private double averageRating;
    @SerializedName("ratingsCount")
    @Expose
    private int ratingsCount;
    @SerializedName("contentVersion")
    @Expose
    private String contentVersion;
    @SerializedName("imageLinks")
    @Expose
    private ImageLinks imageLinks;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("previewLink")
    @Expose
    private String previewLink;
    @SerializedName("infoLink")
    @Expose
    private String infoLink;
    @SerializedName("canonicalVolumeLink")
    @Expose
    private String canonicalVolumeLink;

    public VolumeInfo() {
    }

    protected VolumeInfo(Parcel in) {
        title = in.readString();
        subtitle = in.readString();
        authors = in.createStringArrayList();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        industryIdentifiers = in.createTypedArrayList(IndustryIdentifier.CREATOR);
        pageCount = in.readInt();
        dimensions = in.readParcelable(Dimension.class.getClassLoader());
        printType = in.readString();
        mainCategory = in.readString();
        categories = in.createStringArrayList();
        averageRating = in.readDouble();
        ratingsCount = in.readInt();
        contentVersion = in.readString();
        imageLinks = in.readParcelable(ImageLinks.class.getClassLoader());
        language = in.readString();
        previewLink = in.readString();
        infoLink = in.readString();
        canonicalVolumeLink = in.readString();
    }

    public static final Creator<VolumeInfo> CREATOR = new Creator<VolumeInfo>() {
        @Override
        public VolumeInfo createFromParcel(Parcel in) {
            return new VolumeInfo(in);
        }

        @Override
        public VolumeInfo[] newArray(int size) {
            return new VolumeInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<IndustryIdentifier> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(ArrayList<IndustryIdentifier> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Dimension getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimension dimensions) {
        this.dimensions = dimensions;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public String getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getCanonicalVolumeLink() {
        return canonicalVolumeLink;
    }

    public void setCanonicalVolumeLink(String canonicalVolumeLink) {
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeStringList(authors);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(description);
        dest.writeTypedList(industryIdentifiers);
        dest.writeInt(pageCount);
        dest.writeParcelable(dimensions, flags);
        dest.writeString(printType);
        dest.writeString(mainCategory);
        dest.writeStringList(categories);
        dest.writeDouble(averageRating);
        dest.writeInt(ratingsCount);
        dest.writeString(contentVersion);
        dest.writeParcelable(imageLinks, flags);
        dest.writeString(language);
        dest.writeString(previewLink);
        dest.writeString(infoLink);
        dest.writeString(canonicalVolumeLink);
    }
}
