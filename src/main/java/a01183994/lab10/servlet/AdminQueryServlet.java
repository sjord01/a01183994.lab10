package a01183994.lab10.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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
import java.util.regex.Pattern;

@WebServlet("/adminQuery")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class AdminQueryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Pattern to validate SELECT queries more strictly
    private static final Pattern SELECT_PATTERN = Pattern.compile(
        "^SELECT\\s+.+\\s+FROM\\s+\\w+.*$",
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );
    
    // Pattern to detect dangerous SQL keywords
    private static final Pattern DANGEROUS_PATTERN = Pattern.compile(
        ".*(;|--|/\\*|\\*/|xp_|sp_|exec|execute|insert|update|delete|drop|create|alter|grant|revoke).*",
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String query = request.getParameter("query");
        
        if (query == null || query.trim().isEmpty()) {
            request.setAttribute("error", "Query cannot be empty");
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
            return;
        }
        
        // Validate that it's a safe SELECT query
        String trimmedQuery = query.trim();
        if (!SELECT_PATTERN.matcher(trimmedQuery).matches()) {
            request.setAttribute("error", "Invalid SELECT query format");
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
            return;
        }
        
        // Check for dangerous SQL keywords
        if (DANGEROUS_PATTERN.matcher(trimmedQuery).matches()) {
            request.setAttribute("error", "Query contains forbidden SQL keywords");
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
            // Try to use DataSource if available, otherwise fall back to DriverManager
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:comp/env");
                DataSource ds = (DataSource) envContext.lookup("jdbc/jspweb");
                conn = ds.getConnection();
            } catch (Exception e) {
                // Fallback to DriverManager for development
                conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            }
            
            // Set read-only mode and timeout for safety
            conn.setReadOnly(true);
            stmt = conn.createStatement();
            stmt.setQueryTimeout(10); // 10 seconds timeout
            
            rs = stmt.executeQuery(query);
            
            // Get metadata to extract column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            
            // Process results with a limit to prevent memory issues
            List<Map<String, Object>> results = new ArrayList<>();
            int rowCount = 0;
            int maxRows = 1000; // Limit to 1000 rows
            
            while (rs.next() && rowCount < maxRows) {
                Map<String, Object> row = new HashMap<>();
                for (String columnName : columnNames) {
                    row.put(columnName, rs.getObject(columnName));
                }
                results.add(row);
                rowCount++;
            }
            
            // Set attributes for JSP
            request.setAttribute("queryResults", results);
            request.setAttribute("columnNames", columnNames);
            request.setAttribute("queryExecuted", true);
            
            if (rowCount >= maxRows) {
                request.setAttribute("error", "Results limited to " + maxRows + " rows");
            }
            
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
