package net.jamesandrew.commons.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public final class Number {

    private Number(){}

    public static boolean isLong(String s) {
        try {
            Long.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInt(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String s) {
        try {
            Double.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String s) {
        try {
            Float.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isShort(String s) {
        try {
            Short.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isByte(String s) {
        try {
            Byte.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static long getLong(String s) {
        return Long.valueOf(s);
    }

    public static short getShort(String s) {
        return Short.valueOf(s);
    }

    public static byte getByte(String s) {
        return Byte.valueOf(s);
    }

    public static int getInt(String s) {
        return Integer.valueOf(s);
    }

    public static double getDouble(String s) {
        return Double.valueOf(s);
    }

    public static float getFloat(String s) {
        return Float.valueOf(s);
    }

    /**
     * Returns random with origin and bound being inclusive
     */
    public static long getRandom(int origin, int bound) {
        return ThreadLocalRandom.current().nextLong(origin, bound - 1);
    }

    public static boolean isBetween(int a, int b, int value, boolean inclusive) {
        if (a == b) return value == a;
        return a > b ? inclusive ? value >= b && value <= a : value > b && value < a : inclusive ? value >= a && value <= b : value > a && value < b;
    }

    public static double round(double value, int precision) {
        return BigDecimal.valueOf(value).setScale(precision, RoundingMode.HALF_EVEN).doubleValue();
//        int scale = (int) Math.pow(10, precision);
//        return (double) Math.round(value * scale) / scale;
    }

    public static int getNextInt(int[] a, int start) {
        return getNextInt(Arrays.stream(a).boxed().collect(Collectors.toList()), start);
    }

    public static int getNextInt(Collection<Integer> c, int start) {
        int i = start;

        /*
        c = {1, 2}, start = 0, next = 0 // start below set
        c = {0, 1, 2}, start = 3, next = 3 // start above set
        c = {0, 1, 2}, start = 0, next = 3 // start beginning of set
        c = {-1, 0, 1}, start = 0, next = 2 // start middle of set
        c = {0, 1, 2}, start = 2, next = 3 // start end of set
         */

        if (c.stream().allMatch(v -> v < start)) return start; // start above set
        if (c.stream().allMatch(v -> v > start)) return start; // start below set

        if (c.stream().distinct().count() == 1) {
            int x = c.stream().distinct().findFirst().orElse(-1);
            if (start == x) {
                return x + 1;
            } else return start;
        }

        /*
        Amount of distinct numbers in collection is >= 2
        Start value is inside the set somewhere
         */
        for (int x : c.stream().sorted().distinct().collect(Collectors.toList())) {
            if (x < i) continue;
            if (x != i) return i;
            i++;
        }

        return i;
    }

}
