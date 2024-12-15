package com.hk.prj.netflix_data_analyzer.model;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class ViewedContent {
    private String profile;
    private final String startTime;
    private final Duration duration;
    private String title;
    private String videoType;
    private String year;

    public ViewedContent(String profile, String startTime, Duration duration, String title, String videoType, String year) {
        this.profile = profile;
        this.startTime = startTime;
        this.duration = duration;
        this.title = title;
        this.videoType = videoType;
        this.year = year;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewedContent that = (ViewedContent) o;
        return Objects.equals(profile, that.profile) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profile, title);
    }
}
