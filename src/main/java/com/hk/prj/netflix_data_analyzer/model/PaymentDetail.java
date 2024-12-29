package com.hk.prj.netflix_data_analyzer.model;

public record PaymentDetail(String year, String transactionDate, String priceAmt,
                            String currency, String taxAmt, String grossSaleAmt, String txnType,
                            String pmtStatus, String finalInvoiceResult) {

}
