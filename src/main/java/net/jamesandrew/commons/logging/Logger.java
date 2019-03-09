package net.jamesandrew.commons.logging;

import java.util.logging.Level;
import java.util.stream.Stream;

public final class Logger {

    private static java.util.logging.Logger l = java.util.logging.Logger.getLogger("RealmCommonsLogger");
    private static boolean enableDebug = true;

    private Logger(){}

    public static void setLogger(java.util.logging.Logger logger) {
        l = logger;
    }

    public static <T> void log(T t) {
        l.log(Level.INFO, t.toString());
    }

    public static <T> void warn(T t) {
        l.log(Level.WARNING, t.toString());
    }

    public static <T> void error(T t) {
        l.log(Level.SEVERE, t.toString());
    }

    public static <T> void debug(T t) {
        if (enableDebug) l.log(Level.SEVERE, t.toString());
    }

    public static <T> void debug(Stream<T> tStream) {
        tStream.forEach(Logger::debug);
    }

    public static void setEnableDebug(boolean enable) {
        enableDebug = enable;
    }

}
