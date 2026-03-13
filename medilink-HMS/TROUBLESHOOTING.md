# 🔧 TROUBLESHOOTING GUIDE

## Common Issues and Solutions

---

## ❌ Issue 1: "JAVA_HOME is not defined correctly"

### Symptoms:
```
The JAVA_HOME environment variable is not defined correctly
```

### Solution:
```powershell
# Find where Java is installed
where java

# Set JAVA_HOME (adjust path to your JDK)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"

# Verify
echo $env:JAVA_HOME
java -version
```

### If Still Not Working:
1. Make sure you installed JDK (not JRE)
2. Check the exact installation path
3. Try common paths:
   - `C:\Program Files\Java\jdk-17`
   - `C:\Program Files\Eclipse Adoptium\jdk-17.0.x`
   - `C:\Program Files\OpenJDK\jdk-17`

---

## ❌ Issue 2: Database Connection Failed

### Symptoms:
```
org.postgresql.util.PSQLException: Connection refused
```

### Solution:
1. **Check PostgreSQL is running:**
   - Open Services (Windows + R → services.msc)
   - Find "postgresql-x64-XX"
   - Status should be "Running"
   - If not, right-click → Start

2. **Verify database exists:**
   ```sql
   -- Open pgAdmin or psql
   CREATE DATABASE medilink_db;
   ```

3. **Check credentials in application.properties:**
   ```properties
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```
   Update if your password is different

---

## ❌ Issue 3: Port 8080 Already in Use

### Symptoms:
```
Web server failed to start. Port 8080 was already in use.
```

### Solution:
**Option 1: Stop other application using port 8080**
```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID with actual process ID)
taskkill /PID <PID> /F
```

**Option 2: Change port in application.properties**
```properties
server.port=8081
```

---

## ❌ Issue 4: Sample Data Not Inserted

### Symptoms:
- Application starts but no "DATABASE INITIALIZATION COMPLETE!" message
- GET requests return empty arrays

### Solution:
1. **Check if data already exists:**
   ```
   GET http://localhost:8080/api/locations
   ```
   If it returns data, initialization was skipped (this is normal)

2. **Force re-initialization:**
   - Stop the application (Ctrl + C)
   - Drop all tables in pgAdmin
   - Restart application

3. **Check console for errors:**
   - Look for red error messages during startup
   - Check if DataInitializer ran

---

## ❌ Issue 5: Province Search Returns Empty

### Symptoms:
```
GET /api/users/search?provinceName=Kigali City
→ Returns: []
```

### Solution:
1. **Verify users exist:**
   ```
   GET http://localhost:8080/api/users
   ```

2. **Check user locations:**
   - Users should have `location_id` = 5 or 10 (Village level)
   - Not 1, 2, 3, 4 (Province/District/Sector/Cell)

3. **Verify location hierarchy:**
   ```
   GET http://localhost:8080/api/locations/5
   ```
   Should show parent chain to Province

4. **Check exact province name:**
   - Use "Kigali City" (not "Kigali" or "kigali city")
   - Case-sensitive!

---

## ❌ Issue 6: Compilation Errors in VS Code

### Symptoms:
```
The type java.lang.Object cannot be resolved
950 errors in VS Code
```

### Solution:
This is a VS Code issue, not your code!

1. **Clean Java Language Server:**
   - Ctrl + Shift + P
   - Type: "Java: Clean Java Language Server Workspace"
   - Press Enter
   - Click "Yes"

2. **Reload Window:**
   - Ctrl + Shift + P
   - Type: "Reload Window"
   - Press Enter

3. **Build Project:**
   - Ctrl + Shift + P
   - Type: "Java: Build Project"
   - Press Enter

4. **Verify with Maven:**
   ```bash
   ./mvnw.cmd clean compile
   ```
   If this shows "BUILD SUCCESS", your code is fine!

---

## ❌ Issue 7: Maven Download Slow

### Symptoms:
- Maven wrapper downloading dependencies forever
- Stuck at "Downloading..."

### Solution:
1. **Wait patiently** - First run downloads all dependencies (can take 5-10 minutes)
2. **Check internet connection**
3. **Try again** - Sometimes downloads fail, just restart

---

## ❌ Issue 8: Postman Collection Import Failed

### Symptoms:
- "Invalid collection format"
- Import button grayed out

### Solution:
1. **Verify file exists:**
   - Check `Medilink_HMS_COMPLETE.postman_collection.json` is in project folder

2. **Re-import:**
   - Postman → Import → Select file
   - Make sure you select the JSON file (not .md files)

3. **Manual testing:**
   - If import fails, copy URLs from TESTING_GUIDE.md
   - Create requests manually in Postman

---

## ❌ Issue 9: Application Starts but Endpoints Return 404

### Symptoms:
```
GET http://localhost:8080/api/users
→ 404 Not Found
```

### Solution:
1. **Check application started successfully:**
   - Look for "Started MedilinkHmsApplication" in console

2. **Verify port:**
   - Application should be on port 8080
   - Check application.properties: `server.port=8080`

3. **Check URL:**
   - Use `http://localhost:8080/api/users` (not https)
   - Include `/api` prefix

4. **Check controller mapping:**
   - Controllers should have `@RestController` and `@RequestMapping("/api/...")`

---

## ❌ Issue 10: Duplicate Key Error

### Symptoms:
```
ERROR: duplicate key value violates unique constraint
```

### Solution:
This happens if you try to insert data that already exists.

1. **For username duplicates:**
   - Use different username
   - Or delete existing user first

2. **For national ID duplicates:**
   - Use different national ID
   - Or delete existing patient first

3. **Clear all data:**
   - Stop application
   - Drop all tables in pgAdmin
   - Restart application (will recreate with fresh data)

---

## 🆘 Still Having Issues?

### Check These Files:
1. **SETUP_AND_RUN.md** - Detailed setup instructions
2. **TESTING_GUIDE.md** - All test cases
3. **PROJECT_STATUS.md** - Implementation details

### Verify Your Setup:
```bash
# Java version
java -version

# JAVA_HOME
echo $env:JAVA_HOME

# PostgreSQL connection
psql -U postgres -d medilink_db -c "SELECT version();"

# Application logs
# Check console output for errors
```

### Contact Instructor:
- Available at AUCA every day 10am-1pm in room 204
- Bring your laptop to show the specific error

---

## ✅ Quick Verification Commands

Run these to verify everything is working:

```bash
# 1. Java installed
java -version

# 2. JAVA_HOME set
echo $env:JAVA_HOME

# 3. Compile project
./mvnw.cmd clean compile

# 4. Start application
./mvnw.cmd spring-boot:run

# 5. Test endpoint (in another terminal or browser)
curl http://localhost:8080/api/locations
```

If all these work, you're ready! ✅

---

## 🎯 Most Common Issue: JAVA_HOME

90% of issues are because JAVA_HOME is not set correctly.

**Quick Fix:**
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
./mvnw.cmd spring-boot:run
```

That's it! 🚀
