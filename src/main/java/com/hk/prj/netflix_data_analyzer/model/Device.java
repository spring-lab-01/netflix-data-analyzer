package com.hk.prj.netflix_data_analyzer.model;

import java.util.Objects;

public class Device {
    private final String profile;
    private final String deviceType;
    private final String lastUsedTime;

    public Device(String profile, String deviceType, String lastUsedTime) {
        this.profile = profile;
        this.deviceType = deviceType;
        this.lastUsedTime = lastUsedTime;
    }

    public String getProfile() {
        return profile;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getLastUsedTime() {
        return lastUsedTime;
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
