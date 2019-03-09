package net.jamesandrew.commons.manager;

import java.util.LinkedHashSet;

public class ManagedLinkedHashSet<E> extends Manager<E, LinkedHashSet<E>> {
    public ManagedLinkedHashSet() {
        super(new LinkedHashSet<>());
    }
}
