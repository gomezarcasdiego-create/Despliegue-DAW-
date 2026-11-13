@echo off
REM === Ajusta esta ruta a tu Tomcat ===
set TOMCAT=C:\Users\daw2\Documents\apache-tomcat-9.0.123-windows-x64\apache-tomcat-9.0.123

if not exist conversor\WEB-INF\classes mkdir conversor\WEB-INF\classes
javac -encoding UTF-8 -cp "%TOMCAT%\lib\servlet-api.jar" -d conversor\WEB-INF\classes src\com\decroly\*.java
if errorlevel 1 (echo ERROR al compilar & pause & exit /b 1)

xcopy /E /I /Y conversor "%TOMCAT%\webapps\conversor"
echo.
echo Listo. Arranca Tomcat (bin\catalina.bat run) y abre:
echo http://localhost:8080/conversor/
pause
