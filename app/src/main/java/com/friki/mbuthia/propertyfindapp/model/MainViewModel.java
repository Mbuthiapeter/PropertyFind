package com.friki.mbuthia.propertyfindapp.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.friki.mbuthia.propertyfindapp.database.ListingEntry;
import com.friki.mbuthia.propertyfindapp.database.LocationDatabase;
import com.friki.mbuthia.propertyfindapp.database.LocationEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {// Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private List<LocationEntry> entries;
    private List<ListingEntry> listings;

    public MainViewModel(@NonNull Application application) {
        super(application);
        LocationDatabase database = LocationDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the entries from the DataBase");
        entries = database.locationDao().loadAllEntries();
        listings = database.listingDao().loadAllListings();
    }

    public List<LocationEntry> getEntries() {
        return entries;
    }

    public List<ListingEntry> getListings() {
        return listings;
    }

}
