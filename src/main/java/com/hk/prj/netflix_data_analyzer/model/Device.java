package com.hk.prj.netflix_data_analyzer.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.Objects;

@DynamoDbBean
public class Device {

    private String profile;
    private String deviceType;
    private String lastUsedTime;

    public Device(String profile, String deviceType, String lastUsedTime) {
        this.profile = profile;
        this.deviceType = deviceType;
        this.lastUsedTime = lastUsedTime;
    }

    public Device() {

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

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
