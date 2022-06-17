package com.example.demo.utils;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;

/**
 * 日期Util类
 *
 * @author zhoushuhua
 */
public class DateUtil {

    public static final String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";

    public static final String DatePattern = "yyyy-MM-dd";

    public static final String FullHourDatePattern = "yyyy-MM-dd HH:00:00";

    public static final String EnFullHourDatePattern = "dd/MM/yyyy HH:mm:ss";

    public static final String HFDatePattern = "yyyy-MM-dd HH:mm";

    public static final String HDatePattern = "yyyy-MM-dd HH:00:00";

    public static final String DATE_FORMAT_PATTERN = "yyyyMMdd";

    public static final String MONTH_FORMAT_PATTERN = "yyyyMM";

    public static final String TIME_NUMBER_FORMAT_PATTERN = "yyyyMMddHHmmss";

    public static final String MonthDay_FORMAT_PATTERN = "MM-dd";

    static {
        // 尝试试从messages_zh_Cn.properties中获取defaultDatePattner.
        try {
            // Locale locale = LocaleContextHolder.getLocale();
            // defaultDatePattern =
            // ResourceBundle.getBundle(Constants.MESSAGE_BUNDLE_KEY,
            // locale).getString("date.default_format");
        } catch (MissingResourceException mse) {
            // do nothing
        }
    }

    /**
     * 获得默认的 date pattern
     *
     * @return String
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    /**
     * 返回预设Format的当前日期字符串
     *
     * @return String
     */
    public static String getToday() {
        return format(now(), DatePattern);
    }

    /**
     * 返回预设Format的当前时间字符串
     *
     * @return String
     */
    public static String getNow() {
        return format(now());
    }

    /**
     * 返回预设Format的当前时间字符串
     *
     * @return String
     */
    public static String getNowNumberStr() {
        return format(now(), TIME_NUMBER_FORMAT_PATTERN);
    }

    /**
     * 返回当前时间前/后N天的时间字符串
     *
     * @param day 正数为后N天 负数为前N天
     * @return
     */
    public static String getTimeAfterOrBeforeDays(int day) {
        return format(now().plusMinutes(day*24*60));
    }

    /**
     * 返回当前时间前/后N分钟的时间字符串
     *
     * @param min 正数为后N分钟 负数为前N分钟
     * @return
     */
    public static String getTimeAfterOrBeforeMinutes(int min) {
        return format(now().plusMinutes(min));
    }

    /**
     * 返回设定时间前/后N分钟的时间字符串
     *
     * @param min 正数为后N分钟 负数为前N分钟
     * @return
     */
    public static String getTimeAfterOrBeforeMinutes(LocalDateTime startTime, int min) {
        return format(startTime.plusMinutes(min));
    }

    /**
     * 获取当前日期字符串 yyyyMMdd
     *
     * @return yyyyMMdd
     */
    public static String getDateYMD() {
        return getDateYMD(now());
    }

    /**
     * 获取日期字符串 yyyyMMdd
     *
     * @return yyyyMMdd
     */
    public static String getDateYMD(LocalDateTime date) {
        return format(date, DATE_FORMAT_PATTERN);
    }

