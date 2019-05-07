package com.friki.mbuthia.propertyfindapp.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.friki.mbuthia.propertyfindapp.database.LocationDatabase;
import com.friki.mbuthia.propertyfindapp.database.LocationEntry;

public class AddEntryViewModel extends ViewModel {
    private LiveData<LocationEntry> entry;

    public AddEntryViewModel(LocationDatabase database, int entryId) {
        entry = database.locationDao().loadEntryById(entryId);
    }

    public LiveData<LocationEntry> getEntry() {
        return entry;
    }
}
