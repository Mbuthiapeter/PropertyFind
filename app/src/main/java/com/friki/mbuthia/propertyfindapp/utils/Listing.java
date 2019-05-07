package com.friki.mbuthia.propertyfindapp.utils;

import android.util.Log;
import android.view.View;

import com.friki.mbuthia.propertyfindapp.model.Property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_EMAIL;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_MANAGER;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_PHONE;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_PHOTO;

public class Listing {
    public static String KEY_URL = "http://www.propertyfind.co.ke/App/";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_PROPERTIES = "properties";
    public static final String TAG_TECHNICIANS = "technicians";
    public static final String KEY_TO_LET = "toLet";
    public static final String KEY_FOR_SALE = "forSale";
    public static final String KEY_LISTING = "listing";

    public Listing() {
    }

    public ArrayList<Property> copyList(ArrayList<Property> mPropertyList){
        ArrayList<Property> mSortedList = new ArrayList<>();
        for(int i=0; i < mPropertyList.size(); i++ ){
            Property p = mPropertyList.get(i);
            mSortedList.add(p);
        }
        return mSortedList;
    }



   public long getMaxAmount(String p){
       long maxAmount = 0;
           switch (p){
               case "1":
                   maxAmount = 20000;
                   break;
               case "2":
                   maxAmount = 30000;
                   break;
               case "3":
                   maxAmount = 40000;
                   break;
               case "4":
                   maxAmount = 50000;
                   break;
               case "5":
                   maxAmount = 60000;
                   break;
               case "6":
                   maxAmount = 70000;
                   break;
               case "7":
                   maxAmount = 80000;
                   break;
               case "8":
                   maxAmount = 90000;
                   break;
               case "9":
                   maxAmount = 100000;
                   break;
               default: maxAmount = 0;
           }
        return maxAmount;
   }

    public long getMinAmount(String p){
        long minAmount = 0;
        switch (p){
            case "1":
                minAmount = 10000;
                break;
            case "2":
                minAmount = 20001;
                break;
            case "3":
                minAmount = 30001;
                break;
            case "4":
                minAmount = 40001;
                break;
            case "5":
                minAmount = 50001;
                break;
            case "6":
                minAmount = 60001;
                break;
            case "7":
                minAmount = 70001;
                break;
            case "8":
                minAmount = 80001;
                break;
            case "9":
                minAmount = 100001;
                break;
                default: minAmount = 0;
        }
        return minAmount;
    }

    public int getSelectedBedRooms(String p){
        int bedrooms = 0;
        switch (p){
            case "1":
                bedrooms = 1;
                break;
            case "2":
                bedrooms = 2;
                break;
            case "3":
                bedrooms = 3;
                break;
            case "4":
                bedrooms = 4;
                break;
            case "5":
                bedrooms = 5;
                break;
            default: bedrooms = 0;
        }
        return bedrooms;
    }

    public int getSelectedType(String p){
        int type = 0;
        switch (p){
            case "1":
                type = 1;
                break;
            case "2":
                type = 2;
                break;
            case "3":
                type = 3;
                break;
            case "4":
                type = 4;
                break;
            case "5":
                type = 5;
                break;
            case "6":
                type = 6;
                break;
            case "7":
                type = 7;
                break;
            default: type = 0;
        }
        return type;
    }

}