    /**
     * 返回当前时间
     *
     * @return Date实例
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 时间戳转时间
     *
     * @param timeStrap
     * @return
     */
    public static LocalDateTime longToTime(long timeStrap) {
        Instant instant = Instant.ofEpochMilli(timeStrap);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 使用预设Format格式化Date成字符串
     *
     * @return String
     */
    public static String format(LocalDateTime date) {
        return date == null ? "" : format(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     *
     * @return String
     */
    public static String format(LocalDateTime date, String pattern) {
        return date == null ? "" : date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 时间戳格式化时间字符串
     *
     * @param timeStrap
     * @param pattern
     * @return
     */
    public static String format(long timeStrap, String pattern) {
        return format(longToTime(timeStrap), pattern);
    }


    /**
     * 使用预设格式将字符串转为LocalDate
     * @param strDate
     * @param pattern
     * @return
     */
    public static LocalDate parseDate(String strDate, String pattern) {
        return LocalDate.parse(strDate, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDate 转 LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime LocalDateToLT(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIDNIGHT);
    }

    /**
     * 使用预设格式将字符串转为LocalDateTime
     *
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String strDate) {
        return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(defaultDatePattern));
    }

    /**
     * 使用参数Format将字符串转为LocalDateTime
     *
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String strDate, String pattern) {
        return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取指定时间是周几
     *
     * @param time
     * @return
     */
    public static int week(LocalDateTime time) {
        return time.getDayOfWeek().getValue();
    }

    /**
     * 获取加或减N月的第一天
     *
     * @param num
     * @return
     */
    public static LocalDateTime monthFirst(int num) {
        LocalDateTime newTime = plus(LocalDateTime.now(), num, ChronoUnit.MONTHS);
        newTime = newTime.with(TemporalAdjusters.firstDayOfMonth());
        return getDayStart(newTime);
    }

    /**
     * 获取加或减N月的最后天
     *
     * @param num
     * @return
     */
    public static LocalDateTime monthLast(int num) {
        LocalDateTime newTime = plus(LocalDateTime.now(), num, ChronoUnit.MONTHS);
        newTime = newTime.with(TemporalAdjusters.lastDayOfMonth());
        return getDayEnd(newTime);
    }


    /**
     * 获取加或减N周的第一天
     *
     * @param num
     * @return
     */
    public static LocalDateTime weekFirst(int num) {
        int week = week(LocalDateTime.now());
        LocalDateTime newTime = subtract(LocalDateTime.now(), week - 1, ChronoUnit.DAYS);
        newTime = plus(newTime, num * 7, ChronoUnit.DAYS);
        //formatTime(, "yyyy-MM-dd HH:mm:ss:SSS");
        return getDayStart(newTime);
    }

    /**
     * 获取加或减N周的最后一天
     *
     * @param num
     * @return
     */
    public static LocalDateTime weekLast(int num) {
        int week = week(LocalDateTime.now());
        LocalDateTime newTime = plus(LocalDateTime.now(), 7 - week, ChronoUnit.DAYS);
        newTime = plus(newTime, num * 7, ChronoUnit.DAYS);
        return getDayEnd(newTime);
    }

    /**
     * 自构建 LocalDateTime ==> 年，月，日，时，分
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hour
     * @param minute
     * @return
     */
    public static LocalDateTime of(int year, int month, int dayOfMonth, int hour, int minute) {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

    /**
     * 自构建 LocalDateTime ==> 年，月，日，时，分，秒，毫秒（精确到9位数）
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hour
     * @param minute
     * @param second
     * @param nanoOfSecond
     * @return
     */
    public static LocalDateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond) {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param time
     * @return
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 时间戳 获取指定日期的毫秒
     *
     * @param time
     * @return
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 时间戳 获取指定日期的秒
     *
     * @param time
     * @return
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间的指定格式 ==> yyyy-MM-dd HH:mm:ss:SSS  (HH是24小时制，而hh是12小时制, ss是秒，SSS是毫秒)
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
     *
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
     *
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime subtract(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }


    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     *
     * @param startTime
     * @param endTime
     * @param field     单位(年月日时分秒)
     **/
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }


    /**
     * 获取指定某一天的开始时间 00:00:00
     *
     * @param time
     * @return
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }


    /**
     * 获取指定某一天的结束时间  23:59:59.999
     *
     * @param time
     * @return
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time
                //.withDayOfMonth(1)    // 月
                //.withDayOfYear(2)     // 天
                .withHour(23)           // 时
                .withMinute(59)         // 分
                .withSecond(59)         // 秒
                .withNano(999999999);   // 毫秒（这里精确到9位数）
    }

    /**
     * 获取本周周一
     */
    public static LocalDateTime getWeekOfFirst(LocalDateTime time) {
        return time.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).
                plusDays(1).withHour(0).withMinute(0).withSecond(0);
    }

    /**
     * 获取本周周日
     *
     * @param time
     * @return
     */
    public static LocalDateTime getWeekOfLast(LocalDateTime time) {
        return time.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).
                minusDays(1).withHour(23).withMinute(59).withSecond(59);
    }

    /**
     * 获取本月第一天
     *
     * @param time
     * @return
     */
    public static LocalDateTime getMonthOfFirst(LocalDateTime time) {
        LocalDateTime firstday = time.with(TemporalAdjusters.firstDayOfMonth());
        return LocalDateTime.of(firstday.toLocalDate(), LocalTime.MIN);
    }

    /**
     * 获取本月最后一天
     *
     * @param time
     * @return
     */
    public static LocalDateTime getMonthOfLast(LocalDateTime time) {
        LocalDateTime lastDay = time.with(TemporalAdjusters.lastDayOfMonth());
        return LocalDateTime.of(lastDay.toLocalDate(), LocalTime.MAX);
    }

    /**
     * 获取本年第一天
     *
     * @param time
     * @return
     */
    public static LocalDateTime getYearOfFirst(LocalDateTime time) {
        LocalDateTime firstday = time.with(TemporalAdjusters.firstDayOfYear());
        return LocalDateTime.of(firstday.toLocalDate(), LocalTime.MIN);
    }

    /**
     * 获取本年最后一天
     *
     * @param time
     * @return
     */
    public static LocalDateTime getYearOfLast(LocalDateTime time) {
        LocalDateTime lastDay = time.with(TemporalAdjusters.lastDayOfYear());
        return LocalDateTime.of(lastDay.toLocalDate(), LocalTime.MAX);
    }

    /**
     * 获取当前时间月到 +N月的所有时间，一天一条数据 List<DateDays>
     *
     * @param num
     * @return
     */
    public static List<DateDays> getDateDaysUpList(Integer num) {
        LocalDateTime startTime = monthFirst(0);        // 本月第一天  00:00:00
        LocalDateTime entTime = monthLast(num);              // num月后的最后一天 23:59:59.999
        // 3个月的数据
        List<DateDays> everyDays = new ArrayList<>();
        // 第一天数据
        everyDays.add(new DateDays(startTime, week(startTime)));
        while (true) {
            // 获取一天后时间
            LocalDateTime nextDay = plus(everyDays.get(everyDays.size() - 1).dayTime, 1, ChronoUnit.DAYS);
            //大于两月后最后一天-跳出循环
            if (nextDay.isAfter(entTime)) {
                break;
            }
            everyDays.add(new DateDays(nextDay, week(nextDay)));
        }
        return everyDays;
    }

    /**
     * 获取日期端的数据保存对象
     */
    public static class DateDays {

        public DateDays() {
        }

        public DateDays(LocalDateTime dayTime, int week) {
            this.dayTime = dayTime;
            this.week = week;
        }

        //当天时间- 年月日/00:00:00
        private LocalDateTime dayTime;
        //当天是周几
        private int week;

        public LocalDateTime getDayTime() {
            return dayTime;
        }

        public void setDayTime(LocalDateTime dayTime) {
            this.dayTime = dayTime;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

    }

    /**
     * 获取两个日期的时间差 秒
     * @param oldTime
     * @param newTime
     * @return
     */
    public static Long getSecondsBetweenTime(LocalDateTime oldTime, LocalDateTime newTime){
        return getSecondsByTime(newTime) - getSecondsByTime(oldTime);
    }

    /**
     * 秒换算为时分秒
     *
     * @param time
     * @return
     */
    public static String getConvert(int time) {
        try {

            if (time < 60) {
                return time + "秒";
            } else if ((time / 60) < 60) {
                return time / 60 + "分" + time % 60 + "秒";
            } else {
                return time / 60 / 60 + "小时" + (time % 3600) / 60 + "分" + time % 60 % 60 + "秒";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
