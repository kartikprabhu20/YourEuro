package com.artexceptionals.youreuro.model;

import com.artexceptionals.youreuro.helpers.PaymentTypeHelper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CashRecordFilterTest {
    Category category1 = new Category(4,"Bills","image_dollar");
    Category category2 = new Category(6,"Shopping","image_shopping");
    List<Category> categories = new ArrayList<>();

    @Test
    public void constructorTesting(){
        categories.add(category1);
        categories.add(category2);
        CashRecordFilter cashRecordFilter = new CashRecordFilter(4000l,8000l,5.0f,15.0f,categories,
                PaymentTypeHelper.PaymentType.VOUCHER,true,true,true,true);
        assertEquals("Fetching start time stamp failed",4000l,cashRecordFilter.getStartTimeStamp());
        assertEquals("Fetching end time stamp failed",8000l,cashRecordFilter.getEndTimeStamp());
        assertEquals("Fetching start amount failed",5.0f,cashRecordFilter.getStartAmount(),0.001);
        assertEquals("Fetching start amount failed",15.0f,cashRecordFilter.getEndAmount(),0.001);
        assertEquals("Fetching categories failed",categories,cashRecordFilter.getCategories());
        assertEquals("Fetching payment type failed","Voucher",cashRecordFilter.getPaymentType());
        assertEquals("Fetching if category filter failed",true,cashRecordFilter.isCategoryFilter());
        assertEquals("Fetching if daterange filter failed",true,cashRecordFilter.isDateRangeFilter());
        assertEquals("Fetching if payment filter failed",true,cashRecordFilter.isPaymentFilter());
        assertEquals("Fetching if amountRange filter failed",true,cashRecordFilter.isAmountRangeFilter());
    }

    @Test
    public void setterGetterTesting(){
        categories.add(category1);
        CashRecordFilter cashRecordFilter1 = new CashRecordFilter();
        cashRecordFilter1.setStartTimeStamp(1000l);
        cashRecordFilter1.setEndTimeStamp(2000l);
        cashRecordFilter1.setStartAmount(10.0f);
        cashRecordFilter1.setEndAmount(100.0f);
        cashRecordFilter1.setCategories(categories);
        cashRecordFilter1.setPaymentType(PaymentTypeHelper.PaymentType.DEBIT_CARD);
        cashRecordFilter1.setCategoryFilter(false);
        cashRecordFilter1.setDateRangeFilter(true);
        cashRecordFilter1.setPaymentFilter(false);
        cashRecordFilter1.setAmountRangeFilter(true);
        cashRecordFilter1.setRecurryingFilter(true);
        assertEquals("Fetching start time stamp failed",1000l,cashRecordFilter1.getStartTimeStamp());
        assertEquals("Fetching end time stamp failed",2000l,cashRecordFilter1.getEndTimeStamp());
        assertEquals("Fetching start amount failed",10.0f,cashRecordFilter1.getStartAmount(),0.001);
        assertEquals("Fetching start amount failed",100.0f,cashRecordFilter1.getEndAmount(),0.001);
        assertEquals("Fetching categories failed",categories,cashRecordFilter1.getCategories());
        assertEquals("Fetching payment type failed","Debit Card",cashRecordFilter1.getPaymentType());
        assertEquals("Fetching if category filter failed",false,cashRecordFilter1.isCategoryFilter());
        assertEquals("Fetching if daterange filter failed",true,cashRecordFilter1.isDateRangeFilter());
        assertEquals("Fetching if payment filter failed",false,cashRecordFilter1.isPaymentFilter());
        assertEquals("Fetching if amountRange filter failed",true,cashRecordFilter1.isAmountRangeFilter());
        assertEquals("Fetching if Recurring filter failed",true,cashRecordFilter1.isRecurryingFilter());

    }

}