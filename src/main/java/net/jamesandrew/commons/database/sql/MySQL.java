package net.jamesandrew.commons.database.sql;

import net.jamesandrew.commons.database.sql.helper.SQLHelper;
import net.jamesandrew.commons.logging.Logger;

import java.sql.*;
import java.util.concurrent.CompletableFuture;

public abstract class MySQL implements SQLDataType {

    private Connection connection;
    private final String host, database, username, password;
    private boolean printError = true;
    private final SQLHelper helper;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("The driver for MySQL was not found");
        }
    }

    public MySQL(String host, String database, String username, String password) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;

        connection = getNewConnection();
        helper = new SQLHelper(this);
        ping();
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void ping() {
        final int delay = 10;
        CompletableFuture.runAsync(() -> {
            Statement statement = null;
            try {
                if (connection != null && !connection.isClosed()) {
                    statement = connection.createStatement();
                    statement.execute("SELECT 1");
                }
            } catch (SQLException e) {
                connection = getNewConnection();
                Logger.error("Connection issue, will try again in " + delay + " seconds");
                if (shouldPrintErrors()) e.printStackTrace();
            } finally {
                if (statement != null) close(statement);
            }
        }).thenAcceptAsync(v -> {
            try {
                Thread.sleep(delay * 1000);
                ping();
            } catch (InterruptedException e) {
                if (shouldPrintErrors()) e.printStackTrace();
            }
        });
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean hasLiveConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = getNewConnection();
                return !(connection == null || connection.isClosed());
            }
        } catch (SQLException e) {
            if (shouldPrintErrors()) e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean shouldPrintErrors() {
        return printError;
    }

    @Override
    public void setShouldPrintErrors(boolean printError) {
        this.printError = printError;
    }

    @Override
    public Connection getNewConnection() {
        try {
            String url = "jdbc:mysql://" + host + ":3306/" + database;
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized PreparedStatement prepare(String statement) throws SQLException {
        Connection c = getConnection();
        if (c == null || c.isClosed()) return getNewConnection().prepareStatement(statement);
        return c.prepareStatement(statement);
    }

    public SQLHelper getHelper() {
        return helper;
    }

}

