package com.mindvalley.view.views.pinboardlist;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mindvalley.R;
import com.mindvalley.model.MasterDetails;
import com.mindvalley.utility.AlertUtil;
import com.mindvalley.utility.NetworkUtil;
import com.mindvalley.view.views.common.DialogListener;
import com.mindvalley.view.views.common.FragmentInstanceHandler;
import com.mindvalley.view.views.common.ItemActionListener;
import com.mindvalley.view.views.details.PinDetailsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Fragment is used to show the Users List.
 * <p>
 *
 * @author SandeepD
 * @version 1.0
 */
public class PinboardListFragment extends Fragment implements PinboardListView, ItemActionListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, DialogListener
{
    FragmentInstanceHandler fragmentInstanceHandler;
    PinboardListPresenter listingPresenter;
    @BindView(R.id.xcVwUsers)
    RecyclerView rcVwUsers;
    @BindView(R.id.xswpLayUsersList)
    SwipeRefreshLayout swpLayUsersList;
    @BindView(R.id.ximgvwClearCache)
    FloatingActionButton fabClearCache;

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;


    private final int CLEAR_CACHE = 1;

    PinboardListAdapter listingAdapter;


    public static PinboardListFragment newInstance()
    {

        Bundle args = new Bundle();

        PinboardListFragment fragment = new PinboardListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_listing, vg, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        initObjects();
    }

    /**
     * This function is used to initialise the objects that are going to be used in this fragment
     */
    public void initObjects()
    {
        listingPresenter = new PinboardListPresenterImplementor(getActivity(), this);
        swpLayUsersList.setOnRefreshListener(this);
        fabClearCache.setOnClickListener(this);
        swpLayUsersList.setEnabled(false);
        pbLoading.setVisibility(View.VISIBLE);
        listingPresenter.fetchUsers();
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        fragmentInstanceHandler = (FragmentInstanceHandler) getActivity();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        fragmentInstanceHandler = (FragmentInstanceHandler) getActivity();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void renderUserList(ArrayList<MasterDetails> users)
    {

        listingAdapter = new PinboardListAdapter(getActivity(), new ArrayList<MasterDetails>(), this);

        rcVwUsers.setAdapter(listingAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        rcVwUsers.setLayoutManager(linearLayoutManager);

        for (MasterDetails detailsResponse : users)
        {
            listingAdapter.add(detailsResponse);
        }
        if (swpLayUsersList.isRefreshing())
            swpLayUsersList.setRefreshing(false);
        swpLayUsersList.setEnabled(true);
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void displayErrorMessage(String errorMessage)
    {
        AlertUtil.showToast(getActivity(), errorMessage);

        swpLayUsersList.setEnabled(true);
    }

    @Override
    public void onItemClicked(Object currentObject, int position)
    {

        final MasterDetails currentData = (MasterDetails) currentObject;

        if (NetworkUtil.isAirplaneModeWithNoWIFI(getActivity()) || !NetworkUtil.isNetworkAvailable(getActivity()))
        {
            AlertUtil.showToast(getActivity(), getString(R.string.network_error));
        } else
        {
            fragmentInstanceHandler.changeFragment(PinboardListFragment.this,
                    PinDetailsFragment.newInstance(currentData), true);
        }


    }

    @Override
    public void onRefresh()
    {
        //Pull to Refresh is called
        listingPresenter.fetchUsers();
    }

    @Override
    public void onClick(View view)
    {
        if (view == fabClearCache)
        {
            AlertUtil.showAlertDialogMultipleOptions(getActivity(), CLEAR_CACHE, this, getResources().getString(R.string.clear_cache), getResources().getString(R.string.clear_cache_description), getResources().getString(R.string.yes), getResources().getString(R.string.no));
        }
    }

    @Override
    public void onPositiveAction(int dialogID, Object updatedData)
    {
        listingPresenter.clearCache();
        listingPresenter.fetchUsers();
    }

    @Override
    public void onNegativeAction(int dialogID, Object updatedData)
    {

    }
}
