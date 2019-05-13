package com.artexceptionals.youreuro.model;

public class CashRecord {

    String notes,date,amount, time ;

    @Constants.CurrencyType.Values
    String currency;

    @Constants.CashRecordType.Values
    String cashRecordType;

    @Constants.PaymentType.Values
    String paymentType;

    Category category;

    public CashRecord(String notes, String date, String amount,
                      String currency, String cashRecordType, String paymentType,
                      Category category, String time) {
        this.notes = notes;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.cashRecordType = cashRecordType;
        this.paymentType = paymentType;
        this.category = category;
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

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
