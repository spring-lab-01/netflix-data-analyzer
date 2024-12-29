package com.hk.prj.netflix_data_analyzer.model;

import java.util.Objects;

public record Device(String profile, String deviceType, String lastUsedTime) {

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
