package com.mindvalley.appcontentloader.utilities;

import com.mindvalley.appcontentloader.callback.ContentServiceStatusObserver;
import com.mindvalley.appcontentloader.models.ServiceContentTypeDownload;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

class ContentServiceSyncTaskManager
{
	public AsyncHttpClient get(final ServiceContentTypeDownload serviceContentTypeDownload, final ContentServiceStatusObserver contentServiceStatusObserver)
	{
		AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
		client.get(serviceContentTypeDownload.getUrl(), new AsyncHttpResponseHandler()
		{
			@Override
			public void onStart()
			{
				serviceContentTypeDownload.getContentServiceObserver().onStart(serviceContentTypeDownload);
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response)
			{
				// called when response HTTP status is "200 OK"
				serviceContentTypeDownload.setData(response);
				serviceContentTypeDownload.getContentServiceObserver().onSuccess(serviceContentTypeDownload);
				// Successfull response from server
				contentServiceStatusObserver.setDone(serviceContentTypeDownload);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e)
			{
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)
				serviceContentTypeDownload.getContentServiceObserver().onFailure(serviceContentTypeDownload, statusCode, errorResponse, e);
				// Failure Response from server
				contentServiceStatusObserver.onFailure(serviceContentTypeDownload);
			}
			
			@Override
			public void onRetry(int retryNo)
			{
				// Retry the failed request
				serviceContentTypeDownload.getContentServiceObserver().onRetry(serviceContentTypeDownload, retryNo);
			}
			
			@Override
			public void onCancel()
			{
				super.onCancel();
				// Cancel the request
				contentServiceStatusObserver.setCancelled(serviceContentTypeDownload);
			}
		});
		return client;
	}
}


