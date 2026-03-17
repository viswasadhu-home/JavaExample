import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.swing.*;

public class ClockPanel extends JPanel {
    private ZoneId timeZone;
    private LocalDateTime customTime;
    private boolean useCustomTime;
    private Timer timer;
    private Color clockBackgroundColor;
    private int colorIndex;
    private Color[] colorPalette;

    public ClockPanel(ZoneId timeZone) {
        this.timeZone = timeZone;
        this.useCustomTime = false;
        this.customTime = null;

        // Define color palette
        colorPalette = new Color[]{
            Color.WHITE,
            new Color(255, 200, 200),      // Light Red
            new Color(200, 255, 200),      // Light Green
            new Color(200, 200, 255),      // Light Blue
            new Color(255, 255, 150),      // Light Yellow
            new Color(255, 200, 255),      // Light Magenta
            new Color(150, 255, 255),      // Light Cyan
            new Color(255, 220, 180)       // Light Orange
        };
        
        colorIndex = 0;
        clockBackgroundColor = colorPalette[colorIndex];

        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Timer to update the clock every second
        timer = new Timer(1000, e -> repaint());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 2 - 20;

        // Draw clock face
        drawClockFace(g2d, centerX, centerY, radius);

        // Get current time or custom time
        LocalDateTime displayTime;
        if (useCustomTime && customTime != null) {
            displayTime = customTime;
        } else {
            displayTime = LocalDateTime.now(timeZone);
        }

        // Draw clock hands
        drawHands(g2d, centerX, centerY, radius, displayTime);

        // Draw digital time below the clock
        drawDigitalTime(g2d, centerX, centerY, displayTime);
    }

    private void drawClockFace(Graphics2D g2d, int centerX, int centerY, int radius) {
        // Draw clock circle
        g2d.setColor(clockBackgroundColor);
        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        // Draw hour markers
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30 - 90);
            int x = (int) (centerX + (radius - 30) * Math.cos(angle));
            int y = (int) (centerY + (radius - 30) * Math.sin(angle));

            String text = String.valueOf(i);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(text, x - fm.stringWidth(text) / 2, y + fm.getAscent() / 2);
        }

        // Draw tick marks
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < 60; i++) {
            double angle = Math.toRadians(i * 6);
            int x1 = (int) (centerX + radius * Math.cos(angle));
            int y1 = (int) (centerY + radius * Math.sin(angle));
            int x2 = (int) (centerX + (radius - 8) * Math.cos(angle));
            int y2 = (int) (centerY + (radius - 8) * Math.sin(angle));
            g2d.drawLine(x1, y1, x2, y2);
        }

        // Draw center dot
        g2d.fillOval(centerX - 5, centerY - 5, 10, 10);
    }

    private void drawHands(Graphics2D g2d, int centerX, int centerY, int radius, LocalDateTime time) {
        int hour = time.getHour() % 12;
        int minute = time.getMinute();
        int second = time.getSecond();

        // Hour hand
        double hourAngle = Math.toRadians(hour * 30 + minute * 0.5 - 90);
        int hourLength = (int) (radius * 0.5);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(6));
        g2d.drawLine(centerX, centerY,
                (int) (centerX + hourLength * Math.cos(hourAngle)),
                (int) (centerY + hourLength * Math.sin(hourAngle)));

        // Minute hand
        double minuteAngle = Math.toRadians(minute * 6 + second * 0.1 - 90);
        int minuteLength = (int) (radius * 0.7);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(centerX, centerY,
                (int) (centerX + minuteLength * Math.cos(minuteAngle)),
                (int) (centerY + minuteLength * Math.sin(minuteAngle)));

        // Second hand
        double secondAngle = Math.toRadians(second * 6 - 90);
        int secondLength = (int) (radius * 0.85);
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(centerX, centerY,
                (int) (centerX + secondLength * Math.cos(secondAngle)),
                (int) (centerY + secondLength * Math.sin(secondAngle)));
    }

    private void drawDigitalTime(Graphics2D g2d, int centerX, int centerY, LocalDateTime time) {
        String digitalTime = String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(digitalTime, centerX - fm.stringWidth(digitalTime) / 2, centerY + 120);

        String tzName = timeZone.getId();
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString(tzName, centerX - fm.stringWidth(tzName) / 2, centerY + 140);
    }

    public void setTimeZone(ZoneId newTimeZone) {
        this.timeZone = newTimeZone;
        repaint();
    }

    public void setCustomTime(LocalDateTime time, ZoneId zone) {
        this.customTime = time;
        this.useCustomTime = true;
        this.timeZone = zone;
        repaint();
    }

    public void resetToCurrentTime() {
        this.useCustomTime = false;
        this.customTime = null;
        repaint();
    }

    // Color management methods
    public void setClockBackgroundColor(Color color) {
        this.clockBackgroundColor = color;
        repaint();
    }

    public void cycleBackgroundColor() {
        colorIndex = (colorIndex + 1) % colorPalette.length;
        clockBackgroundColor = colorPalette[colorIndex];
        repaint();
    }

    public Color getClockBackgroundColor() {
        return clockBackgroundColor;
    }

    public void setColorPalette(Color[] colors) {
        if (colors != null && colors.length > 0) {
            colorPalette = colors;
            colorIndex = 0;
            clockBackgroundColor = colorPalette[colorIndex];
            repaint();
        }
    }

    public LocalDateTime getDisplayTime() {
        if (useCustomTime && customTime != null) {
            return customTime;
        } else {
            return LocalDateTime.now(timeZone);
        }
    }

    @Override
    public void removeNotify() {
        timer.stop();
        super.removeNotify();
    }
}
