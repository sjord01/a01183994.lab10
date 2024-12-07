package a01183994.lab10.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import a01183994.lab10.data.Employee;
import a01183994.lab10.data.controller.PrimaryEmployeeController;
import a01183994.lab10.database.util.ErrorCode;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	//FOR DELETING AN EMPLOYEE INSTANCE
    	String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            try {
                String deleteId = request.getParameter("deleteId");
                ErrorCode result = employeeController.deleteEmployee(deleteId);
                
                // Set delete result attributes
                request.setAttribute("deleteResultCode", result.getCode());
                request.setAttribute("deleteResultDescription", result.getDescription());
                
                // Get and set employees list before forwarding
                List<Employee> employees = employeeController.getEmployees();
                request.setAttribute("employees", employees);
                
            } catch (Exception e) {
                request.setAttribute("deleteResultCode", 
                    ErrorCode.DELETE_UNSUCCESSFUL.getCode());
                request.setAttribute("deleteResultDescription", 
                    ErrorCode.DELETE_UNSUCCESSFUL.getDescription());
                
                // Get and set employees list even when delete fails
                try {
                    List<Employee> employees = employeeController.getEmployees();
                    request.setAttribute("employees", employees);
                } catch (Exception ex) {
                    request.setAttribute("error", "Error getting the list of employees");
                }
            }
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
      //FOR ADDING AN EMPLOYEE INSTANCE
    	
    	try {
            String id = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            LocalDate dob = LocalDate.parse(request.getParameter("dob"));

            Employee employee = new Employee(id, firstName, lastName, dob);
            
            ErrorCode result = employeeController.validateAndAddEmployee(employee);
            
            // Store result in session instead of request
            request.getSession().setAttribute("resultCode", result.getCode());
            request.getSession().setAttribute("resultDescription", result.getDescription());
            
            if (result == ErrorCode.SUCCESS_ADD) {
                request.getSession().setAttribute("success", true);
            }
            
            // Redirect instead of forwarding
            response.sendRedirect(request.getContextPath() + "/");
            
        } catch (Exception e) {
            request.getSession().setAttribute("resultCode", ErrorCode.INVALID_DATA.getCode());
            request.getSession().setAttribute("resultDescription", ErrorCode.INVALID_DATA.getDescription());
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
    	//FOR DELETING AN EMPLOYEE INSTANCE
    	Object deleteResultCode = req.getSession().getAttribute("deleteResultCode");
        if (deleteResultCode != null) {
            // Set both code and description as request attributes
            req.setAttribute("deleteResultCode", deleteResultCode);
            req.setAttribute("deleteResultDescription", 
                req.getSession().getAttribute("deleteResultDescription"));
            // Clear session attributes immediately after transfer
            req.getSession().removeAttribute("deleteResultCode");
            req.getSession().removeAttribute("deleteResultDescription");
            
            // Ensure these attributes are available for the current request
            req.setAttribute("showDeleteMessage", true);
        }
    	
    	//FOR FINDING AN EMPLOYEE INSTANCE
    	try {
            if (employeeController != null) {
                // Handle find employee request if searchId parameter exists
                String searchId = req.getParameter("searchId");
                if (searchId != null && !searchId.trim().isEmpty()) {
                    try {
                        Employee employee = employeeController.findEmployee(searchId.toUpperCase());
                        if (employee != null) {
                            // Found the employee
                            req.setAttribute("searchResult", 
                                employee.getFirstName() + " " + employee.getLastName());
                            req.setAttribute("searchResultCode", ErrorCode.SUCCESS_FIND.getCode());
                            req.setAttribute("searchResultDescription", ErrorCode.SUCCESS_FIND.getDescription());
                        } else {
                            // No match found
                            req.setAttribute("searchResultCode", ErrorCode.NO_MATCH.getCode());
                            req.setAttribute("searchResultDescription", ErrorCode.NO_MATCH.getDescription());
                        }
                    } catch (Exception e) {
                        req.setAttribute("searchResultCode", ErrorCode.INVALID_DATA.getCode());
                        req.setAttribute("searchResultDescription", ErrorCode.INVALID_DATA.getDescription());
                    }
                }
                
                // Get all employees for display
                List<Employee> employees = employeeController.getEmployees();
                req.setAttribute("employees", employees);
                
                // Handle add employee messages
                Object resultCode = req.getSession().getAttribute("resultCode");
                if (resultCode != null) {
                    req.setAttribute("resultCode", resultCode);
                    req.setAttribute("resultDescription", 
                        req.getSession().getAttribute("resultDescription"));
                    req.getSession().removeAttribute("resultCode");
                    req.getSession().removeAttribute("resultDescription");
                }
            } else {
                throw new ServletException("Employee Controller is null!");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Error processing request");
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
