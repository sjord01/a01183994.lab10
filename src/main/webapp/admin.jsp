<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Page - COMP3601-Lab10</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <style>
        .admin-container {
            max-width: 900px;
            margin: 2rem auto;
            padding: 2rem;
            border: 1px solid var(--grey-01);
            border-radius: 8px;
        }
        .welcome-message {
            padding: 1rem;
            background-color: #fff3e0;
            border-radius: 4px;
            margin: 1rem 0;
        }
        .query-form {
            display: flex;
            flex-direction: column;
            gap: 1rem;
            margin: 2rem 0;
        }
        .query-form label {
            font-family: var(--main-font-medium);
            font-size: 1.15rem;
        }
        .query-form textarea {
            padding: 0.75rem;
            font-size: 1rem;
            font-family: monospace;
            border: 1px solid var(--grey-01);
            border-radius: 4px;
            min-height: 100px;
        }
        .error-message {
            color: #d32f2f;
            padding: 1rem;
            background-color: #ffebee;
            border-radius: 4px;
            margin: 1rem 0;
        }
        .success-message {
            color: #2e7d32;
            padding: 1rem;
            background-color: #e8f5e9;
            border-radius: 4px;
            margin: 1rem 0;
        }
        .results-container {
            margin-top: 2rem;
        }
    </style>
</head>
<body>
    <header class="primary-header flex">
        <h1>COMP 3601</h1>
    </header>
    <main>
        <section class="admin-container">
            <h1>Admin Page</h1>
            
            <div class="welcome-message">
                <p><strong>Welcome, <%= request.getUserPrincipal().getName() %>!</strong></p>
                <p>You are logged in with the 'admin' role. This page is secured with SSL.</p>
            </div>
            
            <h2>Database Query</h2>
            <p>Enter a SELECT query to retrieve data from the database:</p>
            
            <form class="query-form" method="POST" action="${pageContext.request.contextPath}/adminQuery">
                <label for="query">SQL Query:</label>
                <textarea id="query" name="query" placeholder="SELECT * FROM Employees" required></textarea>
                
                <div>
                    <button type="submit" class="btn-main">Execute Query</button>
                </div>
            </form>
            
            <c:if test="${not empty error}">
                <div class="error-message">
                    <p><strong>Error:</strong> <c:out value="${error}"/></p>
                </div>
            </c:if>
            
            <c:if test="${not empty queryResults}">
                <div class="results-container">
                    <h3>Query Results:</h3>
                    <table>
                        <thead>
                            <tr>
                                <c:forEach var="column" items="${columnNames}">
                                    <th><c:out value="${column}"/></th>
                                </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="row" items="${queryResults}">
                                <tr>
                                    <c:forEach var="column" items="${columnNames}">
                                        <td><c:out value="${row[column]}"/></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            
            <c:if test="${not empty queryExecuted and empty queryResults and empty error}">
                <div class="success-message">
                    <p>Query executed successfully. No results to display.</p>
                </div>
            </c:if>
            
            <form method="POST" action="${pageContext.request.contextPath}/logout" style="margin-top: 2rem;">
                <button type="submit" class="btn-main">Logout</button>
            </form>
        </section>
    </main>
</body>
</html>
