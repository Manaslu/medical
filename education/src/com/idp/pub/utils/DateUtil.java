package com.idp.pub.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
 
/**
 * 
 * @author Raoxy
 * 
 */
public final class DateUtil {
    /** 年月日的时间格式 ：2008-02-27 */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_01 = "yyyy-MM-01";

    public static final String YYYY_01_01 = "yyyy-01-01";
    /** 包含时分秒的时间格式 2008-02-27 15:23:55 */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String YYYY_MM_DD_hh_MM = "yyyy-MM-dd hh:mm";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMM = "yyyyMM";

    public static final String YYYYMMDD_CH = "yyyy年MM月dd日";

    public static final String YYYYMM_CH = "yyyy年MM月";

    public static final String HH_MM_SS = "HH:mm:ss";
    
    public static final String MMDDHHMMSS = "MMddHHmmss";

    public static final String YYYY_MM_DD1 = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_HH_MM1 = "yyyy/MM/dd HH:mm";
    public static SimpleDateFormat SDF_YYYY_MM_DD_2HH_MM = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
    public static SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat(YYYY_MM_DD);
    public static SimpleDateFormat SDF_YYYY_MM_DD_2HH_MM1 = new SimpleDateFormat(YYYY_MM_DD_HH_MM1);
    public static SimpleDateFormat SDF_YYYY_MM_DD1 = new SimpleDateFormat(YYYY_MM_DD1);
    public static SimpleDateFormat SDF_MMDDHHMMSS = new SimpleDateFormat(MMDDHHMMSS);

    /**
     * 
     * @param date
     *            日期
     * @param format
     *            格式化字符串
     * @return
     */
    public static String format(Date date, String format) {
        if (date == null)
            return "";
        return new java.text.SimpleDateFormat(format).format(date);
    }

    /**
     * 
     * @param date
     *            带豪秒级的日期
     * @param format
     *            格式化字符串
     * @return
     */
    public static String format(Timestamp date, String format) {
        if (date == null)
            return "";
        return new java.text.SimpleDateFormat(format).format(date);
    }

    /**
     * 
     * @param date
     *            日期字符串
     * @return
     */
    public static Date format(String date) {
        return java.sql.Date.valueOf(date);
    }

    /**
     * 将java.util.Date转化为java.sql.Date
     * 
     * @param date
     *            java.util.Date的时间
     * @return
     */
    public static java.sql.Date converlUtilDate(Date date) {
        java.sql.Date sqlDate = null;
        sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }

    /**
     * 将java.sql.Date转化为java.util.Date
     * 
     * @param date
     *            java.sql.Date的时间
     * @return
     */
    public static Date convertSqlDate(java.sql.Date date) {
        Date utilDate = null;
        utilDate = new Date(date.getTime());
        return utilDate;
    }

    /**
     * 当前时间的毫秒时长
     */
    public static final Long CURRENT_TIME_MILLIS = System.currentTimeMillis();

    /**
     * 
     * @param date
     * @return
     */
    public static String getWeek(Date date) {

        String weeks[] = { "", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        return weeks[getWeekOfDay(date)];
    }

    /**
     * 
     * @param date
     * @return
     */
    public static String getWeek2(Date date) {

        String weeks[] = { "", "日", "一", "二", "三", "四", "五", "六" };
        return weeks[getWeekOfDay(date)];
    }

    /**
     * 获取当天是一周中的周几 如：2012-08-29 返回值为：4
     * 
     * @param date
     * @return
     */
    public static int getWeekOfDay(Date date) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.CHINA);
        dateFormat.format(date);
        Calendar cal = dateFormat.getCalendar();
        int DAY_OF_WEEK = cal.get(Calendar.DAY_OF_WEEK);

