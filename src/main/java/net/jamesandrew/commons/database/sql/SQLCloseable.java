package net.jamesandrew.commons.database.sql;

import java.sql.*;

@SuppressWarnings("Duplicates")
public interface SQLCloseable {

    default void close(Statement statement) {
        if (statement == null) return;
        try {
            if (statement.isClosed()) return;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default void close(Connection connection) {
        if (connection == null) return;
        try {
            if (connection.isClosed()) return;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default void close(ResultSet resultSet) {
        if (resultSet == null) return;
        try {
            if (resultSet.isClosed()) return;
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default boolean canClose(Object o) {
        return o instanceof ResultSet || o instanceof Connection || o instanceof Statement;
    }

}
