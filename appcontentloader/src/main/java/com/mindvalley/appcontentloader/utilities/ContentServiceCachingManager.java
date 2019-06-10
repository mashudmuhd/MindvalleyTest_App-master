package com.mindvalley.appcontentloader.utilities;

import android.util.LruCache;

import com.mindvalley.appcontentloader.models.ServiceContentTypeDownload;

public class ContentServiceCachingManager
{
	private        int                                       maxCacheSize;
	private static ContentServiceCachingManager instance;
	private        LruCache<String, ServiceContentTypeDownload> mDownloadDataTypeCache;
	
	private ContentServiceCachingManager()
	{
		// Get max available VM memory, exceeding this amount will throw an
		// OutOfMemory exception. Stored in kilobytes as LruCache takes an
		// int in its constructor.
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// Use 1/8th of the available memory for this memory cache.
		maxCacheSize = maxMemory / 8; // 4 * 1024 * 1024; // 4MiB
		mDownloadDataTypeCache = new LruCache<>(maxCacheSize);
	}
	
	public static ContentServiceCachingManager getInstance()
	{
		if (instance == null)
		{
			instance = new ContentServiceCachingManager();
		}
		return instance;
	}
	
	public ServiceContentTypeDownload getMDownloadDataType(String key)
	{
		return mDownloadDataTypeCache.get(key);
	}
	
	public boolean putMDownloadDataType(String key, ServiceContentTypeDownload serviceContentTypeDownload)
	{
		return mDownloadDataTypeCache.put(key, serviceContentTypeDownload) != null;
	}
	
	public void clearTheCash()
	{
		mDownloadDataTypeCache.evictAll();
	}
}
