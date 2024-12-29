package com.hk.prj.netflix_data_analyzer.model;


import java.time.Duration;

public class ViewedContentResponse {
    private String profile;
    private Integer total;
    private Duration duration;
    private String totalDuration;
    private String year;

    public ViewedContentResponse(String profile, Integer total, Duration duration, String totalDuration, String year) {
        this.profile = profile;
        this.total = total;
        this.duration = duration;
        this.totalDuration = totalDuration;
        this.year = year;
    }

    public String getProfile() {
        return profile;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getYear() {
        return year;
    }
    public Integer getTotal() {
        return total;
    }
    public String getTotalDuration() {
        return totalDuration;
    }
}
