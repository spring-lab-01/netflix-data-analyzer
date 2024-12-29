package com.hk.prj.netflix_data_analyzer.model;


import java.time.Duration;

public record ViewedContentResponse(String profile, Integer total, Duration duration,
                                    String totalDuration, String year) { }
