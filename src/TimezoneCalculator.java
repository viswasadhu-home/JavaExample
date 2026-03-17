import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class TimezoneCalculator extends JFrame {
    private ClockPanel clock1;
    private ClockPanel clock2;
    private JComboBox<String> timezone1Combo;
    private JComboBox<String> timezone2Combo;
    private JTextField timeInput;
    private JLabel statusLabel;

    public TimezoneCalculator() {
        setTitle("Timezone Calculator for World Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel for time input
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Color control panel
        JPanel colorPanel = createColorPanel();
        mainPanel.add(colorPanel, BorderLayout.BEFORE_FIRST_LINE);

        // Middle panel for two clocks
        JPanel clocksPanel = new JPanel();
        clocksPanel.setLayout(new GridLayout(1, 2, 20, 0));

        // Clock 1
        JPanel clock1Panel = new JPanel();
        clock1Panel.setLayout(new BorderLayout(5, 5));
        clock1Panel.setBorder(BorderFactory.createTitledBorder("Clock 1"));

        timezone1Combo = new JComboBox<>(getAvailableTimezones());
        timezone1Combo.setSelectedItem("America/New_York");
        timezone1Combo.addActionListener(e -> onTimezone1Changed());

        clock1 = new ClockPanel(ZoneId.of("America/New_York"));
        clock1Panel.add(new JLabel("Select Timezone:"), BorderLayout.NORTH);
        clock1Panel.add(timezone1Combo, BorderLayout.SOUTH);
        clock1Panel.add(clock1, BorderLayout.CENTER);

        // Clock 2
        JPanel clock2Panel = new JPanel();
        clock2Panel.setLayout(new BorderLayout(5, 5));
        clock2Panel.setBorder(BorderFactory.createTitledBorder("Clock 2"));

        timezone2Combo = new JComboBox<>(getAvailableTimezones());
        timezone2Combo.setSelectedItem("Asia/Kolkata");
        timezone2Combo.addActionListener(e -> onTimezone2Changed());

        clock2 = new ClockPanel(ZoneId.of("Asia/Kolkata"));
        clock2Panel.add(new JLabel("Select Timezone:"), BorderLayout.NORTH);
        clock2Panel.add(timezone2Combo, BorderLayout.SOUTH);
        clock2Panel.add(clock2, BorderLayout.CENTER);

        clocksPanel.add(clock1Panel);
        clocksPanel.add(clock2Panel);

        mainPanel.add(clocksPanel, BorderLayout.CENTER);

        // Status label
        statusLabel = new JLabel("Current time in selected timezones");
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Manual Time Input"));

        panel.add(new JLabel("Enter Time (HH:mm:ss):"));
        timeInput = new JTextField(10);
        panel.add(timeInput);

        JButton submitBtn = new JButton("Show Time in Zones");
        submitBtn.addActionListener(e -> processTimeInput());
        panel.add(submitBtn);

        JButton resetBtn = new JButton("Reset to Current Time");
        resetBtn.addActionListener(e -> resetToCurrent());
        panel.add(resetBtn);

        return panel;
    }

    private JPanel createColorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Clock Background Colors"));

        panel.add(new JLabel("Clock 1:"));
        JButton cycle1Btn = new JButton("Cycle Color");
        cycle1Btn.addActionListener(e -> clock1.cycleBackgroundColor());
        panel.add(cycle1Btn);

        JButton color1RedBtn = new JButton("Red");
        color1RedBtn.addActionListener(e -> clock1.setClockBackgroundColor(new Color(255, 200, 200)));
        panel.add(color1RedBtn);

        JButton color1GreenBtn = new JButton("Green");
        color1GreenBtn.addActionListener(e -> clock1.setClockBackgroundColor(new Color(200, 255, 200)));
        panel.add(color1GreenBtn);

        JButton color1BlueBtn = new JButton("Blue");
        color1BlueBtn.addActionListener(e -> clock1.setClockBackgroundColor(new Color(200, 200, 255)));
        panel.add(color1BlueBtn);

        JButton color1YellowBtn = new JButton("Yellow");
        color1YellowBtn.addActionListener(e -> clock1.setClockBackgroundColor(new Color(255, 255, 150)));
        panel.add(color1YellowBtn);

        panel.add(new JSeparator(JSeparator.VERTICAL));

        panel.add(new JLabel("Clock 2:"));
        JButton cycle2Btn = new JButton("Cycle Color");
        cycle2Btn.addActionListener(e -> clock2.cycleBackgroundColor());
        panel.add(cycle2Btn);

        JButton color2RedBtn = new JButton("Red");
        color2RedBtn.addActionListener(e -> clock2.setClockBackgroundColor(new Color(255, 200, 200)));
        panel.add(color2RedBtn);

        JButton color2GreenBtn = new JButton("Green");
        color2GreenBtn.addActionListener(e -> clock2.setClockBackgroundColor(new Color(200, 255, 200)));
        panel.add(color2GreenBtn);

        JButton color2BlueBtn = new JButton("Blue");
        color2BlueBtn.addActionListener(e -> clock2.setClockBackgroundColor(new Color(200, 200, 255)));
        panel.add(color2BlueBtn);

        JButton color2YellowBtn = new JButton("Yellow");
        color2YellowBtn.addActionListener(e -> clock2.setClockBackgroundColor(new Color(255, 255, 150)));
        panel.add(color2YellowBtn);

        return panel;
    }

    private void processTimeInput() {
        String input = timeInput.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a time in HH:mm:ss format");
            return;
        }

        try {
            // Parse the input time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime inputTime = LocalTime.parse(input, formatter);

            // Get current date and create LocalDateTime
            LocalDate today = LocalDate.now();
            LocalDateTime inputDateTime = LocalDateTime.of(today, inputTime);

            // Update clocks with the input time
            ZoneId zone1 = ZoneId.of((String) timezone1Combo.getSelectedItem());
            ZoneId zone2 = ZoneId.of((String) timezone2Combo.getSelectedItem());

            // Convert input time (assuming it's in zone1) to both zones
            ZonedDateTime zdtInZone1 = inputDateTime.atZone(zone1);
            Instant instant = zdtInZone1.toInstant();
            ZonedDateTime zdtInZone2 = instant.atZone(zone2);

            clock1.setCustomTime(inputDateTime, zone1);
            clock2.setCustomTime(zdtInZone2.toLocalDateTime(), zone2);

            statusLabel.setText("Showing time: " + input + " in " + zone1 + 
                               " = " + zdtInZone2.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + 
                               " in " + zone2);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid time format. Please use HH:mm:ss");
        }
    }

    private void resetToCurrent() {
        timeInput.setText("");
        clock1.resetToCurrentTime();
        clock2.resetToCurrentTime();
        statusLabel.setText("Reset to current time");
    }

    private void onTimezone1Changed() {
        String selectedZone = (String) timezone1Combo.getSelectedItem();
        clock1.setTimeZone(ZoneId.of(selectedZone));
    }

    private void onTimezone2Changed() {
        String selectedZone = (String) timezone2Combo.getSelectedItem();
        clock2.setTimeZone(ZoneId.of(selectedZone));
    }

    private String[] getAvailableTimezones() {
        return ZoneId.getAvailableZoneIds().stream()
            .sorted()
            .toArray(String[]::new);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TimezoneCalculator::new);
    }
}
