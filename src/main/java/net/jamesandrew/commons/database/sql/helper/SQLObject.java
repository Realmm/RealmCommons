package net.jamesandrew.commons.database.sql.helper;

public class SQLObject<T> {

    private final String key;
    private final T t;
    private boolean update = true;

    public SQLObject(String key, T t) {
        this.key = key;
        this.t = t;
    }

    public void setUpdateable(boolean update) {
        this.update = update;
    }

    public boolean isUpdateable() {
        return update;
    }

    public String getKey() {
        return key;
    }

    public T get() {
        return t;
    }

}
