package com.mindvalley.appcontentloader.models;

import com.mindvalley.appcontentloader.callback.ContentServiceObserver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class ServiceContentTypeDownload
{
	private String                     url;
	private byte[]                     data;
	private ContentDataType contentDataType;
	private ContentServiceObserver contentServiceObserver;
	private String                     keyMD5;
	// User For Just For Test
	public String comeFrom = "Internet";
	
	protected ServiceContentTypeDownload(String url, ContentDataType contentDataType, ContentServiceObserver contentServiceObserver)
	{
		this.url = url;
		this.contentDataType = contentDataType;
		this.contentServiceObserver = contentServiceObserver;
		this.keyMD5 = md5(this.url);
	}
	
	public String getKeyMD5()
	{
		return keyMD5;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public byte[] getData()
	{
		return data;
	}
	
	public ContentDataType getContentDataType()
	{
		return contentDataType;
	}
	
	public ContentServiceObserver getContentServiceObserver()
	{
		return contentServiceObserver;
	}
	
	public void setData(byte[] data)
	{
		this.data = data;
	}
	
	public abstract ServiceContentTypeDownload getCopyOfMe(ContentServiceObserver contentServiceObserver);
	
	public static final String md5(final String s)
	{
		try
		{
			// Create MD5 Hash
			MessageDigest digest = MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuilder hexString = new StringBuilder();
			for (byte aMessageDigest : messageDigest)
			{
				String h = Integer.toHexString(0xFF & aMessageDigest);
				while (h.length() < 2)
				{
					h = "0" + h;
				}
				hexString.append(h);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
