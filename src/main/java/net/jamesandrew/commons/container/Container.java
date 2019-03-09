package net.jamesandrew.commons.container;

import java.util.*;

public final class Container {

    private Container(){}

    public static <T> List<T> getDuplicates(Collection<T> list) {
        final List<T> duplicatedObjects = new ArrayList<>();

        Set<T> set = new HashSet<>();

        for (T t : list) {
            if (set.contains(t)) {
                duplicatedObjects.add(t);
                continue;
            }
            set.add(t);
        }

        return duplicatedObjects;
    }

    public static <T> Map<T, Integer> getDuplicatesCounted(Collection<T> list) {
        final Map<T, Integer> duplicatedObjects = new HashMap<>();

        for (T t : list) {
            if (getDuplicates(list).contains(t)) duplicatedObjects.put(t, duplicatedObjects.containsKey(t) ? duplicatedObjects.get(t) + 1 : 1);
        }

        return duplicatedObjects;
    }

    public static <T> boolean hasDuplicate(Collection<T> list) {
        return !getDuplicates(list).isEmpty();
    }

}
