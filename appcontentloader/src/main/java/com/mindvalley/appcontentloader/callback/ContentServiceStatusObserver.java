package com.mindvalley.appcontentloader.callback;

import com.mindvalley.appcontentloader.models.ServiceContentTypeDownload;

public interface ContentServiceStatusObserver
{
	void setDone(ServiceContentTypeDownload serviceContentTypeDownload);
	
	void setCancelled(ServiceContentTypeDownload serviceContentTypeDownload);
	
	void onFailure(ServiceContentTypeDownload serviceContentTypeDownload);
}
