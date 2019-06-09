package com.artexceptionals.youreuro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.artexceptionals.youreuro.model.CashRecord;

import java.util.ArrayList;
import java.util.List;

public class RecurringReceiver extends BroadcastReceiver {

    private static RecurringReceiver receiver;
    private final Context context;
    private List<Long> cachedCashRecordIds = new ArrayList<>();
    private RecurringReceiver.ReceiverListener listener;

    private RecurringReceiver(Context context) {
        this.context = context;
    }

    public static synchronized RecurringReceiver getReceiver(Context context) {
        if (receiver == null)
            receiver = new RecurringReceiver(context);
        return receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        long id = intent.getLongExtra(RecurringManager.ALARM_ID,-1);
        Log.i("YourEuro", "Received CashRecord:" + id);

        if (id != -1)
            listener.listen(id);
    }

    public synchronized boolean registerCashRecord(CashRecord cashRecord, ReceiverListener listener) {
        this.listener = listener;

        if (cachedCashRecordIds.isEmpty()) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(RecurringManager.RECURRING_CASHRECORD_ACTION);
            intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            context.registerReceiver(this, intentFilter);
        }

        cachedCashRecordIds.add(cashRecord.getUid());

        return true;
    }

    public interface ReceiverListener{
         void listen(long cashRecordID);
    }
}
