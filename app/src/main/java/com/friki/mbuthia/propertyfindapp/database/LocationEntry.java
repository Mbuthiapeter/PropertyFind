package com.friki.mbuthia.propertyfindapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "locations")
public class LocationEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String location;

    public LocationEntry(int id, String location) {
        this.id = id;
        this.location = location;
    }

    @Ignore
    public LocationEntry(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
