package com.artexceptionals.youreuro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.artexceptionals.youreuro.helpers.RecurringHelper;
import com.artexceptionals.youreuro.model.CashRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RecurringManager {
    public static final String RECURRING_CASHRECORD_ACTION = "recurring_cashrecord";
    public static final String ALARM_ID = "alarm_id";

    private static final long HOURS_MILLISECOND = 60*60*1000L ;
    public static final long HOURS_24_MILLISECOND = HOURS_MILLISECOND * 24 ;

    private static RecurringManager instance;
    private static Context mContext;
    private final AlarmManager alarmManager;
    private final RecurringReceiver receiver;
    private RecurringReceiver.ReceiverListener listener;

    public RecurringManager(RecurringReceiver receiver, AlarmManager alarmManager) {
        this.receiver = receiver;
        this.alarmManager = alarmManager;
    }

    public static RecurringManager getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new RecurringManager(RecurringReceiver.getReceiver(context),
                    (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE));
        }

        return instance;
    }

    public void initialiseNextEvent(CashRecord cashRecord, RecurringReceiver.ReceiverListener listener) {
        this.listener = listener;
        receiver.registerCashRecord(cashRecord, listener);

        long nextTriggerTime = cashRecord.getTimeStamp();
        Log.i("YourEuro",  "initialiseNextEvent:"+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", Locale.US).format(nextTriggerTime));

        switch(cashRecord.getRecurringType()){
            case RecurringHelper.RecurringType.DAILY:
                setAlarm(cashRecord.getUid(), nextTriggerTime+ HOURS_24_MILLISECOND);
                break;
            case RecurringHelper.RecurringType.WEEKLY:
                setAlarm(cashRecord.getUid(), nextTriggerTime + 7 * HOURS_24_MILLISECOND);
                break;
            case RecurringHelper.RecurringType.MONTHLY:
                setAlarm(cashRecord.getUid(), getNextMonthTime(nextTriggerTime));
                break;
            case RecurringHelper.RecurringType.YEARLY:
                setAlarm(cashRecord.getUid(), getNextYearTime(nextTriggerTime));
                break;

        }
    }

    private void setAlarm(long conditionID, long nextTriggerTime) {
        Intent intent = new Intent(RECURRING_CASHRECORD_ACTION);
        intent.putExtra(ALARM_ID, conditionID);
        PendingIntent pi = PendingIntent.getBroadcast(mContext,(int) conditionID, intent, 0);

        Log.i("YourEuro", "setAlarm");
        if (alarmManager != null) {
            alarmManager.cancel(pi);
            alarmManager.set(AlarmManager.RTC_WAKEUP, nextTriggerTime, pi);
            Log.i("YourEuro", "Next trigger for EventID:" + conditionID + " is scheduled at " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", Locale.US).format(nextTriggerTime));
        } else {
            Log.e("YourEuro", "AlarmManager was null hence couldn't schedule next event");
        }
    }

    public void cancelPendingIntents(CashRecord cashRecord) {
        receiver.registerCashRecord(cashRecord, listener);

        Intent intent = new Intent(RECURRING_CASHRECORD_ACTION);
        intent.putExtra(ALARM_ID, cashRecord.getUid());
        PendingIntent pi = PendingIntent.getBroadcast(mContext, (int) cashRecord.getUid(), intent, 0);
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        if (am != null)
            am.cancel(pi);
    }

    private long getNextMonthTime(long timeStamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStamp);

        int currentMonth = cal.get(Calendar.MONTH);
        int nextMonth = currentMonth + 1;

        if(nextMonth > Calendar.DECEMBER){
            nextMonth = Calendar.JANUARY;
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+1);
        }

        cal.set(Calendar.MONTH, nextMonth);
        long nextTime = cal.getTimeInMillis();

        return nextTime;
    }

    private long getNextYearTime(long timeStamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStamp);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+1);

        int maximumDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, maximumDay);
        long nextTime = cal.getTimeInMillis();

        return nextTime;
    }

}
