package com.meitu.library.qwechat.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lh, 2020/12/14
 */
public class DateUtils {
    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
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

    /**
     * 将时间戳转成日期
     * @param date
     * @return
     */

    public static String stampToDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        time = simpleDateFormat.format(date);

        return time;
    }
}
