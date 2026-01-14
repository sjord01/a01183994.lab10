<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
   <title>Login Error - COMP3601-Lab10</title>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
   <style>
       .error-container {
           max-width: 500px;
           margin: 2rem auto;
           padding: 2rem;
           border: 1px solid var(--grey-01);
           border-radius: 8px;
       }
       .error-message {
           color: #d32f2f;
           padding: 1rem;
           background-color: #ffebee;
           border-radius: 4px;
           margin: 1rem 0;
       }
       .login-link {
           display: inline-block;
           margin-top: 1rem;
           padding: 0.75rem 2rem;
           background-color: var(--light-blue-02);
           border-radius: 50px;
           text-decoration: none;
           color: inherit;
       }
       .login-link:hover {
           background-color: var(--orange-01);
       }
   </style>
</head>
<body>
   <header class="primary-header flex">
       <h1>COMP 3601</h1>
   </header>
   <main>
       <section class="error-container">
           <h1>Login Failed</h1>
           
           <div class="error-message">
               <p>Invalid username or password. Please try again.</p>
           </div>
           
           <p>Please check your credentials and try again. Make sure you are using the correct username and password.</p>
           
           <a href="${pageContext.request.contextPath}/login.jsp" class="login-link">Return to Login</a>
       </section>
   </main>
</body>
</html>