<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<section id="delete-employee-form">
    <h2>Delete Employee</h2>
    
    <form action="${pageContext.request.contextPath}" method="post">
        <input type="hidden" name="action" value="delete">
        <div class="form-group">
            <label for="deleteId">Employee ID:</label>
            <input type="text" id="deleteId" name="deleteId" 
                   pattern="[Aa]0[0-9]{7}" 
                   title="ID must start with 'A0' followed by 7 digits" required>
        </div>
        
        <button class="btn-form" type="submit">Delete Employee</button>
    </form>
    
        
    <c:if test="${not empty deleteResultCode}">
        <div class="message ${deleteResultCode == 1 ? 'success' : 'error'}">
            Result Code: ${deleteResultCode} Description: ${deleteResultDescription}
        </div>
    </c:if>
    
</section>