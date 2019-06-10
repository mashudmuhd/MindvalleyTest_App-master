package com.mindvalley.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * User Details model
 * @author SandeepD
 */
public class UserDetails implements Parcelable
{
	@SerializedName("id")
	@Expose
	private String       id;
	@SerializedName("username")
	@Expose
	private String       username;
	@SerializedName("name")
	@Expose
	private String       name;
	@SerializedName("profile_image")
	@Expose
	private ProfileImage profileImage;
	@SerializedName("links")
	@Expose
	private LinkDetails  linkDetails;
	
	protected UserDetails(Parcel in)
	{
		id = in.readString();
		username = in.readString();
		name = in.readString();
		profileImage = in.readParcelable(ProfileImage.class.getClassLoader());
		linkDetails = in.readParcelable(LinkDetails.class.getClassLoader());
	}
	
	public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>()
	{
		@Override
		public UserDetails createFromParcel(Parcel in)
		{
			return new UserDetails(in);
		}
		
		@Override
		public UserDetails[] newArray(int size)
		{
			return new UserDetails[size];
		}
	};
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public ProfileImage getProfileImage()
	{
		return profileImage;
	}
	
	public void setProfileImage(ProfileImage profileImage)
	{
		this.profileImage = profileImage;
	}
	
	public LinkDetails getLinkDetails()
	{
		return linkDetails;
	}
	
	public void setLinkDetails(LinkDetails linkDetails)
	{
		this.linkDetails = linkDetails;
	}
	
	@Override
	public int describeContents()
	{
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int i)
	{
		parcel.writeString(id);
		parcel.writeString(username);
		parcel.writeString(name);
		parcel.writeParcelable(profileImage, i);
		parcel.writeParcelable(linkDetails, i);
	}
}