package com.hk.prj.netflix_data_analyzer.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.Duration;

@DynamoDbBean
public class WatchedSummary {
    private String profile;
    private Integer total;
    private Duration duration;
    private String totalDuration;
    private String year;

    public WatchedSummary(String profile, Integer total, Duration duration, String totalDuration, String year) {
        this.profile = profile;
        this.total = total;
        this.duration = duration;
        this.totalDuration = totalDuration;
        this.year = year;
    }

    public WatchedSummary() {

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

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void setYear(String year) {
        this.year = year;
    }
}