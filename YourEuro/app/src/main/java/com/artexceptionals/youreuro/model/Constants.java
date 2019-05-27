package com.artexceptionals.youreuro.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {

    public interface CashRecordType {
        String INCOME = "Income";
        String EXPENSE = "Expense";

        @Retention(RetentionPolicy.SOURCE)
        @StringDef({INCOME, EXPENSE})
        @interface Values {
        }
    }

}
