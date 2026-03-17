@echo off
REM Timezone Calculator - Compile and Run Script

echo Compiling Java files...
cd src
javac TimezoneCalculator.java ClockPanel.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo Starting Timezone Calculator...
java TimezoneCalculator

pause
