package com.mindvalley.view.views.pinboardlist;

import com.mindvalley.model.MasterDetails;
import com.mindvalley.view.views.common.CommonView;

import java.util.ArrayList;

/**
 * This interface is used for pinboard activities.
 *
 * @author SandeepD
 * @version 1.0
 */
interface PinboardListView extends CommonView
{

    void renderUserList(ArrayList<MasterDetails> users);
}
