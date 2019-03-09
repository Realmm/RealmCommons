package net.jamesandrew.commons.manager;

import java.util.HashSet;

public class ManagedHashSet<E> extends Manager<E, HashSet<E>> {
    public ManagedHashSet() {
        super(new HashSet<>());
    }
}
