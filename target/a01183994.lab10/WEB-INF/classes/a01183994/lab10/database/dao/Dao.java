package a01183994.lab10.database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import a01183994.lab10.database.Database;
import a01183994.lab10.database.util.ApplicationException;

public abstract class Dao<T> {
    protected final Database database;
    protected final String tableName;

    protected Dao(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    public abstract void createTable() throws SQLException;
    public abstract void dropTable() throws SQLException;
    public abstract List<T> getAll() throws SQLException, ApplicationException;
    public abstract void insertAll() throws SQLException;

    protected void executeSQLScript(final String script) throws SQLException {
        String[] sqlBatches = script.split("GO");

        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            
            for (String sql : sqlBatches) {
                sql = sql.trim();
                if (!sql.isEmpty()) {
                    statement.execute(sql);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error performing SQL operation in the " + tableName + " table", e);
        }
    }

    protected void dropTableIfExists() throws SQLException {
        String query = String.format("DROP TABLE IF EXISTS %s", tableName);
        executeSQLScript(query);
    }
}