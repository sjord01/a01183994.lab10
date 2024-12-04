package a01183994.lab10.database.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtil {

    public static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        try (ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null)) {
            while (resultSet.next()) {
                String rsTableName = resultSet.getString("TABLE_NAME");
                if (rsTableName.equalsIgnoreCase(tableName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String readSQLFile(final String fileName) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new FileNotFoundException(fileName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error reading SQL file");
        }
        return sb.toString();
    }
}