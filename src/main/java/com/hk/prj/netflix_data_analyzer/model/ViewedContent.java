package com.hk.prj.netflix_data_analyzer.model;


import java.util.Objects;

public class ViewedContent {
    private String profile;
    private String startTime;
    private String title;
    private String videoType;

    public ViewedContent(String profile, String startTime, String title, String videoType) {
        this.profile = profile;
        this.startTime = startTime;
        this.title = title;
        this.videoType = videoType;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public void setVideoType(String videoType) {
        this.videoType = videoType;
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
