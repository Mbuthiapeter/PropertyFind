package com.friki.mbuthia.propertyfindapp.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.friki.mbuthia.propertyfindapp.database.ListingEntry;
import com.friki.mbuthia.propertyfindapp.database.LocationDatabase;

public class AddListingViewModel extends ViewModel {
    private LiveData<ListingEntry> entry;
    public AddListingViewModel(LocationDatabase database, int entryId) {
        entry = database.listingDao().loadEntryById(entryId);
    }

    public LiveData<ListingEntry> getEntry() {
        return entry;
    }
}
