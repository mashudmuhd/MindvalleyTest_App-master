package com.mindvalley.view.views.details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindvalley.R;
import com.mindvalley.appcontentloader.callback.ContentServiceObserver;
import com.mindvalley.appcontentloader.models.ServiceContentTypeDownload;
import com.mindvalley.appcontentloader.models.ServiceImageDownload;
import com.mindvalley.appcontentloader.utilities.ContentTypeServiceDownload;
import com.mindvalley.model.MasterDetails;
import com.mindvalley.model.UserDetails;
import com.mindvalley.utility.AlertUtil;
import com.mindvalley.utility.DateTimeUtil;
import com.mindvalley.utility.NetworkUtil;
import com.mindvalley.view.views.common.FragmentInstanceHandler;
import com.mindvalley.view.views.unsplash.UnsplashAccountFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Fragment is used to show photo details
 * <p>
 *
 * @author SandeepD
 * @version 1.0
 */
public class PinDetailsFragment extends Fragment implements PinDetailsView, View.OnClickListener
{
    @BindView(R.id.iv_full_view)
    ImageView imgvwFullView;
    @BindView(R.id.tv_title)
    TextView txtvwTitle;
    @BindView(R.id.tv_likes)
    TextView txtvwLikes;
    @BindView(R.id.xlinlayParent)
    LinearLayout linlayParent;
    @BindView(R.id.tv_image_dimensions)
    TextView txtvwImageDimensions;
    @BindView(R.id.tv_image_published_date)
    TextView txtvwImagePublishedDate;
    @BindView(R.id.tv_image_category)
    TextView txtvwImageCategory;
    @BindView(R.id.btn_view_profile)
    Button btnViewProfile;
    private FragmentInstanceHandler fragmentInstanceHandler;
    private PinDetailsPresenter detailsPresenter;
    private MasterDetails masterDetails;

    public static final String ARG_DATA = "data";

    private ContentTypeServiceDownload mProvider;


    public static PinDetailsFragment newInstance(MasterDetails masterDetails)
    {
        Bundle args = new Bundle();
        args.putParcelable(ARG_DATA, masterDetails);
        PinDetailsFragment fragment = new PinDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            masterDetails = bundle.getParcelable(ARG_DATA);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_details, vg, false);
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
        mProvider = ContentTypeServiceDownload.getInstance();
        btnViewProfile.setOnClickListener(this);
        detailsPresenter = new PinDetailsPresenterImplementor(this);
        detailsPresenter.renderDetails(masterDetails);
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
    public void renderPhoto(String full)
    {
        ServiceContentTypeDownload mDataTypeImageCancel = new ServiceImageDownload(masterDetails.getUrlDetails().getThumb(), new ContentServiceObserver()
        {
            @Override
            public void onStart(ServiceContentTypeDownload mDownloadDataType)
            {

            }

            @Override
            public void onSuccess(ServiceContentTypeDownload mDownloadDataType)
            {
                imgvwFullView.setImageBitmap(((ServiceImageDownload) mDownloadDataType).getImageBitmap());
            }

            @Override
            public void onFailure(ServiceContentTypeDownload mDownloadDataType, int statusCode, byte[] errorResponse, Throwable e)
            {
                imgvwFullView.setImageResource(R.drawable.no_image);
            }

            @Override
            public void onRetry(ServiceContentTypeDownload mDownloadDataType, int retryNo)
            {

            }
        });
        mProvider.getRequest(mDataTypeImageCancel);
    }

    @Override
    public void setBackgroundColor(String color)
    {
        linlayParent.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void renderUserDetails(UserDetails user)
    {
        txtvwTitle.setText(getString(R.string.clicked_by, user.getName()));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void renderPhotoDetails(MasterDetails currentData)
    {
        txtvwLikes.setText(String.format("%d", currentData.getLikes()));
        txtvwImageDimensions.setText(String.format("Image Dimensions - %d x %d", currentData.getWidth(), currentData.getHeight()));
        txtvwImagePublishedDate.setText(String.format("Creation Date - %s", DateTimeUtil.formattedDateFromDate(DateTimeUtil.getDateFromString("yyyy-MM-dd'T'HH:mm:ssXXX", masterDetails.getCreatedAt()), "dd-MM-yyyy")));
    }

    @Override
    public void renderCategories(String displayCategories)
    {
        txtvwImageCategory.setText(String.format("Image Category - %s", displayCategories));
    }

    @Override
    public void onClick(View view)
    {
        if (view == btnViewProfile)
        {

            if (NetworkUtil.isAirplaneModeWithNoWIFI(getActivity()) || !NetworkUtil.isNetworkAvailable(getActivity()))
            {
                AlertUtil.showToast(getActivity(), getString(R.string.network_error));

                fragmentInstanceHandler.changeFragment(PinDetailsFragment.this,
                        UnsplashAccountFragment.newInstance(masterDetails.getUser().getLinkDetails().getHtml()),
                        true);
            } else
            {
                fragmentInstanceHandler.changeFragment(PinDetailsFragment.this,
                        UnsplashAccountFragment.newInstance(masterDetails.getUser().getLinkDetails().getHtml()),
                        true);
            }

        }
    }
}
