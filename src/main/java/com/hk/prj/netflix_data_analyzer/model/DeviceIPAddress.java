package com.hk.prj.netflix_data_analyzer.model;

import java.util.Objects;

public class DeviceIPAddress {
    private String ipAddress;
    private String device;
    private String country;
    private String region;
    private String location;
    private String timestamp;

    public DeviceIPAddress(String ipAddress, String device, String country, String region, String location, String timestamp) {
        this.ipAddress = ipAddress;
        this.device = device;
        this.country = country;
        this.region = region;
        this.location = location;
        this.timestamp = timestamp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceIPAddress that = (DeviceIPAddress) o;
        return Objects.equals(ipAddress, that.ipAddress) && Objects.equals(device, that.device);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress, device);
    }
}
