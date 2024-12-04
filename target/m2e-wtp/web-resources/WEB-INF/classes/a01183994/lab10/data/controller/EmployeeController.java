package a01183994.lab10.data.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import a01183994.lab10.data.Employee;
import a01183994.lab10.database.Database;
import a01183994.lab10.database.DbConstants;
import a01183994.lab10.database.dao.EmployeeDao;
import a01183994.lab10.database.util.ApplicationException;

public abstract class EmployeeController {
    protected Properties databaseProperties;
    protected EmployeeDao employeeDao;

    protected EmployeeController(String dbUrl, String dbUser, String dbPassword) throws IOException, SQLException {
        readAndLoadPropertiesFile();
        Database database = new Database(databaseProperties, dbUrl, dbUser, dbPassword);
        database.getConnection();
        employeeDao = new EmployeeDao(database);

        if (!Database.tableExists(DbConstants.EMPLOYEES_TABLE_NAME)) {
            employeeDao.createTable();
            employeeDao.insertAll();
        }
    }

    private void readAndLoadPropertiesFile() throws IOException {
        databaseProperties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DbConstants.DB_PROPERTIES_FILENAME)) {
            if (inputStream == null) {
                throw new IOException("Properties file does not exist: " + DbConstants.DB_PROPERTIES_FILENAME);
            }
            databaseProperties.load(inputStream);
        }
    }

    public abstract List<Employee> getEmployees() throws SQLException, ApplicationException;
}