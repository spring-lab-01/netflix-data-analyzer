package com.hk.prj.netflix_data_analyzer.model;


public class ViewedContentResponse {
    private String profile;
    private Integer total;
    private String totalDuration;
    private String year;

    public ViewedContentResponse(String profile, Integer total, String totalDuration, String year) {
        this.profile = profile;
        this.total = total;
        this.totalDuration = totalDuration;
        this.year = year;
    }

    public String getProfile() {
        return profile;
    }

    public Integer getTotal() {
        return total;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public String getYear() {
        return year;
    }
}
