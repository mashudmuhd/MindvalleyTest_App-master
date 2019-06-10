package com.mindvalley.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * links model
 * @author SandeepD
 */
public class LinkDetails implements Parcelable
{
    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("html")
    @Expose
    private String html;
    @SerializedName("photos")
    @Expose
    private String photos;
    @SerializedName("likes")
    @Expose
    private String likes;

    protected LinkDetails(Parcel in)
    {
        self = in.readString();
        html = in.readString();
        photos = in.readString();
        likes = in.readString();
    }

    public static final Creator<LinkDetails> CREATOR = new Creator<LinkDetails>()
    {
        @Override
        public LinkDetails createFromParcel(Parcel in)
        {
            return new LinkDetails(in);
        }

        @Override
        public LinkDetails[] newArray(int size)
        {
            return new LinkDetails[size];
        }
    };

    public String getSelf()
    {
        return self;
    }

    public void setSelf(String self)
    {
        this.self = self;
    }

    public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
    }

    public String getPhotos()
    {
        return photos;
    }

    public void setPhotos(String photos)
    {
        this.photos = photos;
    }

    public String getLikes()
    {
        return likes;
    }

    public void setLikes(String likes)
    {
        this.likes = likes;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(self);
        parcel.writeString(html);
        parcel.writeString(photos);
        parcel.writeString(likes);
    }
}