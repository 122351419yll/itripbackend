package com.ytzl.itrip.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by l骆明 on 2018/8/20.
 */
public class DateUtil {
    public static Integer getDate(String dbtime1,String dbtime2) throws ParseException {
//算两个日期间隔多少天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1=format.parse(dbtime1);
        Date d2=format.parse(dbtime2);
        int a = (int) ((d1.getTime() - d2.getTime()) / (1000*3600*24));
        return a;
    }

    /**
     * 返回时间对应的星期几
     * @param date
     * @return
     */
    public static int getTodayDayOfWeek(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int dayOfweek = now.get(Calendar.DAY_OF_WEEK);
        dayOfweek--;
        if (dayOfweek == 0) {
            dayOfweek = 7;
        }
        return dayOfweek;
    }

    public static void main(String[] args) throws ParseException {
        String a="2018-06-05";
        String b="2018-06-03";
        getDate(a,b);
    }
}
