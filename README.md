# Timezone Calculator - Java Swing Application

A desktop application that displays two synchronized clocks showing time in different timezones, with manual time input capability.

## Features

1. **Dual Clocks**: Two analog clocks with digital time display
2. **Timezone Selection**: Dropdown menus to select any available timezone for each clock
3. **Automatic Sync**: Change timezone on one clock and see the corresponding time in the other
4. **Manual Time Input**: Enter a specific time to see what it corresponds to in different timezones
5. **Reset Function**: Reset both clocks back to current time with one click
6. **Real-time Updates**: Clocks update automatically every second

## How to Run

### Method 1: Using the Batch Script (Windows)
```
Double-click run.bat
```

### Method 2: Manual Compilation and Execution
```
cd src
javac TimezoneCalculator.java ClockPanel.java
java TimezoneCalculator
```

### Method 3: From Command Line
```powershell
cd c:\Viswanath\JavaExample
powershell -Command "cd src; javac *.java; java TimezoneCalculator"
```

## Usage

### Viewing Current Time
1. The application starts showing the current time in both clocks
2. **Clock 1** defaults to America/New_York
3. **Clock 2** defaults to Asia/Kolkata

### Changing Timezones
1. Click on the timezone dropdown under each clock
2. Select any timezone from the list (100+ timezones available)
3. The clock will immediately update to show the current time in that timezone

### Manual Time Input
1. Enter a time in the format **HH:mm:ss** in the input field (e.g., 14:30:45)
2. Click "Show Time in Zones"
3. The clocks interpret this time as if it were in Clock 1's timezone
4. Clock 2 will automatically show what that time would be in its timezone

### Reset to Current Time
1. Click the "Reset to Current Time" button to return to live clock mode
2. Clear the time input field

## Application Structure

### Files
- **TimezoneCalculator.java**: Main application window and event handling
- **ClockPanel.java**: Custom JPanel component that renders the clock
- **run.bat**: Batch script for easy compilation and execution

### Key Components

#### ClockPanel
- Renders an analog clock with hour, minute, and second hands
- Displays digital time below the clock
- Updates automatically every second
- Supports custom time input
- Works with any timezone

#### TimezoneCalculator
- Main GUI container with BorderLayout
- Manages two ClockPanel instances
- Handles timezone selection and synchronization
- Processes manual time input
- Provides status updates

## Technical Details

- **Language**: Java 8+
- **GUI Framework**: Swing
- **Date/Time API**: java.time (modern Java time API)
- **Timezone Support**: All available Java timezones (100+ timezones)
- **Update Rate**: Real-time updates every 1 second

## Requirements

- Java 8 or higher
- Windows (for batch script), or any OS for manual compilation

## Example Scenarios

1. **Check current time in multiple locations**
   - Select USA/New_York and Europe/London to see time difference

2. **Plan a meeting**
   - Set manual input to 10:00:00
   - See what time the meeting would be in different locations

3. **Handle global team coordination**
   - Keep track of working hours across different timezones
   - Adjust clocks to see implications of scheduling

## Troubleshooting

- **"Compilation failed"**: Ensure Java JDK is installed and in PATH
- **Clock not updating**: Restart the application
- **Invalid time format**: Use format HH:mm:ss (24-hour format)
- **Timezone not found**: All standard Java timezone IDs should work

## Future Enhancement Ideas

- Add multiple clocks (more than 2)
- Save favorite timezone pairs
- Add alarm functionality
- Display timezone offset information
- Add city/country mapping for easier timezone selection
