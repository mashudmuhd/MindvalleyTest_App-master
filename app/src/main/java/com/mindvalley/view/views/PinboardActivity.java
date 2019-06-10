package com.mindvalley.view.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mindvalley.R;
import com.mindvalley.view.views.common.FragmentInstanceHandler;
import com.mindvalley.view.views.details.PinDetailsFragment;
import com.mindvalley.view.views.pinboardlist.PinboardListFragment;
import com.mindvalley.view.views.unsplash.UnsplashAccountFragment;

public class PinboardActivity extends AppCompatActivity implements FragmentInstanceHandler
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinboard);
        changeFragment(null, PinboardListFragment.newInstance(), false);
    }

    /**
     * This function is used to get the current Top Fragment from the Back stack
     *
     * @return - The Fragment on the Top
     */
    private Fragment getCurrentTopFragment()
    {

        FragmentManager fragmentManager = getFragmentManager();
        //Performs any previous pending operations in the queue for the fragments
        fragmentManager.executePendingTransactions();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentManager.findFragmentByTag(fragmentTag);
    }

    @Override
    public void changeFragment(Fragment fromFragment, Fragment toFragment, boolean addToBackStack)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
       // ft.setCustomAnimations(R.animator.custom_fade_in, R.animator.custom_fade_out, R.animator.custom_fade_in, R.animator.custom_fade_out);

        ft.replace(R.id.fragmentHolder, toFragment, toFragment.getClass().getName());
        if (addToBackStack && (fromFragment != null))
        {
            ft.addToBackStack(fromFragment.getClass().getName()); // we are following standard practice of giving fromFrag class name as name for the back stack state.
        }

        ft.commitAllowingStateLoss();
    }
}