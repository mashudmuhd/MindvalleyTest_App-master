package com.mindvalley.view.views.details;

import com.mindvalley.model.MasterDetails;
import com.mindvalley.model.UserDetails;

/**
 * This interface is used for pin detail view activities.
 *
 * @author SandeepD
 * @version 1.0
 */
interface PinDetailsView
{

    void renderPhoto(String full);

    void setBackgroundColor(String color);

    void renderUserDetails(UserDetails user);

    void renderPhotoDetails(MasterDetails currentData);

    void renderCategories(String displayCategories);
}
