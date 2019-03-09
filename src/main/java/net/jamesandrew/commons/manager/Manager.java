package net.jamesandrew.commons.manager;

import net.jamesandrew.commons.exception.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class Manager<E, T extends Set<E>> {

    private final T set;

    public Manager(T t) {
        this.set = t;
    }

    public void register(E e) {
        Validator.notContains(e, set);
        set.add(e);
    }

    public void unregister(E e) {
        set.remove(e);
    }

    public boolean isRegistered(E e) {
        return set.stream().anyMatch(o -> o.equals(e));
    }

    public Collection<? extends E> getRegistered() {
        return Collections.unmodifiableCollection(set);
    }

    public boolean isEmpty() {
        return getRegistered().size() <= 0;
    }

    public void clear() {
        Iterator iter = set.iterator();
        while(iter.hasNext()) {
            iter.remove();
        }
    }

}
