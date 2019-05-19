package com.artexceptionals.youreuro.helpers;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CurrencyHelper {
    public interface CurrencyType {
        String DOLLAR = "Dollar";
        String EURO = "Euro";
        String RUPEE = "RUPEE";

        @Retention(RetentionPolicy.SOURCE)
        @StringDef({DOLLAR, EURO, RUPEE})
        @interface Values {
        }
    }

    public static String getSymbol(String currencyType ){

        switch (currencyType){
            case CurrencyType.EURO:
                return "€";
            case CurrencyType.DOLLAR:
                return "$";
            case CurrencyType.RUPEE:
                return "₹";
            default:
                return PaymentTypeHelper.PaymentType.UNKNOWN;
        }

    }
}

