package com.friki.mbuthia.propertyfindapp.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;

import com.friki.mbuthia.propertyfindapp.AllProperties;
import com.friki.mbuthia.propertyfindapp.R;
import com.friki.mbuthia.propertyfindapp.database.LocationDatabase;
import com.friki.mbuthia.propertyfindapp.database.LocationEntry;
import com.friki.mbuthia.propertyfindapp.model.LocationNames;
import com.friki.mbuthia.propertyfindapp.model.MainViewModel;
import com.friki.mbuthia.propertyfindapp.model.Property;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class LocationPreferences extends ListPreference {
    private final String TAG = LocationPreferences.class.getSimpleName();
    private Context mContext = ApplicationContextProvider.getContext();
    private LocationDatabase mDb = LocationDatabase.getInstance(mContext);
    List<String> list = new ArrayList<>();

    public LocationPreferences(Context context, AttributeSet attrs) {
        super(context, attrs);
        settingEntryValues();
        setValueIndex(initializeIndex());
    }
    public LocationPreferences(Context context) {
        this(context, null);

    }


    private int initializeIndex() {
        return 0;
    }

    private void settingEntryValues() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<LocationEntry> locations = mDb.locationDao().loadAllEntries();
                list.add(mContext.getString(R.string.pref_location_0_value));
                for( LocationEntry location : locations){
                    String locationString = location.getLocation();
                    list.add(locationString);
                }
                CharSequence[] names = list.toArray(new String[0]);
                setEntries(names);
                setEntryValues(names);
            }
        });
    }
}
