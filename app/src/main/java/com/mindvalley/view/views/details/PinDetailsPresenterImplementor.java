package com.mindvalley.view.views.details;

import android.annotation.SuppressLint;

import com.mindvalley.model.CategoryDetails;
import com.mindvalley.model.MasterDetails;

import java.util.List;

/**
 * Pin details presenter implementation.
 * @author Sandeep D
 *
 */
class PinDetailsPresenterImplementor implements PinDetailsPresenter
{
	private PinDetailsView detailsView;
	
	PinDetailsPresenterImplementor(PinDetailsView detailsView)
	{
		this.detailsView = detailsView;
	}
	
	@Override
	public void renderDetails(MasterDetails currentData)
	{
		if (detailsView != null)
		{
			if (currentData != null)
			{
				detailsView.renderPhoto(currentData.getUrlDetails().getFull());
				detailsView.setBackgroundColor(currentData.getColor());
				detailsView.renderUserDetails(currentData.getUser());
				detailsView.renderPhotoDetails(currentData);
				detailsView.renderCategories(getDisplayCategories(currentData.getCategories()));
			}
		}
	}
	
	@SuppressLint("DefaultLocale")
	private String getDisplayCategories(List<CategoryDetails> categories)
	{
		String displayCategories = "";
		for (CategoryDetails categoriesDetailsResponse :
				categories)
		{
			if (displayCategories.trim().equals(""))
			{
				displayCategories = String.format("%s (%d)", categoriesDetailsResponse.getTitle(), categoriesDetailsResponse.getPhotoCount());
			}
			else
			{
				displayCategories = String.format("%s/%s", displayCategories, String.format("%s (%d)", categoriesDetailsResponse.getTitle(), categoriesDetailsResponse.getPhotoCount()));
			}
		}
		return displayCategories;
	}
}
