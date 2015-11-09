package net.ralphpina.kamcordwatch.utils;

import net.ralphpina.kamcordwatch.KamApplication;
import net.ralphpina.kamcordwatch.R;

import java.util.Calendar;

public final class TimeUtils {

    private static final long MINUTE_MS = 60000l;
    private static final long HOUR_MS   = 3600000l;
    private static final long DAY_MS    = 86400000l;
    private static final long MONTH_MS  = 2592000000l;

    public static String getHumanReadableTimeElapsed(long secondsElapsed) {
        long minutes = secondsElapsed / 60;
        long seconds = secondsElapsed % 60;

        String minutesStr = (minutes < 10) ? "0" + minutes : "" + minutes;
        String secondsStr = (seconds < 10) ? "0" + seconds : "" + seconds;
        return minutesStr + ":" + secondsStr;
    }

    public static String getHumanReadableTimeElapsedHMMSS(long secondsElapsed) {
        long hours = secondsElapsed / (60 * 60);
        secondsElapsed = secondsElapsed - (hours * 60 * 60);
        long minutes = secondsElapsed / 60;
        long seconds = secondsElapsed % 60;

        String minutesStr = (minutes < 10) ? "0" + minutes : "" + minutes;
        String secondsStr = (seconds < 10) ? "0" + seconds : "" + seconds;
        return hours + ":" + minutesStr + ":" + secondsStr;
    }

    public static String getStringForUploadTime(long uploadTime) {
        Calendar now = Calendar.getInstance();
        Calendar upload = Calendar.getInstance();
        upload.setTimeInMillis(uploadTime);

        long timeDiff = now.getTimeInMillis() - uploadTime;
        if (timeDiff < MINUTE_MS) {
            return getSecondsAgo(timeDiff);
        }
        if (timeDiff < HOUR_MS) {
            return getMinutesAgo(timeDiff);
        }
        if (timeDiff < DAY_MS) {
            return getHoursAgo(timeDiff);
        }
        if (timeDiff < MONTH_MS) {
            return getDaysAgo(timeDiff);
        }
        return getMonthsAgo(timeDiff);
    }

    private static String getSecondsAgo(long timeMs) {
        long sec = timeMs / 1000;
        return KamApplication.get().getString(R.string.seconds, sec);
    }

    private static String getMinutesAgo(long timeMs) {
        long sec = timeMs / 1000;
        long min = sec / 60;
        return KamApplication.get().getString(R.string.minutes, min);
    }

    private static String getHoursAgo(long timeMs) {
        long sec = timeMs / 1000;
        long min = sec / 60;
        long hours = min / 60;
        return KamApplication.get().getString(R.string.hours, hours);
    }

    private static String getDaysAgo(long timeMs) {
        long sec = timeMs / 1000;
        long min = sec / 60;
        long hours = min / 60;
        long days = hours / 24;
        return KamApplication.get().getString(R.string.days, days);
    }

    private static String getMonthsAgo(long timeMs) {
        long sec = timeMs / 1000;
        long min = sec / 60;
        long hours = min / 60;
        long days = hours / 24;
        long months = days / 30;
        return KamApplication.get().getString(R.string.months, months);
    }
}
