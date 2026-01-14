# Testing Checklist for Security Implementation

## Pre-deployment Setup
- [ ] WildFly is installed and running
- [ ] Users added to ApplicationRealm (admin/admin, user/user)
- [ ] SSL/HTTPS is configured on WildFly (port 8443)
- [ ] Database is accessible at java-sql.ad.bcit.ca:1433

## Build and Deploy
- [ ] Run `mvn clean package` - builds successfully
- [ ] WAR file created at `target/a01183994.lab10.war`
- [ ] Deploy WAR to `$JBOSS_HOME/standalone/deployments/`
- [ ] Check deployment logs for errors

## Authentication Tests

### Test 1: Redirect to Login
- [ ] Navigate to `http://localhost:8080/a01183994.lab10/`
- [ ] **Expected**: Automatically redirected to `/login.jsp`

### Test 2: Invalid Credentials
- [ ] Enter wrong username/password
- [ ] **Expected**: Redirected to `/login-error.jsp` with error message

### Test 3: User Login
- [ ] Login with username: `user`, password: `user`
- [ ] **Expected**: Access granted to main application
- [ ] Can access `/user.jsp`
- [ ] Cannot access `/admin.jsp` (should get 403 Forbidden)

### Test 4: Admin Login
- [ ] Login with username: `admin`, password: `admin`
- [ ] **Expected**: Access granted to all pages
- [ ] Can access `/admin.jsp`
- [ ] Can access `/user.jsp`

## Authorization Tests

### Test 5: User Role Access
- [ ] Login as `user`
- [ ] Try to access `http://localhost:8080/a01183994.lab10/admin.jsp`
- [ ] **Expected**: 403 Forbidden error

### Test 6: Admin Role Access
- [ ] Login as `admin`
- [ ] Access `https://localhost:8443/a01183994.lab10/admin.jsp`
- [ ] **Expected**: Admin page loads successfully

## SSL/HTTPS Tests

### Test 7: Admin Page Requires SSL
- [ ] Try to access admin page via HTTP: `http://localhost:8080/a01183994.lab10/admin.jsp`
- [ ] **Expected**: Automatically redirected to HTTPS (port 8443)

## Database Query Tests

### Test 8: Valid SELECT Query
- [ ] Login as `admin`
- [ ] Access admin page
- [ ] Enter query: `SELECT * FROM Employees`
- [ ] **Expected**: Results displayed in table

### Test 9: SQL Injection Prevention
Test these queries - all should be blocked:
- [ ] `DROP TABLE Employees` - **Expected**: Error "Invalid SELECT query format"
- [ ] `SELECT * FROM Employees; DELETE FROM Employees;` - **Expected**: Error "Query contains forbidden SQL keywords"
- [ ] `INSERT INTO Employees VALUES (...)` - **Expected**: Error "Query contains forbidden SQL keywords"
- [ ] `UPDATE Employees SET ...` - **Expected**: Error "Query contains forbidden SQL keywords"

### Test 10: Query Timeout
- [ ] Enter a complex query that takes long to execute
- [ ] **Expected**: Query times out after 10 seconds with error

### Test 11: Result Limit
- [ ] Enter query that returns more than 1000 rows
- [ ] **Expected**: Only 1000 rows returned with warning message

## Logout Tests

### Test 12: User Logout
- [ ] Login as any user
- [ ] Click "Logout" button
- [ ] **Expected**: Redirected to login page
- [ ] Session invalidated
- [ ] Accessing protected pages requires re-login

### Test 13: Session Invalidation
- [ ] Login and note the session ID
- [ ] Logout
- [ ] Try to access protected page with old session
- [ ] **Expected**: Redirected to login page

## Security Verification

### Test 14: Direct Access Prevention
- [ ] Without logging in, try to access:
  - [ ] `/index.jsp` - **Expected**: Redirected to login
  - [ ] `/user.jsp` - **Expected**: Redirected to login
  - [ ] `/admin.jsp` - **Expected**: Redirected to login

### Test 15: Code Security Scan
- [ ] Run CodeQL security scanner
- [ ] **Expected**: No vulnerabilities found ✅

## Files Verification

### Test 16: All Required Files Present
- [ ] `src/main/webapp/login.jsp` - Login form
- [ ] `src/main/webapp/login-error.jsp` - Error page
- [ ] `src/main/webapp/user.jsp` - User page
- [ ] `src/main/webapp/admin.jsp` - Admin page
- [ ] `src/main/java/a01183994/lab10/servlet/AdminQueryServlet.java` - Query handler
- [ ] `src/main/java/a01183994/lab10/servlet/LogoutServlet.java` - Logout handler
- [ ] `src/main/webapp/WEB-INF/web.xml` - Security configuration
- [ ] `src/main/webapp/WEB-INF/jboss-web.xml` - WildFly config
- [ ] `WILDFLY_CONFIG.md` - Setup instructions
- [ ] `IMPLEMENTATION_SUMMARY.md` - Implementation details

## Test Results Summary
- Total Tests: 16
- Passed: ___
- Failed: ___
- Notes: _________________________________

