package com.artexceptionals.youreuro.helpers;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RecurringHelper {
    public interface RecurringType {
        String DAILY = "Daily";
        String WEEKLY = "Weekly";
        String MONTHLY = "Monthly";
        String YEARLY = "Yearly";
        String UNKNOWN = "Unknown";

        @Retention(RetentionPolicy.SOURCE)
        @StringDef({DAILY, WEEKLY, MONTHLY, YEARLY, UNKNOWN})
        @interface Values {
        }
    }
}
