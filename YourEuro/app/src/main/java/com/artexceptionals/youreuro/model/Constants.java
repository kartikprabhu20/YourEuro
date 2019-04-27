package com.artexceptionals.youreuro.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {

    public interface PaymentType {
        java.lang.String DEBIT_CARD = "Debit Card";
        java.lang.String CREDIT_CARD = "Credit Card";
        java.lang.String BANK_ACCOUNT = "Bank Account";
        java.lang.String CASH = "Cash";


        @Retention(RetentionPolicy.SOURCE)
        @StringDef({DEBIT_CARD, CREDIT_CARD, BANK_ACCOUNT, CASH})
        @interface Values {
        }
    }

    public interface CashRecordType {
        java.lang.String INCOME = "Income";
        java.lang.String EXPENSE = "Expense";
        java.lang.String TRANSFER = "Transfer";


        @Retention(RetentionPolicy.SOURCE)
        @StringDef({INCOME, EXPENSE, TRANSFER})
        @interface Values {
        }
    }

    public interface CurrencyType {
        java.lang.String DOLLAR = "Dollar";
        java.lang.String EURO = "Euro";
        java.lang.String RUPEE = "RUPEE";


        @Retention(RetentionPolicy.SOURCE)
        @StringDef({DOLLAR, EURO, RUPEE})
        @interface Values {
        }
    }

}
