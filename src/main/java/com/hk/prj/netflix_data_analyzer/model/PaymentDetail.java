package com.hk.prj.netflix_data_analyzer.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class PaymentDetail {

    private String year;
    private String transactionDate;
    private String priceAmt;
    private String currency;
    private String taxAmt;
    private String grossSaleAmt;
    private String txnType;
    private String pmtStatus;
    private String finalInvoiceResult;


    public PaymentDetail(String year, String transactionDate, String priceAmt, String currency, String taxAmt, String grossSaleAmt, String txnType, String pmtStatus, String finalInvoiceResult) {
        this.year = year;
        this.transactionDate = transactionDate;
        this.priceAmt = priceAmt;
        this.currency = currency;
        this.taxAmt = taxAmt;
        this.grossSaleAmt = grossSaleAmt;
        this.txnType = txnType;
        this.pmtStatus = pmtStatus;
        this.finalInvoiceResult = finalInvoiceResult;
    }

    public PaymentDetail() {

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPriceAmt() {
        return priceAmt;
    }

    public void setPriceAmt(String priceAmt) {
        this.priceAmt = priceAmt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(String taxAmt) {
        this.taxAmt = taxAmt;
    }

    public String getGrossSaleAmt() {
        return grossSaleAmt;
    }

    public void setGrossSaleAmt(String grossSaleAmt) {
        this.grossSaleAmt = grossSaleAmt;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getPmtStatus() {
        return pmtStatus;
    }

    public void setPmtStatus(String pmtStatus) {
        this.pmtStatus = pmtStatus;
    }

    public String getFinalInvoiceResult() {
        return finalInvoiceResult;
    }

    public void setFinalInvoiceResult(String finalInvoiceResult) {
        this.finalInvoiceResult = finalInvoiceResult;
    }
}
