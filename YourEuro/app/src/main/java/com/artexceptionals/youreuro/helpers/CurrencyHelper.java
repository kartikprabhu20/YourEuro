package com.artexceptionals.youreuro.helpers;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CurrencyHelper {
    public static final String CURRENT_CURRENCY = "current_currency";

    public interface CurrencyType {
        String EURO = "1";
        String DOLLAR = "2";
        String RUPEE = "3";
        String POUND = "4";

        @Retention(RetentionPolicy.SOURCE)
        @StringDef({DOLLAR, EURO, RUPEE, POUND})
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
            case CurrencyType.POUND:
                return "£";
            default:
                return PaymentTypeHelper.PaymentType.UNKNOWN;
        }

    }

    public static String getName(String currencyType ){

        switch (currencyType){
            case CurrencyType.EURO:
                return "Euro";
            case CurrencyType.DOLLAR:
                return "Dollar";
            case CurrencyType.RUPEE:
                return "Rupee";
            case CurrencyType.POUND:
                return "Pound";
            default:
                return PaymentTypeHelper.PaymentType.UNKNOWN;
        }

    }

    public static String getSymbolName(String currencyType ){

        switch (currencyType){
            case CurrencyType.EURO:
                return "€";
            case CurrencyType.DOLLAR:
                return "$";
            case CurrencyType.RUPEE:
                return "Rs";
            case CurrencyType.POUND:
                return "£";
            default:
                return PaymentTypeHelper.PaymentType.UNKNOWN;
        }
    }
}


