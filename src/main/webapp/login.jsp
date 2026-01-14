<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
   <title>Login - COMP4601-Lab10</title>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

</head>
<body>
   <header class="primary-header flex">
       <h1>COMP 4601</h1>
   </header>
   <main>
       <section class="login-container">
           <h1>Login</h1>
           <p>Please enter your credentials to access the application.</p>
           
            <form class="login-form" method="POST" action="j_security_check">
               <div class="form-group">
                   <label for="j_username">Username:</label>
                   <input type="text" id="j_username" name="j_username" required>
               </div>
               
               <div class="form-group">
                   <label for="j_password">Password:</label>
                   <input type="password" id="j_password" name="j_password" required>
               </div>
               
               <button type="submit" class="btn-main">Login</button>
           </form>
       </section>
   </main>
</body>
</html>