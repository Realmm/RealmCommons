package net.jamesandrew.commons.database.sql.helper;

import net.jamesandrew.commons.database.sql.MySQL;
import net.jamesandrew.commons.manager.ManagedHashSet;

import java.sql.PreparedStatement;
import java.util.Arrays;

public final class SQLHelper {

    private ManagedHashSet<SQLTable> tables = new ManagedHashSet<>();
    private final MySQL mySQL;

    public SQLHelper(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public void createTable(String name, boolean async, SQLColumn[] columns) {
        if (columns.length <= 1) return;
        StringBuilder sqlSb = new StringBuilder("CREATE TABLE IF NOT EXISTS " + name + " (");

        for (int i = 0; i < columns.length; i++) {
            SQLColumn c = columns[i];
            sqlSb.append(c.getKey());
            sqlSb.append(" ");
            sqlSb.append(c.getSql());
            sqlSb.append(", ");
            if (i == columns.length - 1) {
                sqlSb.append("PRIMARY KEY (");
                sqlSb.append(columns[0].getKey());
                sqlSb.append("))");
            }
        }

        if (async) {
            mySQL.operateAsync(PreparedStatement::execute, sqlSb.toString());
        } else mySQL.operate(PreparedStatement::execute, sqlSb.toString());
    }

    public void insert(String name, boolean update, boolean async, SQLObject<Object>[] columns) {
//        if (objects.length <= 1 || columns.length != objects.length) return;
        StringBuilder sqlSb = new StringBuilder("INSERT INTO ");
        StringBuilder valuesSb = new StringBuilder("VALUES (");
        sqlSb.append(name);
        sqlSb.append(" (");

        for (int i = 0; i < columns.length; i++) {
            sqlSb.append(columns[i].getKey());
            valuesSb.append("?");
            if (i + 1 != columns.length) {
                sqlSb.append(", ");
                valuesSb.append(", ");
            }
        }

        sqlSb.append(") ");
        valuesSb.append(")");

        sqlSb.append(valuesSb.toString());

        if (update) {
            sqlSb.append(" ON DUPLICATE KEY UPDATE ");
            for (int i = 0; i < columns.length; i++) {
                SQLObject<Object> c = columns[i];
                if (!c.isUpdateable()) continue;
                sqlSb.append(c.getKey());
                sqlSb.append("=VALUES(");
                sqlSb.append(c.get());
                sqlSb.append(")");

                if (i == columns.length - 1) continue;
                Arrays.stream(Arrays.copyOfRange(columns, i, columns.length - 1)).filter(SQLObject::isUpdateable).findFirst().ifPresent(co -> sqlSb.append(", "));
            }
        }

        if (async) {
            mySQL.operateAsync(PreparedStatement::execute, sqlSb.toString(), Arrays.stream(columns).map(SQLObject::get).toArray(Object[]::new));
        } else mySQL.operate(PreparedStatement::execute, sqlSb.toString(), Arrays.stream(columns).map(SQLObject::get).toArray(Object[]::new));

    }

}
