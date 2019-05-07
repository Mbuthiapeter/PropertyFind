package com.friki.mbuthia.propertyfindapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "listing")
public class ListingEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int listingId;
    private String listing;

    public ListingEntry(int id, int listingId, String listing) {
        this.id = id;
        this.listingId = listingId;
        this.listing = listing;
    }

    @Ignore
    public ListingEntry(int listingId, String listing) {
        this.listingId = listingId;
        this.listing = listing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public String getListing() {
        return listing;
    }

    public void setListing(String listing) {
        this.listing = listing;
    }
}
