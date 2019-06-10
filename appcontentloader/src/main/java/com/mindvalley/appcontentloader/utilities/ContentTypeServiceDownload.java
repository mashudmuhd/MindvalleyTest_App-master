package com.mindvalley.appcontentloader.utilities;

import android.util.Log;

import com.mindvalley.appcontentloader.callback.ContentServiceObserver;
import com.mindvalley.appcontentloader.callback.ContentServiceStatusObserver;
import com.mindvalley.appcontentloader.models.ServiceContentTypeDownload;
import com.loopj.android.http.AsyncHttpClient;

import java.util.HashMap;
import java.util.LinkedList;

public class ContentTypeServiceDownload
{
	private static ContentTypeServiceDownload instance;
	private HashMap<String, LinkedList<ServiceContentTypeDownload>> allRequestsByKey  = new HashMap<>();
	private HashMap<String, AsyncHttpClient>                     allRequestsClient = new HashMap<>();
	private ContentServiceCachingManager contentServiceCachingManager;
	
	private ContentTypeServiceDownload()
	{
		contentServiceCachingManager = ContentServiceCachingManager.getInstance();
	}
	
	public static ContentTypeServiceDownload getInstance()
	{
		if (instance == null)
		{
			instance = new ContentTypeServiceDownload();
		}
		return instance;
	}
	
	public void getRequest(final ServiceContentTypeDownload serviceContentTypeDownload)
	{
		final String mKey = serviceContentTypeDownload.getKeyMD5();
		// Check if exist in the cache
		ServiceContentTypeDownload serviceContentTypeDownloadFromCache = contentServiceCachingManager.getMDownloadDataType(mKey);
		if (serviceContentTypeDownloadFromCache != null)
		{
			serviceContentTypeDownloadFromCache.comeFrom = "Cache";
			// call interface
			ContentServiceObserver contentServiceObserver = serviceContentTypeDownload.getContentServiceObserver();
			contentServiceObserver.onStart(serviceContentTypeDownloadFromCache);
			contentServiceObserver.onSuccess(serviceContentTypeDownloadFromCache);
			return;
		}
		// This will run if two request come for same url
		if (allRequestsByKey.containsKey(mKey))
		{
			serviceContentTypeDownload.comeFrom = "Cache";
			allRequestsByKey.get(mKey).add(serviceContentTypeDownload);
			return;
		}
		// Put it in the allRequestsByKey to manage it after done or cancel
		if (allRequestsByKey.containsKey(mKey))
		{
			allRequestsByKey.get(mKey).add(serviceContentTypeDownload);
		}
		else
		{
			LinkedList<ServiceContentTypeDownload> lstMDDataType = new LinkedList<>();
			lstMDDataType.add(serviceContentTypeDownload);
			allRequestsByKey.put(mKey, lstMDDataType);
		}
		// Create new WebCallDataTypeDownload by clone it from the parameter
		ServiceContentTypeDownload newWebCallDataTypeDownload = serviceContentTypeDownload.getCopyOfMe(new ContentServiceObserver()
		{
			@Override
			public void onStart(ServiceContentTypeDownload mDownloadDataType)
			{
				for (ServiceContentTypeDownload m : allRequestsByKey.get(mDownloadDataType.getKeyMD5()))
				{
					m.setData(mDownloadDataType.getData());
					m.getContentServiceObserver().onStart(m);
				}
			}
			
			@Override
			public void onSuccess(ServiceContentTypeDownload mDownloadDataType)
			{
				for (ServiceContentTypeDownload m : allRequestsByKey.get(mDownloadDataType.getKeyMD5()))
				{
					m.setData(mDownloadDataType.getData());
					Log.e("HERRRR", m.comeFrom);
					m.getContentServiceObserver().onSuccess(m);
				}
				allRequestsByKey.remove(mDownloadDataType.getKeyMD5());
			}
			
			@Override
			public void onFailure(ServiceContentTypeDownload mDownloadDataType, int statusCode, byte[] errorResponse, Throwable e)
			{
				for (ServiceContentTypeDownload m : allRequestsByKey.get(mDownloadDataType.getKeyMD5()))
				{
					m.setData(mDownloadDataType.getData());
					m.getContentServiceObserver().onFailure(m, statusCode, errorResponse, e);
				}
				allRequestsByKey.remove(mDownloadDataType.getKeyMD5());
			}
			
			@Override
			public void onRetry(ServiceContentTypeDownload mDownloadDataType, int retryNo)
			{
				for (ServiceContentTypeDownload m : allRequestsByKey.get(mDownloadDataType.getKeyMD5()))
				{
					m.setData(mDownloadDataType.getData());
					m.getContentServiceObserver().onRetry(m, retryNo);
				}
			}
		});
		// Get from download manager
		final ContentServiceSyncTaskManager contentServiceSyncTaskManager = new ContentServiceSyncTaskManager();
		AsyncHttpClient client = contentServiceSyncTaskManager.get(newWebCallDataTypeDownload, new ContentServiceStatusObserver()
		{
			@Override
			public void setDone(ServiceContentTypeDownload mDownloadDataType)
			{
				// put in the cache when mark as done
				contentServiceCachingManager.putMDownloadDataType(mDownloadDataType.getKeyMD5(), mDownloadDataType);
				allRequestsClient.remove(mDownloadDataType.getKeyMD5());
			}
			
			@Override
			public void onFailure(ServiceContentTypeDownload mDownloadDataType)
			{
				allRequestsClient.remove(mDownloadDataType.getKeyMD5());
			}
			
			@Override
			public void setCancelled(ServiceContentTypeDownload mDownloadDataType)
			{
				allRequestsClient.remove(mDownloadDataType.getUrl());
			}
		});
		allRequestsClient.put(mKey, client);
	}
	
	public void cancelRequest(ServiceContentTypeDownload serviceContentTypeDownload)
	{
		if (allRequestsByKey.containsKey(serviceContentTypeDownload.getKeyMD5()))
		{
			allRequestsByKey.get(serviceContentTypeDownload.getKeyMD5()).remove(serviceContentTypeDownload);
			serviceContentTypeDownload.comeFrom = "cancelRequest";
			serviceContentTypeDownload.getContentServiceObserver().onFailure(serviceContentTypeDownload, 0, null, null);
		}
	}
	
	public boolean isQueueEmpty()
	{
		return allRequestsByKey.size() == 0;
	}
	
	public void clearTheCash()
	{
		contentServiceCachingManager.clearTheCash();
	}
}
