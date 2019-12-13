package com.example.whoknows2.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Source  implements Serializable, Parcelable{
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;

    public Source(String id, String name, String description, String url, String category, String language, String country)  {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
    }

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Source() {
    }

    protected Source(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        url = in.readString();
        category = in.readString();
        language = in.readString();
        country = in.readString();
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    @NonNull
    public String toString() {
        return this.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getId());
        parcel.writeString(getName());
    }
}
