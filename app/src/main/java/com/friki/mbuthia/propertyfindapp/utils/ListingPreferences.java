package com.friki.mbuthia.propertyfindapp.utils;

import android.content.Context;
import android.support.v7.preference.ListPreference;
import android.util.AttributeSet;

import com.friki.mbuthia.propertyfindapp.R;
import com.friki.mbuthia.propertyfindapp.database.ListingEntry;
import com.friki.mbuthia.propertyfindapp.database.LocationDatabase;
import com.friki.mbuthia.propertyfindapp.database.LocationEntry;

import java.util.ArrayList;
import java.util.List;

public class ListingPreferences extends ListPreference {
    private final String TAG = ListingPreferences.class.getSimpleName();
    private Context mContext = ApplicationContextProvider.getContext();
    private LocationDatabase mDb = LocationDatabase.getInstance(mContext);
    List<String> list = new ArrayList<>();
    List<String> listValues = new ArrayList<>();
    public ListingPreferences(Context context, AttributeSet attrs) {
        super(context, attrs);
        settingEntryValues();
        setValueIndex(initializeIndex());
    }
    public ListingPreferences(Context context) {
        this(context, null);

    }
    private int initializeIndex() {
        return 0;
    }

    private void settingEntryValues() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<ListingEntry> listings = mDb.listingDao().loadAllListings();
                list.add(mContext.getString(R.string.pref_listing_value));
                listValues.add("0");
                for( ListingEntry listing : listings){
                    String listingValue = String.valueOf(listing.getListingId());
                    String listingString = listing.getListing();
                    list.add(listingString);
                    listValues.add(listingValue);
                }
                CharSequence[] names = list.toArray(new String[0]);
                CharSequence[] values = listValues.toArray(new String[0]);
                setEntries(names);
                setEntryValues(values);
            }
        });
    }
}
