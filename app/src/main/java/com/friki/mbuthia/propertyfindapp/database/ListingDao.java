package com.friki.mbuthia.propertyfindapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.friki.mbuthia.propertyfindapp.utils.Listing;

import java.util.List;

@Dao
public interface ListingDao {
    @Query("SELECT * FROM listing ORDER BY id ASC")
    List<ListingEntry> loadAllListings();

    @Insert
    void insertEntry(ListingEntry listingEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(ListingEntry listingEntry);

    @Delete
    void deleteAll(ListingEntry listingEntry);

    @Query("DELETE FROM listing")
    public void nukeEntries();

    @Query("SELECT * FROM listing WHERE id = :id")
    LiveData<ListingEntry> loadEntryById(int id);
}
