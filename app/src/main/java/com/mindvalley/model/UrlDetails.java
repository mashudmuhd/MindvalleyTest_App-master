package com.mindvalley.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Url Details model
 * @author SandeepD
 */
public class UrlDetails implements Parcelable
{
	@SerializedName("raw")
	@Expose
	private String raw;
	@SerializedName("full")
	@Expose
	private String full;
	@SerializedName("regular")
	@Expose
	private String regular;
	@SerializedName("small")
	@Expose
	private String small;
	@SerializedName("thumb")
	@Expose
	private String thumb;
	
	protected UrlDetails(Parcel in)
	{
		raw = in.readString();
		full = in.readString();
		regular = in.readString();
		small = in.readString();
		thumb = in.readString();
	}
	
	public static final Creator<UrlDetails> CREATOR = new Creator<UrlDetails>()
	{
		@Override
		public UrlDetails createFromParcel(Parcel in)
		{
			return new UrlDetails(in);
		}
		
		@Override
		public UrlDetails[] newArray(int size)
		{
			return new UrlDetails[size];
		}
	};
	
	public String getRaw()
	{
		return raw;
	}
	
	public void setRaw(String raw)
	{
		this.raw = raw;
	}
	
	public String getFull()
	{
		return full;
	}
	
	public void setFull(String full)
	{
		this.full = full;
	}
	
	public String getRegular()
	{
		return regular;
	}
	
	public void setRegular(String regular)
	{
		this.regular = regular;
	}
	
	public String getSmall()
	{
		return small;
	}
	
	public void setSmall(String small)
	{
		this.small = small;
	}
	
	public String getThumb()
	{
		return thumb;
	}
	
	public void setThumb(String thumb)
	{
		this.thumb = thumb;
	}
	
	@Override
	public int describeContents()
	{
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int i)
	{
		parcel.writeString(raw);
		parcel.writeString(full);
		parcel.writeString(regular);
		parcel.writeString(small);
		parcel.writeString(thumb);
	}
}