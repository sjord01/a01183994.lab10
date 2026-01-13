<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Login - COMP3601-Lab10</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <style>
        .login-container {
            max-width: 400px;
            margin: 2rem auto;
            padding: 2rem;
            border: 1px solid var(--grey-01);
            border-radius: 8px;
        }
        .login-form {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }
        .form-group {
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
        }
        .form-group label {
            font-family: var(--main-font-medium);
            font-size: 1.15rem;
        }
        .form-group input {
            padding: 0.75rem;
            font-size: 1rem;
            border: 1px solid var(--grey-01);
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <header class="primary-header flex">
        <h1>COMP 3601</h1>
    </header>
    <main>
        <section class="login-container">
            <h1>Login</h1>
            <p>Please enter your credentials to access the application.</p>
            
            <form class="login-form" method="POST" action="${pageContext.request.contextPath}/j_security_check">
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
