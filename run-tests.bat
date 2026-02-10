@echo off
echo ================================================
echo Employee Management System - Test Runner
echo ================================================
echo.
echo Prerequisites Check:
echo [1/3] Checking Java...
java -version
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java not found!
    pause
    exit /b 1
)

echo.
echo [2/3] Checking MySQL...
sc query MySQL80 | find "RUNNING"
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: MySQL is not running!
    echo Please start MySQL service first.
    pause
    exit /b 1
)

echo.
echo [3/3] Checking compiled classes...
if not exist "target\classes\com\employee\App.class" (
    echo ERROR: Application not compiled!
    echo Please compile the project first using your IDE.
    pause
    exit /b 1
)

echo.
echo ================================================
echo All prerequisites met!
echo ================================================
echo.
echo INSTRUCTIONS:
echo 1. Open this project in IntelliJ IDEA or Eclipse
echo 2. Right-click on src/main/java/com/employee/App.java
echo 3. Select "Run 'App.main()'"
echo 4. Use credentials: admin / admin123
echo.
echo OR use Maven:
echo   mvn spring-boot:run
echo.
echo ================================================
pause
