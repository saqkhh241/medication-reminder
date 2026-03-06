@echo off
cd /d %~dp0
start "" java -jar target\medication-reminder-0.0.1-SNAPSHOT.jar
timeout /t 5 /nobreak > nul
start "" http://localhost:8081/medicines