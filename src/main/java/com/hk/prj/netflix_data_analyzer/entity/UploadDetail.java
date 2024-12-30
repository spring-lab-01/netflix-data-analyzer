package com.hk.prj.netflix_data_analyzer.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;
import java.util.Objects;

@DynamoDbBean
public class UploadDetail {

    private String id;
    private String email;
    private String filename;
    private LocalDateTime uploadTime;

    public UploadDetail(String id, String email, String filename, LocalDateTime uploadTime) {
        this.id = id;
        this.email = email;
        this.filename = filename;
        this.uploadTime = uploadTime;
    }
    public UploadDetail() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbPartitionKey
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadDetail that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(email, that.email)
                && Objects.equals(filename, that.filename)
                && Objects.equals(uploadTime, that.uploadTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, filename, uploadTime);
    }
}
