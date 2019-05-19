package com.artexceptionals.youreuro.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.artexceptionals.youreuro.helpers.CurrencyHelper;
import com.artexceptionals.youreuro.helpers.PaymentTypeHelper;

import java.io.Serializable;


@Entity(tableName = "cashrecord")
public class CashRecord implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "notes")
    String notes;
    @ColumnInfo(name = "timeStamp")
    long timeStamp;
    @ColumnInfo(name = "amount")
    String amount;

    @ColumnInfo(name = "currency")
    @CurrencyHelper.CurrencyType.Values
    String currency;

    @ColumnInfo(name = "cashrecordtype")
    @Constants.CashRecordType.Values
    String cashRecordType;

    @ColumnInfo(name = "paymenttype")
    @PaymentTypeHelper.PaymentType.Values
    String paymentType;

    @Embedded
    Category category;

    public CashRecord(String notes, long timeStamp, String amount,
                      String currency, String cashRecordType, String paymentType,
                      Category category) {
        this.notes = notes;
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.currency = currency;
        this.cashRecordType = cashRecordType;
        this.paymentType = paymentType;
        this.category = category;
    }

    @Ignore
    public CashRecord() {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @CurrencyHelper.CurrencyType.Values
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@CurrencyHelper.CurrencyType.Values String currency) {
        this.currency = currency;
    }

    @PaymentTypeHelper.PaymentType.Values
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(@PaymentTypeHelper.PaymentType.Values String paymentType) {
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
