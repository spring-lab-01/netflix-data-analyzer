package com.hk.prj.netflix_data_analyzer.model;

import java.util.Objects;

public class Device {
    private String profile;
    private String deviceType;
    private String esn;
    private String lastUsedTime;
    public Device(String profile, String deviceType, String esn, String lastUsedTime) {
        this.profile = profile;
        this.deviceType = deviceType;
        this.esn = esn;
        this.lastUsedTime = lastUsedTime;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getEsn() {
        return esn;
    }

    public void setEsn(String esn) {
        this.esn = esn;
    }

    public String getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(String lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return (deviceType.equalsIgnoreCase(device.deviceType) &&
                lastUsedTime.equalsIgnoreCase(device.lastUsedTime));
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceType, lastUsedTime);
    }
}
