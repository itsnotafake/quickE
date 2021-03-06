package utility;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import nippledefensecommittee.quicke.R;
import nippledefensecommittee.quicke.framework.BrowseFragment;

/**
 * Created by Devin on 8/17/2017.
 */

public class BusinessList {

    private static final String TAG = BusinessList.class.getName();
    private static ArrayList<Business> businessList;

    static{
        businessList = new ArrayList<>();
    }

    public static ArrayList<Business> getBusinessList(){
        return businessList;
    }

    public static void addBusiness(ArrayList<Business> aL, Business b){
        aL.add(b);
    }

    public static Business getBusiness(int position){
        return businessList.get(position);
    }

    public static void removeBusiness(int position){
        businessList.remove(position);
    }

    public static void removeLast(){
        removeBusiness(size()-1);
    }

    public static void clear(){
        businessList.clear();
    }

    public static int size(){
        return businessList.size();
    }

    public static void randomize(ArrayList<Business> aL){
        Collections.shuffle(aL);
    }

    public static void addBulk(String bulkList, Context context){
        ArrayList<Business> tempList = new ArrayList<>();
        try{
            JSONObject bulkListJSON = new JSONObject(bulkList);
            JSONArray businessListJSON = bulkListJSON.getJSONArray(
                    context.getString(R.string.businesslist_businesses));

            HashMap<String, String> businessMap = new HashMap<>();
            JSONObject businessJSON;
            JSONObject coordinatesJSON;
            String yelpId;
            String name;
            String image_url;
            String url;

            String rating;
            String reviewCount;
            String price;
            String phone_number;

            String latitude;
            String longitude;
            String distance;

            for(int i = 0; i < businessListJSON.length(); i++) {
                businessJSON = businessListJSON.getJSONObject(i);
                yelpId = businessJSON.getString(context.getString(R.string.businesslist_id));
                name = businessJSON.getString(context.getString(R.string.businesslist_name));
                image_url = businessJSON.getString(
                        context.getString(R.string.businesslist_imageurl));
                url = businessJSON.getString
                        (context.getString(R.string.businesslist_url));
                rating = String.valueOf(businessJSON.getDouble(
                        context.getString(R.string.businesslist_rating)));
                reviewCount = String.valueOf(businessJSON.getInt(
                        context.getString(R.string.businesslist_reviewCount)));
                price = businessJSON.getString(context.getString(R.string.businesslist_price));
                phone_number = businessJSON.getString(
                        context.getString(R.string.businesslist_phonenumber));
                distance = String.valueOf(businessJSON.getDouble(
                        context.getString(R.string.businesslist_distance)));

                coordinatesJSON = businessJSON.getJSONObject(
                        context.getString(R.string.businesslist_coordinates));
                latitude = String.valueOf(coordinatesJSON.getDouble(
                        context.getString(R.string.businesslist_latitude)));
                longitude = String.valueOf(coordinatesJSON.getDouble(
                        context.getString(R.string.businesslist_longitude)));

                businessMap.put(context.getString(R.string.businesslist_id), yelpId);
                businessMap.put(context.getString(R.string.businesslist_name), name);
                businessMap.put(context.getString(R.string.businesslist_imageurl), image_url);
                businessMap.put(context.getString(R.string.businesslist_url), url);
                businessMap.put(context.getString(R.string.businesslist_rating), rating);
                businessMap.put(context.getString(R.string.businesslist_reviewCount), reviewCount);
                businessMap.put(context.getString(R.string.businesslist_price), price);
                businessMap.put(
                        context.getString(R.string.businesslist_phonenumber), phone_number);
                businessMap.put(context.getString(R.string.businesslist_distance), distance);
                businessMap.put(context.getString(R.string.businesslist_latitude), latitude);
                businessMap.put(context.getString(R.string.businesslist_longitude), longitude);

                addBusiness(tempList, new Business(businessMap, context));
            }
            randomize(tempList);
            for(Business b : tempList){
                addBusiness(businessList, b);
            }
            Intent businessListBroadcast = new Intent(BrowseFragment.BUSINESS_BROADCAST);
            LocalBroadcastManager.getInstance(context).sendBroadcast(businessListBroadcast);
        }catch(JSONException e){
            Log.e(TAG, "JSONException handling bulkList in BusinessList... " + e);
        }
    }
}
