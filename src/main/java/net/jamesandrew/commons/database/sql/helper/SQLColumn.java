package net.jamesandrew.commons.database.sql.helper;

public class SQLColumn {

    private final String key, sql;
    private boolean update = true;

    public SQLColumn(String key, String sql) {
        this.key = key;
        this.sql = sql;
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

    public String getSql() {
        return sql;
    }
}
