package a01183994.lab10.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import a01183994.lab10.data.Employee;
import a01183994.lab10.database.Database;
import a01183994.lab10.database.DbConstants;
import a01183994.lab10.database.util.ApplicationException;
import a01183994.lab10.database.util.DbUtil;

public class EmployeeDao extends Dao<Employee> {

    public EmployeeDao(final Database database) {
        super(database, DbConstants.EMPLOYEES_TABLE_NAME);
    }

    @Override
    public List<Employee> getAll() throws SQLException, ApplicationException {
        List<Employee> employees = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", tableName);
        
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            employees = getEmployeesFromResultSet(resultSet);
        } catch (SQLException e) {
            throw e;
        }

        return employees;
    }

    @Override
    public void createTable() throws SQLException {
        String createTableSQLQuery = DbUtil.readSQLFile(DbConstants.EMPLOYEE_CREATE_TABLE_SCRIPT_NAME);
        createTableSQLQuery = createTableSQLQuery.replace("Employees", tableName);
        executeSQLScript(createTableSQLQuery);
    }

    @Override
    public void dropTable() throws SQLException {
        dropTableIfExists();
    }

    @Override
    public void insertAll() throws SQLException {
        String insertAllSQLQuery = DbUtil.readSQLFile(DbConstants.EMPLOYEE_INSERT_ALL_SCRIPT_NAME);
        insertAllSQLQuery = insertAllSQLQuery.replaceAll("Employees", tableName);
        executeSQLScript(insertAllSQLQuery);
    }

    public void listAllTablesNames() throws SQLException {
        String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
        
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                @SuppressWarnings("unused")
				String tableName = resultSet.getString("TABLE_NAME");
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private ArrayList<Employee> getEmployeesFromResultSet(final ResultSet resultSet) throws SQLException, ApplicationException {
        final ArrayList<Employee> employees = new ArrayList<>();

        while (resultSet.next()) {
            final String id = resultSet.getString("ID");
            final String firstName = resultSet.getString("firstName");
            final String lastName = resultSet.getString("lastName");
            final String dobString = resultSet.getString("dob");

            final Employee employee;
            if (dobString != null) {
                final LocalDate dob = LocalDate.parse(dobString);
                employee = new Employee(id, firstName, lastName, dob);
            } else {
                employee = new Employee(id, firstName, lastName);
            }

            employees.add(employee);
        }
        return employees;
    }
}