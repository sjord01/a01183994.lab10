package a01183994.lab10.data.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import a01183994.lab10.data.Employee;
import a01183994.lab10.database.util.ApplicationException;
import a01183994.lab10.database.util.ErrorCode;

public class PrimaryEmployeeController extends EmployeeController {

    public PrimaryEmployeeController(String dbUrl, String dbUser, String dbPassword) throws IOException, SQLException {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public List<Employee> getEmployees() throws SQLException, ApplicationException {
        return employeeDao.getAll();
    }
    
    public void addEmployee(Employee employee) throws SQLException, ApplicationException {
        employeeDao.insertEmployee(employee);
    }
    
    public ErrorCode validateAndAddEmployee(Employee employee) throws SQLException, ApplicationException {
        ErrorCode validationResult = employeeDao.validateEmployee(employee);
        if (validationResult == ErrorCode.SUCCESS_ADD) {
            employeeDao.insertEmployee(employee);
        }
        return validationResult;
    }
    
    public Employee findEmployee(String id) throws SQLException, ApplicationException {
        // Capitalize the ID
        id = id.toUpperCase();
        return employeeDao.findEmployeeById(id);
    }
    
    public ErrorCode deleteEmployee(String id) throws SQLException, ApplicationException {
        id = id.toUpperCase();
		boolean deleted = employeeDao.deleteEmployee(id);
		return deleted ? ErrorCode.SUCCESS_DELETE : ErrorCode.DELETE_UNSUCCESSFUL;
    }
}