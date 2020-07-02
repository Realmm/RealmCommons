package net.jamesandrew.commons.number;

import java.util.concurrent.ThreadLocalRandom;

public final class Number {

    private Number(){}

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
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

}
