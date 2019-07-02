package com.artexceptionals.youreuro.model;

import com.artexceptionals.youreuro.helpers.CurrencyHelper;
import com.artexceptionals.youreuro.helpers.PaymentTypeHelper;
import com.artexceptionals.youreuro.helpers.RecurringHelper;

import org.junit.Test;

import static org.junit.Assert.*;

public class CashRecordTest {
    Category category = new Category("Education","image_education");
    CashRecord cashRecord = new CashRecord("Tution Fee",8560l,300f, CurrencyHelper.CurrencyType.POUND,
            Constants.CashRecordType.INCOME, PaymentTypeHelper.PaymentType.CASH, category,true);

    @Test
    public void constructor1Testing(){
        assertEquals("Fetching notes failed","Tution Fee",cashRecord.getNotes());
        assertEquals("Fetching timeStamp failed",8560l,cashRecord.getTimeStamp());
        assertEquals("Fetching amount failed",300f,cashRecord.getAmount(),0.001);
        assertEquals("Fetching currency failed","4",cashRecord.getCurrency());
        assertEquals("Fetching cashRecordType failed","Income",cashRecord.getCashRecordType());
        assertEquals("Fetching payment type failed","Cash",cashRecord.getPaymentType());
        assertEquals("fetching category failed",category,cashRecord.getCategory());
        assertEquals("fetching recurring type failed",true,cashRecord.isRecurred());
    }

    @Test
    public void constructor2Testing(){
        cashRecord.setRecurringType(RecurringHelper.RecurringType.WEEKLY);
        cashRecord.setRecurringTransaction(true);
        CashRecord cashRecord1 = new CashRecord(cashRecord);
        assertEquals("Fetching notes failed","Tution Fee",cashRecord.getNotes());
        assertEquals("Fetching amount failed",300f,cashRecord.getAmount(),0.001);
        assertEquals("Fetching currency failed","4",cashRecord.getCurrency());
        assertEquals("Fetching cashRecordType failed","Income",cashRecord.getCashRecordType());
        assertEquals("Fetching payment type failed","Cash",cashRecord.getPaymentType());
        assertEquals("fetching category failed",category,cashRecord.getCategory());
        assertEquals("fetching recurring type failed","Weekly",cashRecord.getRecurringType());
        assertEquals("fetching whether recurring transaction failed",true,cashRecord.isRecurringTransaction());
    }

    @Test
    public void setterGetterTesting(){
        CashRecord cashRecord2 = new CashRecord();
        cashRecord2.setNotes("Bus charges");
        cashRecord2.setAmount(2500f);
        cashRecord2.setTimeStamp(1000l);
        cashRecord2.setCurrency(CurrencyHelper.CurrencyType.DOLLAR);
        cashRecord2.setPaymentType(PaymentTypeHelper.PaymentType.DEBIT_CARD);
        cashRecord2.setCategory(category);
        cashRecord2.setCashRecordType(Constants.CashRecordType.EXPENSE);
        cashRecord2.setRecurred(false);

        assertEquals("Fetching notes failed","Bus charges",cashRecord2.getNotes());
        assertEquals("Fetching timeStamp failed",1000l,cashRecord2.getTimeStamp());
        assertEquals("Fetching amount failed",2500f,cashRecord2.getAmount(),0.001);
        assertEquals("Fetching currency failed","2",cashRecord2.getCurrency());
        assertEquals("Fetching cashRecordType failed","Expense",cashRecord2.getCashRecordType());
        assertEquals("Fetching payment type failed","Debit Card",cashRecord2.getPaymentType());
        assertEquals("fetching category failed",category,cashRecord2.getCategory());
        assertEquals("fetching recurring type failed",false,cashRecord2.isRecurred());
    }

}