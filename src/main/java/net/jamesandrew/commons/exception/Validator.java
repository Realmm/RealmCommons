package net.jamesandrew.commons.exception;

import java.util.NoSuchElementException;
import java.util.Set;

public final class Validator {

    private Validator(){}

    public static <T> void contains(T object, Set<T> objectSet) {
        if (!objectSet.contains(object)) throw new NoSuchElementException("The object \'" + object.getClass().getCanonicalName() + "\' was not found");
    }

    public static <T> void notContains(T object, Set<T> objectSet) {
        if (objectSet.contains(object)) throw new NoSuchElementException("The object \'" + object.getClass().getCanonicalName() + "\' was found");
    }

}
