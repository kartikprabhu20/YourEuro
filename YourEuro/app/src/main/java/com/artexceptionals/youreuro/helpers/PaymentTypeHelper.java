package com.artexceptionals.youreuro.helpers;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PaymentTypeHelper {

    public interface PaymentType {
        String DEBIT_CARD = "Debit Card";
        String CREDIT_CARD = "Credit Card";
        String BANK_ACCOUNT = "Bank Account";
        String CASH = "Cash";
        String VOUCHER = "Voucher";
        String UNKNOWN = "Unknown";

        @Retention(RetentionPolicy.SOURCE)
        @StringDef({DEBIT_CARD, CREDIT_CARD, BANK_ACCOUNT, CASH,VOUCHER, UNKNOWN})
        @interface Values {
        }
    }

    public static String getPaymentType(String paymentType) {

        switch (paymentType){
            case "Cash":
                return PaymentType.CASH;
            case "Debit Card":
                return PaymentType.DEBIT_CARD;
            case "Credit Card":
                return PaymentType.CREDIT_CARD;
            case "Bank Account":
                return PaymentType.BANK_ACCOUNT;
            case "Voucher":
                return PaymentType.VOUCHER;
            default:
                return PaymentType.UNKNOWN;
        }

    }
}
