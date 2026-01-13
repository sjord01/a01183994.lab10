package a01183994.lab10.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/adminQuery")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class AdminQueryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is in admin role using programmatic security
        if (!request.isUserInRole("admin")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Admin role required.");
            return;
        }
        
        String query = request.getParameter("query");
        
        if (query == null || query.trim().isEmpty()) {
            request.setAttribute("error", "Query cannot be empty");
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
            return;
        }
        
        // Validate that it's a SELECT query
        String trimmedQuery = query.trim().toUpperCase();
        if (!trimmedQuery.startsWith("SELECT")) {
            request.setAttribute("error", "Only SELECT queries are allowed");
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
            return;
        }
        
        // Get database configuration from context parameters
        String dbUrl = getServletContext().getInitParameter("db.url");
        String dbUsername = getServletContext().getInitParameter("db.username");
        String dbPassword = getServletContext().getInitParameter("db.password");
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // Execute the query
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            
            // Get metadata to extract column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            
            // Process results
            List<Map<String, Object>> results = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (String columnName : columnNames) {
                    row.put(columnName, rs.getObject(columnName));
                }
                results.add(row);
            }
            
            // Set attributes for JSP
            request.setAttribute("queryResults", results);
            request.setAttribute("columnNames", columnNames);
            request.setAttribute("queryExecuted", true);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error executing query: " + e.getMessage());
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                // Log error but don't fail
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
        
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }
}
