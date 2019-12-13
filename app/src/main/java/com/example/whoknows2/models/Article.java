package com.example.whoknows2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

    private Source mSource;
    private String mAuthor;
    private String mTitle;
    private String mDescription;
    private String mUrl;
    private String mUrlToImage;
    private String mPublishedAt;
    private String mContent;

    public Article() {
    }

    protected Article(Parcel in) {
        mSource = in.readParcelable(Source.class.getClassLoader());
        mAuthor = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mUrl = in.readString();
        mUrlToImage = in.readString();
        mPublishedAt = in.readString();
        mContent = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public Source getSource() {
        return mSource;
    }

    public void setSource(Source source) {
        mSource = source;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mSource, flags);
        dest.writeString(mAuthor);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mUrl);
        dest.writeString(mUrlToImage);
        dest.writeString(mPublishedAt);
        dest.writeString(mContent);
    }
}

