package net.jamesandrew.commons.database.sql.helper;

public class SQLObject {

    private final String key;
    private final Object o;
    private boolean update = true;

    public SQLObject(String key, Object o) {
        this.key = key;
        this.o = o;
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

    public Object get() {
        return o;
    }

}
