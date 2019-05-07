package com.friki.mbuthia.propertyfindapp.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Property {
    int id;
    String propertyNotation;
    int propertyTypeId;
    String propertyType;
    String propertyListing;
    int listing;
    String location;
    String generalLocation;
    long amount;
    int bedrooms;
    int showers;
    int car_parks;
    String description;
    int status;
    String manager;
    String manager_phone;
    String manager_email;
    String manager_photo;
    JSONArray uploads;
    private String mImageUrl;


    public Property(int id, String propertyNotation, int propertyTypeId, String propertyType, String location, long amount, int bedrooms, int showers, int car_parks, String description, int status, String manager, String manager_phone, String manager_email, String manager_photo, String mImageUrl, JSONArray uploads) {
        this.id = id;
        this.propertyNotation = propertyNotation;
        this.propertyTypeId = propertyTypeId;
        this.propertyType = propertyType;
        this.propertyListing = propertyListing;
        this.listing = listing;
        this.location = location;
        this.generalLocation = generalLocation;
        this.amount = amount;
        this.bedrooms = bedrooms;
        this.showers = showers;
        this.car_parks = car_parks;
        this.description = description;
        this.status = status;
        this.manager = manager;
        this.manager_phone = manager_phone;
        this.manager_email = manager_email;
        this.manager_photo = manager_photo;
        this.mImageUrl = mImageUrl;
        this.uploads = uploads;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPropertyNotation() {
        return propertyNotation;
    }

    public void setPropertyNotation(String propertyNotation) {
        this.propertyNotation = propertyNotation;
    }

    public int getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(int propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyListing() {
        return propertyListing;
    }

    public void setPropertyListing(String propertyListing) {
        this.propertyListing = propertyListing;
    }

    public int getListing() {
        return listing;
    }

    public void setListing(int listing) {
        this.listing = listing;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGeneralLocation() {
        return generalLocation;
    }

    public void setGeneralLocation(String generalLocation) {
        this.generalLocation = generalLocation;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getShowers() {
        return showers;
    }

    public void setShowers(int showers) {
        this.showers = showers;
    }

    public int getCar_parks() {
        return car_parks;
    }

    public void setCar_parks(int car_parks) {
        this.car_parks = car_parks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManager_phone() {
        return manager_phone;
    }

    public void setManager_phone(String manager_phone) {
        this.manager_phone = manager_phone;
    }

    public String getManager_email() {
        return manager_email;
    }

    public void setManager_email(String manager_email) {
        this.manager_email = manager_email;
    }

    public String getManager_photo() {
        return manager_photo;
    }

    public void setManager_photo(String manager_photo) {
        this.manager_photo = manager_photo;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public JSONArray getUploads() {
        return uploads;
    }

    public void setUploads(JSONArray uploads) {
        this.uploads = uploads;
    }
}
