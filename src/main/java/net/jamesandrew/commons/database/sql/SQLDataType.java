package net.jamesandrew.commons.database.sql;

import net.jamesandrew.commons.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("Duplicates")
public interface SQLDataType extends SQLCloseable {

    default <R> SQLResponse<R> query(SQLFunction<? super ResultSet, R> function, String sql, Object... params) {
        if (!confirmLiveConnection()) return new SQLResponse<>();
        PreparedStatement statement = null;
        R returned = null;
        try {
            statement = prepare(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            ResultSet rs = statement.executeQuery();
            returned = function.apply(rs);
            close(rs);
        } catch (SQLException e) {
            if (shouldPrintErrors()) e.printStackTrace();
        } finally {
            close(statement);
        }
        return new SQLResponse<>(returned);
    }

    default <R> SQLResponse<R> operate(SQLFunction<? super PreparedStatement, R> function, String sql, Object... params) {
        if (!confirmLiveConnection()) return new SQLResponse<>();
        PreparedStatement statement = null;
        R returned = null;
        try {
            statement = prepare(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            returned = function.apply(statement);
        } catch (SQLException e) {
            if (shouldPrintErrors()) e.printStackTrace();
        } finally {
            close(statement);
        }
        return new SQLResponse<>(returned);
    }

    default <R> CompletableFuture<SQLResponse<R>> queryAsync(SQLFunction<? super ResultSet, R> function, String sql, Object... params) {
        return CompletableFuture.supplyAsync(() -> query(function, sql, params));
    }

    default <R> CompletableFuture<SQLResponse<R>> operateAsync(SQLFunction<? super PreparedStatement, R> function, String sql, Object... params) {
        return CompletableFuture.supplyAsync(() -> operate(function, sql, params));
    }

    default boolean confirmLiveConnection() {
        if (!hasLiveConnection()) {
            Logger.error("Database not found, consider checking access parameters");
            return false;
        }
        return true;
    }

    PreparedStatement prepare(String statement) throws SQLException;

    Connection getConnection();

    Connection getNewConnection();

    boolean hasLiveConnection();

    boolean shouldPrintErrors();

    void ping();

    void setShouldPrintErrors(boolean printError);

}
