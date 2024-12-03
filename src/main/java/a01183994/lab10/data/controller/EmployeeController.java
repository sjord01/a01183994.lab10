package a01183994.lab10.data.controller;

import java.sql.SQLException;
import java.util.List;

import a01183994.lab10.data.Employee;
import a01183994.lab10.database.util.ApplicationException;

public interface EmployeeController {
	List<Employee> getEmployees() throws SQLException, ApplicationException;
}
