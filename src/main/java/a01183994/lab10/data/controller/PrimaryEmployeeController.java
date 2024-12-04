package a01183994.lab10.data.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import a01183994.lab10.data.Employee;
import a01183994.lab10.database.util.ApplicationException;

public class PrimaryEmployeeController extends EmployeeController {

    public PrimaryEmployeeController(String dbUrl, String dbUser, String dbPassword) throws IOException, SQLException {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public List<Employee> getEmployees() throws SQLException, ApplicationException {
        return employeeDao.getAll();
    }
}