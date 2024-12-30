package com.hk.prj.netflix_data_analyzer.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class SpentByYear {

    private String amount;
    private String year;

    public SpentByYear(String year, String amount) {
        this.amount = amount;
        this.year = year;
    }
    public SpentByYear(){

    }

    public String getAmount() {
        return amount;
    }
    public String getYear() {
        return year;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
