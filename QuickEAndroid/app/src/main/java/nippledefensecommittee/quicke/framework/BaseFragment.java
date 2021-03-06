package nippledefensecommittee.quicke.framework;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import nippledefensecommittee.quicke.R;
import utility.ColorState;

/**
 * Created by Devin on 7/26/2017.
 */

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getName();
    private ActionBar mMainActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mMainActionBar = ((MainActivity)activity).getSupportActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        initializeButtons(view);
        initializeSeekBar(view);
        return view;
    }

    /**
     * We clear MealSelection here, because if the user restarts their meal selection then
     * we want to wipe the mealselection category list. I believe that this is the best place
     * to do it.
     */
    @Override
    public void onStart(){
        super.onStart();
        MainActivity.MealSelection.clear();
        try {
            mMainActionBar.setTitle(R.string.app_name);
        }catch(NullPointerException e){
            Log.e(TAG, "NullPointerException, title not changed");
        }
    }

    @Override
    public String toString(){
        return "BaseFragment";
    }

    /**
     * Method designed to move code out of onCreateView. Simply assigns to the necessary buttons
     * the correct onButtonClick methods
     *
     * @param view - View for finding the buttons within the view
     */
    private void initializeButtons(View view) {
        final AppCompatImageButton button_eat = (AppCompatImageButton)
                view.findViewById(R.id.main_button_eat);
        button_eat.setActivated(MainActivity.QuickEUsage.getQuickEUsageAt(0));
        final AppCompatImageButton button_drink = (AppCompatImageButton)
                view.findViewById(R.id.main_button_drink);
        button_drink.setActivated(MainActivity.QuickEUsage.getQuickEUsageAt(1));

        final AppCompatButton button_price1 = (AppCompatButton)
                view.findViewById(R.id.main_button_price_1);
        button_price1.setActivated(MainActivity.PriceRange.getPriceIndicationAt(0));
        final AppCompatButton button_price2 = (AppCompatButton)
                view.findViewById(R.id.main_button_price_2);
        button_price2.setActivated(MainActivity.PriceRange.getPriceIndicationAt(1));
        final AppCompatButton button_price3 = (AppCompatButton)
                view.findViewById(R.id.main_button_price_3);
        button_price3.setActivated(MainActivity.PriceRange.getPriceIndicationAt(2));
        final AppCompatButton button_price4 = (AppCompatButton)
                view.findViewById(R.id.main_button_price_4);
        button_price4.setActivated(MainActivity.PriceRange.getPriceIndicationAt(3));

        final AppCompatImageButton button_go = (AppCompatImageButton)
                view.findViewById(R.id.main_button_go);

        ColorStateList cswBG = ColorState.getButtonColorStateListBG(getContext());
        button_eat.setSupportBackgroundTintList(cswBG);
        button_drink.setSupportBackgroundTintList(cswBG);
        button_price1.setSupportBackgroundTintList(cswBG);
        button_price2.setSupportBackgroundTintList(cswBG);
        button_price3.setSupportBackgroundTintList(cswBG);
        button_price4.setSupportBackgroundTintList(cswBG);

        ColorStateList cswText = ColorState.getButtonColorStateListText(getContext());
        button_price1.setTextColor(cswText);
        button_price2.setTextColor(cswText);
        button_price3.setTextColor(cswText);
        button_price4.setTextColor(cswText);

        Picasso.with(getContext()).load(R.drawable.eat).into(button_eat);
        Picasso.with(getContext()).load(R.drawable.drink).into(button_drink);

        button_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean activated = button_eat.isActivated();
                button_eat.setActivated(!activated);
                MainActivity.QuickEUsage.updateQuickEUsage(0, button_eat.isActivated());
            }
        });
        button_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean activated = button_drink.isActivated();
                button_drink.setActivated(!activated);
                MainActivity.QuickEUsage.updateQuickEUsage(1, button_drink.isActivated());
            }
        });

        button_price1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean activated = button_price1.isActivated();
                button_price1.setActivated(!activated);
                MainActivity.PriceRange.updatePriceIndication(0, button_price1.isActivated());
            }
        });
        button_price2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean activated = button_price2.isActivated();
                button_price2.setActivated(!activated);
                MainActivity.PriceRange.updatePriceIndication(1, button_price2.isActivated());
            }
        });
        button_price3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean activated = button_price3.isActivated();
                button_price3.setActivated(!activated);
                MainActivity.PriceRange.updatePriceIndication(2, button_price3.isActivated());
            }
        });
        button_price4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean activated = button_price4.isActivated();
                button_price4.setActivated(!activated);
                MainActivity.PriceRange.updatePriceIndication(3, button_price4.isActivated());
            }
        });

        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MainActivity.QuickEUsage.hasSelection()) {
                    Toast.makeText(getContext(), getResources()
                            .getText(R.string.select_QuickEUsage), Toast.LENGTH_LONG)
                            .show();
                }else if(!MainActivity.PriceRange.hasSelection()){
                    Toast.makeText(getContext(), getResources()
                            .getText(R.string.select_PriceRange), Toast.LENGTH_LONG)
                            .show();
                }else{
                    Fragment fragment = new FoodSelectFragment();
                    FragmentChangeListener fcl = (FragmentChangeListener) getActivity();
                    fcl.replaceFragment(fragment, true);
                }
            }
        });
    }

    private void initializeSeekBar(View view) {
        AppCompatSeekBar seekBar = (AppCompatSeekBar) view.findViewById(R.id.main_seekbar_distance);
        final TextView seekBarTV = (TextView) view.findViewById(R.id.main_seekbar_indicator);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String seekBarTV_text;
                int realProgress;
                if(progress < 1){
                    seekBarTV_text = getString(R.string.base_seekbar_range_default);
                    seekBarTV.setText(seekBarTV_text);
                    realProgress = 1;
                }else{
                    realProgress = progress*5;
                    seekBarTV_text = (realProgress) + " " +
                            getString(R.string.base_seekbar_range_plural);
                    seekBarTV.setText(seekBarTV_text);
                }
                MainActivity.AreaRadius.setRadius(realProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
