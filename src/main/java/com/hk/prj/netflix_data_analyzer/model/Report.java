package com.hk.prj.netflix_data_analyzer.model;

import java.util.List;

public class Report{
    private List<String> files;
    private List<Device> devices;
    private List<IPAddressStreaming> ipAddressStreamings;

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

    public List<IPAddressStreaming> getIpAddressStreamings() {
        return ipAddressStreamings;
    }

    public void setIpAddressStreamings(List<IPAddressStreaming> ipAddressStreamings) {
        this.ipAddressStreamings = ipAddressStreamings;
    }
}
