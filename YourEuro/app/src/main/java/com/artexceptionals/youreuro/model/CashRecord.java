package com.artexceptionals.youreuro.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.artexceptionals.youreuro.helpers.CurrencyHelper;
import com.artexceptionals.youreuro.helpers.PaymentTypeHelper;

import java.util.Objects;

@Entity(tableName = "cashrecord")
public class CashRecord implements Parcelable {

    public static final String CASHRECORD_DETAIL = "cashrecord_details";
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "notes")
    String notes;
    @ColumnInfo(name = "timeStamp")
    long timeStamp;
    @ColumnInfo(name = "amount")
    float amount;

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

    @ColumnInfo(name = "recurringTransaction")
    boolean recurringTransaction;

    @ColumnInfo(name = "recurringType")
    String recurringType;

    public CashRecord(String notes, long timeStamp, float amount,
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

    protected CashRecord(Parcel in) {
        uid = in.readInt();
        notes = in.readString();
        timeStamp = in.readLong();
        amount = in.readFloat();
        currency = in.readString();
        cashRecordType = in.readString();
        paymentType = in.readString();
        category = in.readParcelable(Category.class.getClassLoader());
        recurringTransaction = in.readByte() != 0;
        recurringType = in.readString();
    }

    public static final Creator<CashRecord> CREATOR = new Creator<CashRecord>() {
        @Override
        public CashRecord createFromParcel(Parcel in) {
            return new CashRecord(in);
        }

        @Override
        public CashRecord[] newArray(int size) {
            return new CashRecord[size];
        }
    };

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
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

    public boolean isRecurringTransaction() {
        return recurringTransaction;
    }

    public void setRecurringTransaction(boolean recurringTransaction) {
        this.recurringTransaction = recurringTransaction;
    }

    public String getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(String recurringType) {
        this.recurringType = recurringType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(notes);
        dest.writeLong(timeStamp);
        dest.writeFloat(amount);
        dest.writeString(currency);
        dest.writeString(cashRecordType);
        dest.writeString(paymentType);
        dest.writeParcelable(category, flags);
        dest.writeByte((byte) (recurringTransaction ? 1 : 0));
        dest.writeString(recurringType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashRecord that = (CashRecord) o;
        return uid == that.uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }
}
