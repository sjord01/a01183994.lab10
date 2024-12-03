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

public class EmployeeDao implements Dao<Employee> {
	public static final String TABLE_NAME;
    private final Database database;

    static
    {
        TABLE_NAME = DbConstants.EMPLOYEES_TABLE_NAME;
    }
    
    public EmployeeDao(final Database database)
    {
        this.database = database;
    }

	
	 public List<Employee> getAll() throws SQLException, ApplicationException
	    {
	        ArrayList<Employee> employees;
	        ResultSet           resultSet;

	        try(Connection connection = database.getConnection();
	            Statement statement = connection.createStatement())
	        {
	            String query = String.format("SELECT * FROM %s",
	                                         TABLE_NAME);
	            resultSet = statement.executeQuery(query);
	            employees = getEmployeesFromResultSet(resultSet);
	        }
	        catch(SQLException e)
	        {
	            System.out.println("error getting employees");
	            throw e;
	        }

	        return employees;
	    }
	
	public void listAllTablesNames() throws SQLException
    {
        ResultSet resultSet;
        try(Connection connection = database.getConnection();
            Statement statement = connection.createStatement())
        {
            String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }
    }

	    private ArrayList<Employee> getEmployeesFromResultSet(final ResultSet resultSet) throws SQLException, ApplicationException
	    {
	        final ArrayList<Employee> employees = new ArrayList<>();

	        while(resultSet.next())
	        {
	            final Employee employee;
	            final String   id        = resultSet.getString("ID");
	            final String   firstName = resultSet.getString("firstName");
	            final String   lastName  = resultSet.getString("lastName");
	            final String   dobString = resultSet.getString("dob");

	            if(dobString != null)
	            {
	                final LocalDate dob = LocalDate.parse(resultSet.getString("dob"));
	                employee = new Employee(id, firstName, lastName, dob);
	            }
	            else
	            {
	                employee = new Employee(id, firstName, lastName);
	            }

	            employees.add(employee);
	        }
	        return employees;
	    }
	    
	    public void dropTable() throws SQLException
	    {
	        try(Connection connection = database.getConnection();
	            Statement statement = connection.createStatement())
	        {
	            String query = String.format("DROP TABLE %s", TABLE_NAME);
	            statement.executeUpdate(query);
	        }
	        catch(Exception e)
	        {
	            System.out.println("error dropping table");
	            throw e;
	        }

	        System.out.println("Table " + TABLE_NAME + "dropped correctly");
	    }
	    

	    public void createTable() throws SQLException {
	        System.out.println("Creating table");
	        String ucIdentifier = "uc_" + DbConstants.TABLE_PREFIX + "EmployeeID";
	        String createTableSQLQuery = DbUtil.readSQLFile(DbConstants.EMPLOYEE_CREATE_TABLE_SCRIPT_NAME);
	        createTableSQLQuery = createTableSQLQuery.replace("Employees", TABLE_NAME);
	        createTableSQLQuery = createTableSQLQuery.replace("uc_EmployeeID", ucIdentifier);
	        executeSQLScript(createTableSQLQuery);
	    }

	    /**
	     * Inserts all employee records into the database using a pre-defined SQL script.
	     * The SQL script is read from the file specified by {@link DbConstants#EMPLOYEE_INSERT_ALL_SCRIPT_NAME}.
	     *
	     * @throws SQLException if a database access error occurs or the operation fails.
	     */
	    public void insertAll() throws SQLException
	    {
	        System.out.println("Inserting employees");
	        String insertAllSQLQuery = DbUtil.readSQLFile(DbConstants.EMPLOYEE_INSERT_ALL_SCRIPT_NAME);
	        insertAllSQLQuery = insertAllSQLQuery.replaceAll("Employees", TABLE_NAME);
	        executeSQLScript(insertAllSQLQuery);
	    }

	    /**
	     * Executes an SQL script containing multiple statements, separated by "GO".
	     *
	     * @param script the SQL script to execute.
	     * @throws SQLException if a database access error occurs or the script execution fails.
	     */
	    private void executeSQLScript(final String script) throws SQLException
	    {
	        String[] sqlBatches = script.split("GO");

	        try(Connection connection = database.getConnection();
	            Statement statement = connection.createStatement())
	        {
	            for(String sql : sqlBatches)
	            {
	                sql = sql.trim();
	                if(!sql.isEmpty())
	                {
	                    statement.execute(sql);
	                }
	            }
	        }
	        catch(Exception e)
	        {
	            System.out.println("Error performing SQL operation in the employees table: " + e.getMessage());
	            throw new SQLException(e);
	        }
	    }

}
