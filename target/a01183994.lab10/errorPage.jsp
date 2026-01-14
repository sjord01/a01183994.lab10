<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<!DOCTYPE html>
<html>
<head>
    <title>COMP3601-Lab10</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>

<body>
	<header class="primary-header flex">
            <h1>COMP 3601</h1>
     </header>
     <main>
		<section class="error-page">
		    <div class="error-container">
		        <h2>Application Error</h2>
		        
		        <div class="error-details">
		            <h3>Error Details:</h3>
		            <p><strong>Status Code:</strong> ${pageContext.errorData.statusCode}</p>
		            <p><strong>Request URI:</strong> ${pageContext.errorData.requestURI}</p>
		            <p><strong>Servlet Name:</strong> ${pageContext.errorData.servletName}</p>
		            <p><strong>Exception Type:</strong> ${pageContext.exception['class'].name}</p>
		            <p><strong>Exception Message:</strong> ${pageContext.exception.message}</p>
		        </div>
		        
		        <div class="error-actions">
		            <a href="${pageContext.request.contextPath}" class="button">Return to Home Page</a>
		        </div>
		    </div>
		</section>
	</main>
  </body>
</html>