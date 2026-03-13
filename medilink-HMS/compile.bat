@echo off
echo ════════════════════════════════════════════════════════════════
echo           Compiling Medilink HMS Project
echo ════════════════════════════════════════════════════════════════
echo.

echo Checking Java version...
java -version
echo.

echo Setting JAVA_HOME...
set JAVA_HOME=C:\Program Files\Java\jdk-21.0.10
echo JAVA_HOME set to: %JAVA_HOME%
echo.

echo Cleaning and compiling...
mvnw.cmd clean compile

echo.
echo ════════════════════════════════════════════════════════════════
echo If you see "BUILD SUCCESS" above, your code is ready!
echo ════════════════════════════════════════════════════════════════
echo.

pause
