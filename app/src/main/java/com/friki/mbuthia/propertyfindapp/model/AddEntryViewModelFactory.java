package com.friki.mbuthia.propertyfindapp.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.friki.mbuthia.propertyfindapp.database.LocationDatabase;

public class AddEntryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final LocationDatabase mDb;
    private final int mEntryId;

    public AddEntryViewModelFactory(LocationDatabase database, int entryId) {
        mDb = database;
        mEntryId = entryId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class <T> modelClass) {
        return (T) new AddEntryViewModel(mDb, mEntryId);
    }
}
