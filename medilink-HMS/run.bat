@echo off
echo ════════════════════════════════════════════════════════════════
echo           Starting Medilink HMS Application
echo ════════════════════════════════════════════════════════════════
echo.

echo Checking Java version...
java -version
echo.

echo Setting JAVA_HOME...
set JAVA_HOME=C:\Program Files\Java\jdk-21.0.10
echo JAVA_HOME set to: %JAVA_HOME%
echo.

echo Starting Spring Boot application...
echo This will take 15-20 seconds...
echo.

mvnw.cmd spring-boot:run

pause
