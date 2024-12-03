package a01183994.lab10.database;

public interface DbConstants {
    String DB_PROPERTIES_FILENAME = "db.properties";
    String DB_DRIVER_KEY = "db.driver";
    String DB_URL_KEY = "db.url";
    String DB_USER_KEY = "db.user";
    String DB_PASSWORD_KEY = "db.password";
    String TABLE_PREFIX = "A01183994_";
	String CUSTOMERS_TABLE_NAME = TABLE_PREFIX + "Customers";
	String EMPLOYEES_TABLE_NAME = TABLE_PREFIX + "Employees";
	String EMPLOYEE_CREATE_TABLE_SCRIPT_NAME = "Employees_CreateTable.sql";
    String EMPLOYEE_INSERT_ALL_SCRIPT_NAME   = "Employees_Insert.sql";
}