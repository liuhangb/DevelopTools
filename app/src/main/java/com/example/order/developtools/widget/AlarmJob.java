package com.example.order.developtools.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.order.developtools.TaoBaoEventProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lh, 2020/12/11
 * 定时任务
 */
public class AlarmJob {
    private final String TAG = "AlarmJob";

    private Context mContext;

    public AlarmJob(Context context) {
        mContext = context;
    }

    /**
     * 启动定时任务
     * @param date 格式："2020-12-10 11:40:00"
     * @param broadcastReceiver 提供广播接受者
     */
    public void start(String date, Class broadcastReceiver) {
        if (mContext == null) {
            Log.e(TAG, "AlarmJob start failed, because of context null");
            return;
        }

        AlarmManager alarmMgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, broadcastReceiver);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        long time2 = dateToStamp(date);
        Log.d(TAG, "getTimeInMillis: " + time2);
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, time2,
                alarmIntent);
    }

    /*
     * 将时间转换为时间戳
     */
    private static long dateToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date == null ? 0 : date.getTime();
        return ts;
    }
}
