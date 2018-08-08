package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Livro implements Parcelable {

    private int id;
    private String title;
    private String author;
    private String content;
    private String content_short;
    private String publisher;
    private String publisher_date;
    private int pages;
    private String language;
    private String url_details;
    private String url_download;
    private String url_read_online;
    private String cover;
    private String thumbnail;
    private int num_comments;
    private ArrayList<Categoria> categories;
    private ArrayList<Tag> tags;

    public Livro() {
    }

    protected Livro(Parcel in) {
        id = in.readInt();
        title = in.readString();
        author = in.readString();
        content = in.readString();
        content_short = in.readString();
        publisher = in.readString();
        publisher_date = in.readString();
        pages = in.readInt();
        language = in.readString();
        url_details = in.readString();
        url_download = in.readString();
        url_read_online = in.readString();
        cover = in.readString();
        thumbnail = in.readString();
        num_comments = in.readInt();
        categories = in.createTypedArrayList(Categoria.CREATOR);
        tags = in.createTypedArrayList(Tag.CREATOR);
    }

    public static final Creator<Livro> CREATOR = new Creator<Livro>() {
        @Override
        public Livro createFromParcel(Parcel in) {
            return new Livro(in);
        }

        @Override
        public Livro[] newArray(int size) {
            return new Livro[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_short() {
        return content_short;
    }

    public void setContent_short(String content_short) {
        this.content_short = content_short;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher_date() {
        return publisher_date;
    }

    public void setPublisher_date(String publisher_date) {
        this.publisher_date = publisher_date;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl_details() {
        return url_details;
    }

    public void setUrl_details(String url_details) {
        this.url_details = url_details;
    }

    public String getUrl_download() {
        return url_download;
    }

    public void setUrl_download(String url_download) {
        this.url_download = url_download;
    }

    public String getUrl_read_online() {
        return url_read_online;
    }

    public void setUrl_read_online(String url_read_online) {
        this.url_read_online = url_read_online;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }

    public ArrayList<Categoria> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categoria> categories) {
        this.categories = categories;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(content_short);
        dest.writeString(publisher);
        dest.writeString(publisher_date);
        dest.writeInt(pages);
        dest.writeString(language);
        dest.writeString(url_details);
        dest.writeString(url_download);
        dest.writeString(url_read_online);
        dest.writeString(cover);
        dest.writeString(thumbnail);
        dest.writeInt(num_comments);
        dest.writeTypedList(categories);
        dest.writeTypedList(tags);
    }
}
