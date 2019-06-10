package com.artexceptionals.youreuro.model;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class CashRecordFilter implements Parcelable {

    public static final String FILTER = "cashrecordfilter";

    long startTimeStamp=0, endTimeStamp=0;
    float startAmount = 0, endAmount = 0;
    List<Category> categories = new ArrayList<>();
    String paymentType;
    boolean categoryFilter,dateRangeFilter, paymentFilter, amountRangeFilter, recurryingFilter;

    public CashRecordFilter(long startTimeStamp, long endTimeStamp, float startAmount, float endAmount,
                            List<Category> categories, String paymentType, boolean categoryFilter, boolean dateRangeFilter,
                            boolean paymentFilter, boolean amountRangeFilter) {
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.startAmount = startAmount;
        this.endAmount = endAmount;
        this.categories.addAll(categories);
        this.paymentType = paymentType;
        this.categoryFilter = categoryFilter;
        this.dateRangeFilter = dateRangeFilter;
        this.paymentFilter = paymentFilter;
        this.amountRangeFilter = amountRangeFilter;
    }

    protected CashRecordFilter(Parcel in) {
        startTimeStamp = in.readLong();
        endTimeStamp = in.readLong();
        startAmount = in.readFloat();
        endAmount = in.readFloat();
        categories = in.createTypedArrayList(Category.CREATOR);
        paymentType = in.readString();
        categoryFilter = in.readByte() != 0;
        dateRangeFilter = in.readByte() != 0;
        paymentFilter = in.readByte() != 0;
        amountRangeFilter = in.readByte() != 0;
    }

    public CashRecordFilter() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(startTimeStamp);
        dest.writeLong(endTimeStamp);
        dest.writeFloat(startAmount);
        dest.writeFloat(endAmount);
        dest.writeTypedList(categories);
        dest.writeString(paymentType);
        dest.writeByte((byte) (categoryFilter ? 1 : 0));
        dest.writeByte((byte) (dateRangeFilter ? 1 : 0));
        dest.writeByte((byte) (paymentFilter ? 1 : 0));
        dest.writeByte((byte) (amountRangeFilter ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CashRecordFilter> CREATOR = new Creator<CashRecordFilter>() {
        @Override
        public CashRecordFilter createFromParcel(Parcel in) {
            return new CashRecordFilter(in);
        }

        @Override
        public CashRecordFilter[] newArray(int size) {
            return new CashRecordFilter[size];
        }
    };

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public float getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(float startAmount) {
        this.startAmount = startAmount;
    }

    public float getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(float endAmount) {
        this.endAmount = endAmount;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories.addAll(categories);
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isCategoryFilter() {
        return categoryFilter;
    }

    public void setCategoryFilter(boolean categoryFilter) {
        this.categoryFilter = categoryFilter;
    }

    public boolean isDateRangeFilter() {
        return dateRangeFilter;
    }

    public void setDateRangeFilter(boolean dateRangeFilter) {
        this.dateRangeFilter = dateRangeFilter;
    }

    public boolean isPaymentFilter() {
        return paymentFilter;
    }

    public void setPaymentFilter(boolean paymentFilter) {
        this.paymentFilter = paymentFilter;
    }

    public boolean isAmountRangeFilter() {
        return amountRangeFilter;
    }

    public void setAmountRangeFilter(boolean amountRangeFilter) {
        this.amountRangeFilter = amountRangeFilter;
    }

    public boolean isRecurryingFilter() {
        return recurryingFilter;
    }

    public void setRecurryingFilter(boolean recurryingFilter) {
        this.recurryingFilter = recurryingFilter;
    }

}
