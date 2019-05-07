package com.friki.mbuthia.propertyfindapp.model;

public class ServiceItem {
    private int photo;
    int serviceId;
    private String service;

    public ServiceItem(int photo, int serviceId, String service) {
        this.photo = photo;
        this.serviceId = serviceId;
        this.service = service;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
