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
    <table>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>DOB</th>
        </tr>
<%--        <jsp:useBean id="employees" scope="request" type="java.util.List"/>--%>
        <c:forEach var="employee" items="${employees}">
            <tr>
                <td><c:out value="${employee.id}"/> </td>
                <td><c:out value="${employee.firstName}"/> </td>
                <td><c:out value="${employee.lastName}"/> </td>
                <td><c:out value="${employee.dateOfBirth}"/> </td>
            </tr>
        </c:forEach>

        <c:if test="${empty employees}">
            <p>No employees found.</p>
        </c:if>

        <c:if test="${not empty error}">
            <p><c:out value="${error}"/></p>
        </c:if>
    </table>
    </main>
</body>
</html>