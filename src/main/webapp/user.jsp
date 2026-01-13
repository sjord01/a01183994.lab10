<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>User Page - COMP3601-Lab10</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <style>
        .user-container {
            max-width: 600px;
            margin: 2rem auto;
            padding: 2rem;
            border: 1px solid var(--grey-01);
            border-radius: 8px;
        }
        .welcome-message {
            padding: 1rem;
            background-color: #e3f2fd;
            border-radius: 4px;
            margin: 1rem 0;
        }
        .logout-link {
            display: inline-block;
            margin-top: 1rem;
            padding: 0.75rem 2rem;
            background-color: var(--light-blue-02);
            border-radius: 50px;
            text-decoration: none;
            color: inherit;
        }
        .logout-link:hover {
            background-color: var(--orange-01);
        }
    </style>
</head>
<body>
    <header class="primary-header flex">
        <h1>COMP 3601</h1>
    </header>
    <main>
        <section class="user-container">
            <h1>User Page</h1>
            
            <div class="welcome-message">
                <p><strong>Welcome, <%= request.getUserPrincipal().getName() %>!</strong></p>
                <p>You are logged in with the 'user' role.</p>
            </div>
            
            <p>This is a protected page accessible only to users with the 'user' role.</p>
            <p>You have successfully authenticated and can access user resources.</p>
            
            <form method="POST" action="${pageContext.request.contextPath}/logout">
                <button type="submit" class="btn-main">Logout</button>
            </form>
        </section>
    </main>
</body>
</html>
