package com.friki.mbuthia.propertyfindapp.model;

public class Technician {
    private int id;
    private String name;
    private String phone;
    private String providerInfo;
    private int rating;
    private String photoUrl;
    private String locality;
    private String experience;
    private String clients;

    public Technician(int id, String name, String phone, String providerInfo, int rating, String photoUrl, String locality, String experience, String clients) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.providerInfo = providerInfo;
        this.rating = rating;
        this.photoUrl = photoUrl;
        this.locality = locality;
        this.experience = experience;
        this.clients = clients;
    }

    public String getProviderInfo() {
        return providerInfo;
    }

    public void setProviderInfo(String providerInfo) {
        this.providerInfo = providerInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getClients() {
        return clients;
    }

    public void setClients(String clients) {
        this.clients = clients;
    }
}
