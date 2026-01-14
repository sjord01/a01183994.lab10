<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<section id="employees-list">
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