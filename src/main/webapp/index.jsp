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
            <h1>COMP 3601</h1>
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
     <section>
    <table>
    	<caption>Employee List</caption>
    	<thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">DOB</th>
        </tr>
        </thead>
<%--        <jsp:useBean id="employees" scope="request" type="java.util.List"/>--%>
        <c:forEach var="employee" items="${employees}">
            <tr>
                <td data-label="ID"><c:out value="${employee.id}"/> </td>
                <td data-label="First Name"><c:out value="${employee.firstName}"/> </td>
                <td data-label="Last Name"><c:out value="${employee.lastName}"/> </td>
                <td data-label="DOB"><c:out value="${employee.dateOfBirth}"/> </td>
            </tr>
        </c:forEach>

        <c:if test="${empty employees}">
            <p>No employees found.</p>
        </c:if>

        <c:if test="${not empty error}">
            <p><c:out value="${error}"/></p>
        </c:if>
    </table>
    </section>
    </main>
</body>
</html>