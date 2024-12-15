package com.hk.prj.netflix_data_analyzer.model;

public class PaymentDetail {

    private String transactionDate;
    private String priceAmt;
    private String currency;
    private String taxAmt;
    private String grossSaleAmt;
    private String txnType;
    private String pmtStatus;
    private String finalInvoiceResult;


    public PaymentDetail(String transactionDate, String priceAmt, String currency, String taxAmt, String grossSaleAmt, String txnType, String pmtStatus, String finalInvoiceResult) {
        this.transactionDate = transactionDate;
        this.priceAmt = priceAmt;
        this.currency = currency;
        this.taxAmt = taxAmt;
        this.grossSaleAmt = grossSaleAmt;
        this.txnType = txnType;
        this.pmtStatus = pmtStatus;
        this.finalInvoiceResult = finalInvoiceResult;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getPriceAmt() {
        return priceAmt;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTaxAmt() {
        return taxAmt;
    }

    public String getGrossSaleAmt() {
        return grossSaleAmt;
    }

    public String getTxnType() {
        return txnType;
    }

    public String getPmtStatus() {
        return pmtStatus;
    }

    public String getFinalInvoiceResult() {
        return finalInvoiceResult;
    }
}
