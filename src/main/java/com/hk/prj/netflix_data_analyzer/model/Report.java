package com.hk.prj.netflix_data_analyzer.model;

import java.util.List;
import java.util.Map;

public class Report{
    private List<String> files;
    private List<Device> devices;
    private Map<String, List<IPAddressStreaming>> ipAddressStreamings;

    public Report(){}

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public Map<String, List<IPAddressStreaming>> getIpAddressStreamings() {
        return ipAddressStreamings;
    }

    public void setIpAddressStreamings(Map<String, List<IPAddressStreaming>> ipAddressStreamings) {
        this.ipAddressStreamings = ipAddressStreamings;
    }
}
