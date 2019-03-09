package net.jamesandrew.commons.reflection;

import net.jamesandrew.commons.logging.Logger;
import org.reflections.Reflections;

import java.util.Set;

public final class Reflect {

    private Reflect(){}

    public static <T> Set<Class<? extends T>> getClassesExtending(Class<T> clazz) {
        Reflections reflections = new Reflections("");
        Set<Class<? extends T>> classes = reflections.getSubTypesOf(clazz);

        if (classes.size() <= 0) {
            Logger.warn("No classes extending " + clazz.getCanonicalName());
            return classes;
        }

        return classes;
    }

}
