package nippledefensecommittee.quicke.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lorentzos.flingswipe.FlingCardListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import design.FlingAdapter;
import nippledefensecommittee.quicke.R;
import sync.YelpSearchIntentService;
import utility.Business;
import utility.BusinessList;
import utility.Helper;

/**
 * Created by Devin on 8/18/2017.
 */

public class BrowseFragment extends Fragment {
    private static final String TAG = BrowseFragment.class.getName();
    public static final String BUSINESS_BROADCAST = "myBusinessListBroadcastListener";
    private Context mContext;

    private SwipeFlingAdapterView mFlingContainer;
    private FlingAdapter mAdapter;
    private ProgressBar mProgress;

    private static boolean mUpdatingList = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            initializeBroadcastReceiver();
        }
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        mProgress = (ProgressBar) view.findViewById(R.id.browse_progress);
        initializeSwipeFling(view, savedInstanceState);
        if(!mAdapter.isEmpty()){
            mProgress.setVisibility(View.GONE);
            mFlingContainer.setVisibility(View.VISIBLE);
        }
        initializeButtons(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public String toString(){
        return "BrowseFragment";
    }

    private void initializeBroadcastReceiver(){
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mUpdatingList = false;
                mAdapter.notifyDataSetChanged();
                mProgress.setVisibility(View.GONE);
                mFlingContainer.setVisibility(View.VISIBLE);

            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(
                receiver, new IntentFilter(BUSINESS_BROADCAST));
    }

    private void initializeSwipeFling(View view, Bundle savedInstanceState){
        mFlingContainer =
                (SwipeFlingAdapterView) view.findViewById(R.id.browse_swipefling);

        mAdapter = new FlingAdapter(this, savedInstanceState);

        mFlingContainer.setAdapter(mAdapter);
        mFlingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener(){
            @Override
            public void removeFirstObjectInAdapter(){
                BusinessList.removeBusiness(0);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject){
            }

            @Override
            public void onRightCardExit(Object dataObject){
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter){
                if(!mUpdatingList) {
                    if(Helper.isInternetAvailable(getContext())) {
                        mUpdatingList = true;
                        Intent yelpSync = new Intent(
                                mContext,
                                YelpSearchIntentService.class);
                        yelpSync.putExtra(
                                YelpSearchIntentService.OFFSET_MULTIPLIER,
                                YelpSearchIntentService.CURRENT_OFFSET_INCREMENT);
                        getContext().startService(yelpSync);
                    }else{
                        Toast.makeText(
                                getContext(),
                                getString(R.string.browse_nointernet),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }

            @Override
            public void onScroll(float scroll){

            }
        });
    }

    private void initializeButtons(View view){
        AppCompatImageButton yesButton =
                (AppCompatImageButton) view.findViewById(R.id.browse_button_yes);
        AppCompatImageButton noButton =
                (AppCompatImageButton) view.findViewById(R.id.browse_button_no);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlingContainer.getTopCardListener().selectRight();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlingContainer.getTopCardListener().selectLeft();
            }
        });
    }

    public static void setUpdatingList(boolean b){
        mUpdatingList = b;
    }
}