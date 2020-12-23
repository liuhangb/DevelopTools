package com.example.order.developtools.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.order.developtools.utils.DateUtils;

/**
 * Created by lh, 2020/12/11
 * 定时任务
 */
public class AlarmJob {
    private static final String TAG = "AlarmJob";

    private Context mContext;
    private static Runnable mRunnable;
    private PendingIntent mPendingIntent;

    public AlarmJob(Context context, Runnable runnable) {
        mContext = context;
        mRunnable = runnable;
    }

    /**
     * 启动定时任务
     * @param date 格式："2020-12-10 11:40:00"
     */
    public void start(String date) {
        if (mContext == null) {
            Log.e(TAG, "AlarmJob start failed, because of context null");
            return;
        }

        AlarmManager alarmMgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmReceiver.class);

        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        long time2 = DateUtils.dateToStamp(date);
        Log.d(TAG, "getTimeInMillis: " + time2);
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, time2,
                mPendingIntent);
    }

    public void cancel() {
        AlarmManager alarmMgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(mPendingIntent);
    }

    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "AlarmReceiver onReceive====");
            if (mRunnable != null) {
                mRunnable.run();
            }
        }
    }
}
