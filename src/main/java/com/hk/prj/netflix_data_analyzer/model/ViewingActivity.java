package com.hk.prj.netflix_data_analyzer.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.Duration;
import java.util.Objects;

@DynamoDbBean
public class ViewingActivity {

    private String profile;
    private String startTime;
    private Duration duration;
    private String title;
    private String videoType;
    private String year;

    public ViewingActivity(String profile, String startTime, Duration duration, String title, String videoType, String year) {
        this.profile = profile;
        this.startTime = startTime;
        this.duration = duration;
        this.title = title;
        this.videoType = videoType;
        this.year = year;
    }

    public ViewingActivity( ){

    }

    public String getProfile() {
        return profile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoType() {
        return videoType;
    }

    public String getYear() {
        return year;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewingActivity that = (ViewingActivity) o;
        return Objects.equals(profile, that.profile) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profile, title);
    }
}
