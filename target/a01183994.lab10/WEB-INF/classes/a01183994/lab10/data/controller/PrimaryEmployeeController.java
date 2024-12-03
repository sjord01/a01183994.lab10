package a01183994.lab10.data.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import a01183994.lab10.database.dao.EmployeeDao;
import a01183994.lab10.database.util.ApplicationException;
import a01183994.lab10.data.Employee;
import a01183994.lab10.database.*;



public class PrimaryEmployeeController implements EmployeeController
{
    private Properties databaseProperties;

    private final EmployeeDao employeeDao;

    /**
     * Constructs a {@code DefaultEmployeeManager} with the provided database credentials.
     * This constructor initializes a {@link Database} object, checks if the employee table exists,
     * and creates and populates it if necessary. It also loads database properties from a file.
     *
     * @param dbUrl      the URL of the database.
     * @param dbUser     the username to connect to the database.
     * @param dbPassword the password to connect to the database.
     * @throws IOException  if an error occurs while reading the properties file.
     * @throws SQLException if a database access error occurs during initialization.
     */
    public PrimaryEmployeeController (final String dbUrl,
                                  final String dbUser,
                                  final String dbPassword) throws IOException, SQLException
    {
        readAndLoadPropertiesFile();
        Database database = new Database(databaseProperties, dbUrl, dbUser, dbPassword);

        database.getConnection();
        employeeDao = new EmployeeDao(database);

        if(!Database.tableExists(DbConstants.EMPLOYEES_TABLE_NAME))
        {
            employeeDao.createTable();
            employeeDao.insertAll();
        }
    }

    /**
     * Reads and loads the database properties file.
     * This method attempts to load the database configuration from a properties file
     * specified by {@link DbConstants#DB_PROPERTIES_FILENAME}. If the file is not found,
     * it throws a {@code RuntimeException}.
     *
     * @throws IOException if an error occurs while reading the properties file.
     * @throws RuntimeException if the properties file does not exist.
     */
    private void readAndLoadPropertiesFile() throws IOException
    {
    	databaseProperties = new Properties();
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DbConstants.DB_PROPERTIES_FILENAME))
        {
            if(inputStream == null)
            {
                System.out.println("Properties file does not exist. " + DbConstants.DB_PROPERTIES_FILENAME);
                throw new RuntimeException("Properties file does not exist");
            }
            databaseProperties.load(inputStream);
        }
    }

    /**
     * Retrieves all employees from the database.
     * This method delegates to the {@link EmployeeDao#getAll()} method to fetch
     * a list of employees from the database.
     *
     * @return a {@link List} of {@link Employee} objects representing all employees in the database.
     * @throws SQLException if a database access error occurs.
     * @throws ApplicationException 
     */
    @Override
    public List<Employee> getEmployees() throws SQLException, ApplicationException
    {
        return employeeDao.getAll();
    }
}
