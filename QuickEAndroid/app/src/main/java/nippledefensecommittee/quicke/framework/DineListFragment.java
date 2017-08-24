package nippledefensecommittee.quicke.framework;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nippledefensecommittee.quicke.R;
import utility.Business;
import utility.DineList;

/**
 * Created by Devin on 8/23/2017.
 */

public class DineListFragment extends Fragment {
    private static final String TAG = DineListFragment.class.getName();
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_dinelist, container, false);
        initializeViewPager(view);
        return view;
    }

    @Override
    public String toString(){
        return "DineListFragment";
    }

    private void initializeViewPager(View view){
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.dinelist_viewpager);
        viewPager.setAdapter(new DinePagerAdapter(getContext()));
    }

    private class DinePagerAdapter extends PagerAdapter{
        private Context mContext;
        private static final int mDineListPage = R.layout.dinelist_page;

        DinePagerAdapter(Context context){
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position){
            Business business = DineList.get(position);
            ViewGroup layout = (ViewGroup) LayoutInflater
                    .from(mContext)
                    .inflate(mDineListPage, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.viewpage_image);
            Glide.with(mContext)
                    .load(business.getImageUrl())
                    .into(imageView);
            ((TextView)layout.findViewById(R.id.viewpage_title)).setText(business.getName());

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view){
            collection.removeView((View) view);
        }

        @Override
        public int getCount(){
            return DineList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object){
            return view == object;
        }
    }
}