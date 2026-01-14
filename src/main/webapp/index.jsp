<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>COMP3601-Lab10</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
	<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
	<header class="primary-header flex">
            <h1>COMP 4601</h1>
     </header>
     <main>
     <section>
     <h1>Employee Management System</h1>
     <p>This web application allows users to perform CRUD (Create, Read, Update, Delete) 
     operations on an employee database. Users can view a list of employees, add new employees, 
     search for specific employees by ID, and remove employees from the database. The application 
     follows a Model 2 design pattern with separate Controller, Presentation, Business, and Data layers, 
     ensuring a well-structured and maintainable codebase. It utilizes JSP for the user interface and 
     interacts with a SQL Server database to store and retrieve employee information.</p>
     </section>
     <%-- 
    <div class="grid"> 
	    <jsp:include page="employees.jsp" />
	    <jsp:include page="addEmployees.jsp" />
	    <jsp:include page="findEmployees.jsp" />
	    <jsp:include page="deleteEmployee.jsp" />
    </div> 
    --%>
    <div style="margin-top: 2rem; text-align: center;">
         <a href="${pageContext.request.contextPath}/login.jsp" class="btn-main" style="display: inline-block; text-decoration: none;">Access the Application</a>
     </div>
    </main>
</body>
</html>