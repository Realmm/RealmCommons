package net.jamesandrew.commons.number;

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

    public static int getInt(String s) {
        return Integer.valueOf(s);
    }

    public static double getDouble(String s) {
        return Double.valueOf(s);
    }

    public static float getFloat(String s) {
        return Float.valueOf(s);
    }

}
