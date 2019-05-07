package com.friki.mbuthia.propertyfindapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM locations ORDER BY location ASC")
    List<LocationEntry> loadAllEntries();

    @Insert
    void insertEntry(LocationEntry locationEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(LocationEntry locationEntry);

    @Delete
    void deleteAll(LocationEntry locationEntry);

    @Query("DELETE FROM locations")
    public void nukeEntries();

    @Query("SELECT * FROM locations WHERE id = :id")
    LiveData<LocationEntry> loadEntryById(int id);
}
