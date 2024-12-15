package com.hk.prj.netflix_data_analyzer.model;

public class SpentByYear {

    private String amount;
    private String year;

    public SpentByYear(String year, String amount) {
        this.amount = amount;
        this.year = year;
    }

    public String getAmount() {
        return amount;
    }
    public String getYear() {
        return year;
    }

}