        return DAY_OF_WEEK;
    }

    /**
     * 是否润年
     * 
     * @param year
     * @return
     */
    public static boolean isLeap(int year) {

        boolean div4 = year % 4 == 0;
        boolean div100 = year % 100 == 0;
        boolean div400 = year % 400 == 0;
        return div4 && (!div100 || div400);
    }

    /**
     * 获取某月份有多少天
     * 
     * @param date
     * 
     * @return
     */
    public static int getDays(Date date) {
        return getEndDayOfMonth(getYear(date), getMonth(date));
    }

    /**
     * 获取一个月中的第几天
     * 
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        return Cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取一年中的第几天
     * 
     * @param date
     * @return
     */
    public static int getDayOfYear(Date date) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        return Cal.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取月份
     * 
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        return (Cal.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取年份
     * 
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);

        return Cal.get(Calendar.YEAR);
    }

    /**
     * 获取一个月的有多少天
     * 
     * @param year
     *            年份
     * @param month
     *            月份
     * @return
     */
    public static int getEndDayOfMonth(int year, int month) {
        boolean pass = true;
        String message = "";
        if (year < 0) {
            message = "The year[" + year + "] is negative";
            pass = false;
        }
        if (month - 1 < 0 || month - 1 >= 12) {
            message = message + " The month[" + month + "] is illegal";
            pass = false;
        }
        if (!pass) {
            throw new IllegalArgumentException(message);
        }

        int[] days = new int[] { 31, isLeap(year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        return days[month - 1];
    }

    /**
     * 增加小时
     * 
     * @param dDate
     * @param hour
     * @return
     */
    public static Date addHour(Date dDate, long hour) {
        return addSecond(dDate, hour * 60L * 60L);
    }

    /**
     * 增加分
     * 
     * @param dDate
     * @param minute
     * @return
     */
    public static Date addMinute(Date dDate, long minute) {

        return addSecond(dDate, minute * 60L);

    }

    /**
     * 增加秒
     * 
     * @param dDate
     * @param second
     * @return
     */
    public static Date addSecond(Date dDate, long second) {

        long datems = dDate.getTime();
        long secondms = second * 1000L;
        long newDateMs = datems + secondms;
        Date result = new Date(newDateMs);
        return result;

    }

    /**
     * 增加月份
     * 
     * @param dDate
     * @param iNbMonth
     * @return
     */
    public static Date addMonth(Date dDate, int iNbMonth) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        int month = cal.get(2);
        month += iNbMonth;
        int year = month / 12;
        month %= 12;
        cal.set(2, month);
        if (year != 0) {
            int oldYear = cal.get(1);
            cal.set(1, year + oldYear);
        }
        return cal.getTime();
    }

    /**
     * 增加天数
     * 
     * @param dDate
     * @param iNbDay
     * @return
     */
    public static Date addDay(Date dDate, long iNbDay) {
        return addHour(dDate, iNbDay * 24);
    }

    public static Date addWeek(Date str2date, int iNday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(str2date);
        calendar.add(Calendar.WEEK_OF_YEAR, iNday);
        return calendar.getTime();
    }

    /**
     * 对日期增加年份
     * 
     * @param dDate
     * @param iNbYear
     * @return
     */
    public static Date addYear(Date dDate, int iNbYear) {
        return addMonth(dDate, iNbYear * 12);

    }

    /**
     * 两个时间相减，得到两者豪秒级的差值
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static long subDate(Date date1, Date date2) {
        long date1ms = date1.getTime();
        long date2ms = date2.getTime();
        return date2ms - date1ms;
    }

    /**
     * 两个时间相减，得到两个天数的差值
     * 
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     * 
     */
    public static int subDate(String startDate, String endDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
        Date date1 = formatter.parse(startDate);
        Date date2 = formatter.parse(endDate);
        return (int) subDate(date1, date2) / (24 * 3600 * 1000);

    }

    /**
     * 获取日期段中的所有日期
     * 
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 获取两个日期中间是所有月份
     * 
     * @param dateBegin
     * @param dateEnd
     * @return
     */
    public static String[] findMonthsBetweenDifferentDate(String dateBegin, String dateEnd) {
        java.sql.Date date1 = null; // 开始日期
        java.sql.Date date2 = null; // 结束日期
        try {
            date1 = java.sql.Date.valueOf(dateBegin.substring(0, 7) + "-01");
            date2 = java.sql.Date.valueOf(dateEnd.substring(0, 7) + "-02");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        // 定义集合存放月份
        List<String> list = new ArrayList<String>();
        // 添加第一个月，即开始时间
        // list.add(dateBegin.substring(0,7));
        c1.setTime(date1);
        c2.setTime(date2);
        while (c1.compareTo(c2) < 0) {
            Date ss = c1.getTime();
            String str = DateUtil.format(ss, YYYY_MM_DD);
            str = str.substring(0, str.lastIndexOf("-"));
            str = str.length() == 7 ? str : str.substring(0, 5) + "0" + str.substring(5);
            list.add(str);
            c1.add(Calendar.MONTH, 1);// 开始日期加一个月直到等于结束日期为止
        }
        // 存放入数组
        String[] str = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            str[i] = (String) list.get(i);
        }
        return str;
    }

    /**
     * 得到默认的日期时间的字符串格式
     * 
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateTimeToStrDefault(Date date) {
        String dateStr = null;
        if (date != null) {
            dateStr = SDF_YYYY_MM_DD_2HH_MM.format(date);
        }
        return dateStr;
    }

    /**
     * 时间转换函数(由Stirng转换成Date对象类型)
     * 
     * @param dstr
     *            （日期串:yyyy-MM-dd HH:mm:ss)
     * @param sdf
     *            （SimpleDateFormat对象，可从常量中取到，如果为空默认为yyyy-MM-dd格式)
     * @return Date
     * @throws ParseException
     * @throws Exception
     */
    public static Date strToDate(String dateStr, SimpleDateFormat sdf) throws ParseException {
        Date date = null;
        if (dateStr != null && !"".equals(dateStr)) {
            if (sdf != null) {
                date = sdf.parse(dateStr);
            } else {
                date = SDF_YYYY_MM_DD.parse(dateStr);
            }
        }
        return date;
    }
    
    public static void main(String[]args){
    	System.out.println(DateUtil.format("2014-04-15"));
//    	System.out.println(DateUtil.format("2014-04-15 12:23:00"));
    }

}
