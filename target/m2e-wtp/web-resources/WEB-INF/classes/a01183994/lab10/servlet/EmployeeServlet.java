package a01183994.lab10.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import a01183994.lab10.data.Employee;
import a01183994.lab10.data.controller.PrimaryEmployeeController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(urlPatterns = "")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private PrimaryEmployeeController employeeController;

    @Override
    public void init() throws ServletException {
        super.init();
        String url = getServletContext().getInitParameter("db.url");
        String username = getServletContext().getInitParameter("db.username");
        String password = getServletContext().getInitParameter("db.password");

        createConnection(url, username, password);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (employeeController != null) {
                List<Employee> employees = employeeController.getEmployees();
                req.setAttribute("employees", employees);
            } else {
                throw new ServletException("Employee Controller is null!");
            }
        } catch (Exception e) {
            System.out.println("Error getting the list of employees: " + e.getMessage());
            req.setAttribute("error", "Error getting the list of employees");
        }

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private void createConnection(String url, String username, String password) {
        System.out.println("Connecting to the database...");
        try {
            employeeController = new PrimaryEmployeeController(url, username, password);
        } catch (IOException | SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }
}
