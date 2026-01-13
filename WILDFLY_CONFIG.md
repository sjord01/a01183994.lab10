# WildFly User Configuration Instructions

To configure the users for this application on WildFly, you need to add the following users to the ApplicationRealm.

## Method 1: Using add-user.sh/add-user.bat Script

Navigate to your WildFly installation directory and run:

```bash
# For Linux/Mac
$JBOSS_HOME/bin/add-user.sh

# For Windows
%JBOSS_HOME%\bin\add-user.bat
```

When prompted, add the following users:

### Admin User
- What type of user: **a** (Application User)
- Username: **admin**
- Password: **admin**
- Groups/Roles: **admin**
- Used for: one AS process to connect to another AS process: **no**

### Regular User
- What type of user: **a** (Application User)
- Username: **user**
- Password: **user**
- Groups/Roles: **user**
- Used for: one AS process to connect to another AS process: **no**

## Method 2: Manual Configuration

Alternatively, you can manually edit the following file:
`$JBOSS_HOME/standalone/configuration/application-users.properties`

Add these lines:
```
admin=<hashed_password>
user=<hashed_password>
```

And in `$JBOSS_HOME/standalone/configuration/application-roles.properties`:
```
admin=admin
user=user
```

## SSL Configuration for Admin Page

The admin page requires SSL (HTTPS). Ensure your WildFly server is configured with SSL/TLS.

### Enable HTTPS on WildFly

1. Generate a keystore (if not already present):
```bash
keytool -genkeypair -alias wildfly -keyalg RSA -keystore keystore.jks -keysize 2048
```

2. Configure the HTTPS connector in WildFly's standalone.xml or use the default configuration.

3. Access the admin page via: `https://localhost:8443/a01183994.lab10/admin.jsp`

## Testing the Application

After configuration:

1. Build and deploy the application:
```bash
mvn clean package
```

2. Access the application at: `http://localhost:8080/a01183994.lab10/`

3. You will be redirected to the login page

4. Login credentials:
   - **Admin**: username=admin, password=admin
   - **User**: username=user, password=user

5. Admin users can access:
   - `/admin.jsp` (via HTTPS)
   - All user-level pages

6. Regular users can access:
   - `/user.jsp`
   - Main application pages
