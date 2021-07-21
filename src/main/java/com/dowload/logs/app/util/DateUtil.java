package com.dowload.logs.app.util;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    public final static double DMILLISECONDS = 1000;

    public final static double DSECONDS = 60;

    public final static double DMINUTES = 60;

    public final static double DHOURS = 24;

    public final static double DDAY = 365;

    public final static long MILLISECONDS = 1000;

    public final static long SECONDS = 60;

    public final static long MINUTES = 60;

    public final static long HOURS = 24;

    public final static long DAY = 365;

    private DateUtil() throws InstantiationException {
        throw new InstantiationException("You can't create new instance of DateUtil.");
    }

    public static double totalTimeMilliseconds( Date initDate, Date finishDate ) {
        return (initDate.getTime() - finishDate.getTime());
    }

    public static double totalTimeSeconds( Date initDate, Date finishDate ) {
        return (initDate.getTime() - finishDate.getTime()) / DMILLISECONDS;
    }

    public static double totalTimeMinutes( Date initDate, Date finishDate ) {
        return (initDate.getTime() - finishDate.getTime()) / DMILLISECONDS / DSECONDS;
    }

    public static double totalTimeHours( Date initDate, Date finishDate ) {
        return (initDate.getTime() - finishDate.getTime()) / DMILLISECONDS / DSECONDS / DMINUTES;
    }

    public static double totalTimeDays( Date initDate, Date finishDate ) {
        return (initDate.getTime() - finishDate.getTime()) / DMILLISECONDS / DSECONDS / DMINUTES / DHOURS;
    }

    public static void sleep( long milliseconds ) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    public static Date addTime( Date date, int field, int time ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, time);
        return calendar.getTime();
    }

    public static String timeMessage( Date initDate, Date finishDate ) {
        StringBuilder sb = new StringBuilder("Total time: ");
        long time = finishDate.getTime() - initDate.getTime();
        if (time == 1) {
            sb.append(time).append(" millisecond.");
        } else if (time > 1 && time < 999) {
            sb.append(time).append(" milliseconds.");
        } else if (time == 1000) {
            sb.append(time / MILLISECONDS).append(" second.");
        } else if (time > 1000 && time < 60000) {
            sb.append(DecimalUtil.roundDecimal(time / DMILLISECONDS, 3)).append(" seconds.");
        } else if (time == 60000) {
            sb.append(time / MILLISECONDS / SECONDS).append(" minute.");
        } else if (time > 60000 && time < 3600000) {
            sb.append(DecimalUtil.roundDecimal(time / DMILLISECONDS / DSECONDS, 3)).append(" minutes.");
        } else if (time == 3600000) {
            sb.append(time / MILLISECONDS / SECONDS / HOURS).append(" hour.");
        } else if (time > 3600000 && time < 86400000) {
            sb.append(DecimalUtil.roundDecimal(time / DMILLISECONDS / DSECONDS / DHOURS,3)).append(" hours.");
        } else if (time == 86400000) {
            sb.append(time / MILLISECONDS / SECONDS / HOURS / DAY).append(" day.");
        } else {
            sb.append(DecimalUtil.roundDecimal(time / DMILLISECONDS / DSECONDS / DHOURS / DDAY, 3)).append(" days.");
        }
        return sb.toString();
    }
}
