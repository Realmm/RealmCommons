package net.jamesandrew.commons.time;

import java.util.concurrent.TimeUnit;

public final class Time {

    public static String format(TimeUnit timeUnit, long duration) {
        long millis = timeUnit.toMillis(duration);
        int secs = (int) (millis / 1000) % 60;
        int mins = (int) ((millis / (1000*60)) % 60);
        int hours = (int) ((millis) / (1000*60*60)) % 24;
        int days = (int) ((millis) / (1000*60*60*24)) % 7;
        int weeks = (int) ((millis) / (1000*60*60*24*7));

        StringBuilder sb = new StringBuilder();

        boolean m = mins > 0, h = hours > 0, d = days > 0, w = weeks > 0;

        if (w) {
            d = true;
            h = true;
            m = true;
        } else if (d) {
            h = true;
            m = true;
        } else if (h) {
            m = true;
        }

        if (w) {
            sb.append(weeks);
            sb.append(" ");
            sb.append(weeks != 1 ? "weeks, " : "week, ");
        }

        if (d) {
            sb.append(days);
            sb.append(" ");
            sb.append(days != 1 ? "days, " : "day, ");
        }

        if (h) {
            sb.append(hours);
            sb.append(" ");
            sb.append(hours != 1 ? "hours, " : "hour, ");
        }

        if (m) {
            sb.append(mins);
            sb.append(" ");
            sb.append(mins != 1 ? "minutes, " : "minute, ");
        }

        sb.append(secs);
        sb.append(" ");
        sb.append(secs != 1 ? "seconds" : "second");

        return sb.toString();
    }

}
