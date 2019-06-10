package com.mindvalley.appcontentloader.models;

import com.mindvalley.appcontentloader.callback.ContentServiceObserver;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class ServiceJsonDownload extends ServiceContentTypeDownload
{
	public ServiceJsonDownload(String url, ContentServiceObserver contentServiceObserver)
	{
		super(url, ContentDataType.JSON, contentServiceObserver);
	}
	
	@Override
	public ServiceContentTypeDownload getCopyOfMe(ContentServiceObserver contentServiceObserver)
	{
		return new ServiceJsonDownload(this.getUrl(), contentServiceObserver);
	}
	
	private String getJsonAsString()
	{
		try
		{
			return new String(getData(), "UTF-8");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public Object getJson(Type type)
	{
		Gson gson = new Gson();
		return gson.fromJson(getJsonAsString(), type);
	}
}
