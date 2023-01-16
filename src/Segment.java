import java.awt.*;

public class Segment {
    int x, y;
    double angle;
    double amplitude;
    double frequency;
    double speed;

    Color color;

    public Segment(int x, int y, double angle, double amplitude, double frequency, double speed, Color color) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.speed = speed;
        this.color = color;
    }

    public void update(Segment prevSegment) {
        angle += frequency;
        x = (int)(prevSegment.x + speed/20 * Math.cos(angle));
        y = (int)(prevSegment.y + amplitude * Math.sin(angle));
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 10, 20);
    }
}