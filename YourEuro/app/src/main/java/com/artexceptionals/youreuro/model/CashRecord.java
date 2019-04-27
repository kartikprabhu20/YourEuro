package com.artexceptionals.youreuro.model;

public class CashRecord {

    String notes, date ;

    @Constants.CurrencyType.Values
    String currency;

    @Constants.CashRecordType.Values
    String cashRecordType;

    @Constants.PaymentType.Values
    String paymentType;

    Category category;
    Account account;


    public java.lang.String getNotes() {
        return notes;
    }

    public void setNotes(java.lang.String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Constants.CurrencyType.Values
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@Constants.CurrencyType.Values String currency) {
        this.currency = currency;
    }

    @Constants.PaymentType.Values
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(@Constants.PaymentType.Values String paymentType) {
        this.paymentType = paymentType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Constants.CashRecordType.Values
    public String getCashRecordType() {
        return cashRecordType;
    }

    public void setCashRecordType(@Constants.CashRecordType.Values String cashRecordType) {
        this.cashRecordType = cashRecordType;
    }
}
