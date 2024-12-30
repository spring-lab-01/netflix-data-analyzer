package com.hk.prj.netflix_data_analyzer.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class AccountDetail {
    private String firstName;
    private String lastName;
    private String email;

    public AccountDetail() {
        super();
    }

    public AccountDetail(String s, String s1, String s2) {
        this.firstName = s;
        this.lastName = s1;
        this.email = s2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
