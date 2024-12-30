package com.hk.prj.netflix_data_analyzer.entity;

import com.hk.prj.netflix_data_analyzer.model.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
public class UploadAnalysis {

    private String email;
    private AccountDetail accountDetail;
    private List<Device> devices;
    private List<ViewingActivity> viewingActivityList;
    private List<SpentByYear> paymentSummary;

    public UploadAnalysis(String email, AccountDetail accountDetail, List<Device> devices, List<ViewingActivity> viewingActivityList,
                          List<SpentByYear> paymentSummary) {
        this.email = email;
        this.accountDetail = accountDetail;
        this.devices = devices;
        this.viewingActivityList = viewingActivityList;
        this.paymentSummary = paymentSummary;
    }

    public UploadAnalysis() {
    }

    @DynamoDbPartitionKey
    public String getEmail() {
        return email;
    }

    public AccountDetail getAccountDetail() {
        return accountDetail;
    }

    public List<Device> getDevices() {
        return devices;
    }


    public List<SpentByYear> getPaymentSummary() {
        return paymentSummary;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountDetail(AccountDetail accountDetail) {
        this.accountDetail = accountDetail;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void setPaymentSummary(List<SpentByYear> paymentSummary) {
        this.paymentSummary = paymentSummary;
    }

    public List<ViewingActivity> getViewingActivityList() {
        return viewingActivityList;
    }

    public void setViewingActivityList(List<ViewingActivity> viewingActivityList) {
        this.viewingActivityList = viewingActivityList;
    }
}
