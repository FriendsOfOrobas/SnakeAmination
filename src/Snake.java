import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake {
    private ArrayList<Segment> segments;
    private SnakePanel panel;
    private Color color;
    Thread thread;
    private final Object lock = new Object();

    public Snake(int x, int y,SnakePanel panel) {
        this.panel = panel;
        this.segments = new ArrayList<>();
        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        color = new Color(r, g, b);
        int numOfSeg = new Random().nextInt(8)+2;
        for (int i = 0; i < numOfSeg; i++) {
            double angle = Math.PI * Math.random();
            double amplitude = 20 * Math.random();
            double frequency = 0.01 * Math.random();
            double speed = 5 * Math.random();
            segments.add(new Segment(x, y, angle, amplitude, frequency, speed, color));
        }

        thread = new Thread(() -> {
            while (!Thread.interrupted() && panel != null) {
                //update(timer);
                panel.repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        thread.start();
    }

    public void checkCollision(Snake other) {
        if (segments.size() > 0) {
            synchronized (lock) {
                Segment head = segments.get(0);
                for (Segment segment : other.segments) {
                    if (head.x < segment.x + 5 && head.x > segment.x - 5 && head.y < segment.y + 5 && head.y > segment.y - 5) {
                        // Collision detected
                        segments.addAll(other.segments);
                        other.segments.clear();
                        return;
                    }
                }
            }
        }
    }
    public void update(Timer timer) {
        if (timer.isRunning())
        if(segments.size()>0){
            Segment head = segments.get(0);
            int x = head.x;
            int y = head.y;
            int width = panel.getWidth();
            int height = panel.getHeight();
            if (x + 20 > width || x < 0) {
                head.angle = Math.PI - head.angle;
            }
            if (y + 20 > height || y < 0) {
                head.angle = -head.angle;
            }

            x += (int)(head.speed * Math.cos(head.angle));
            y += (int)(head.amplitude * Math.sin(head.angle));
            head.x = x;
            head.y = y;

            for (int i = segments.size() - 1; i > 0; i--) {
                segments.get(i).update(segments.get(i - 1));
                // Check for collision with other snakes

                    for (Snake other : panel.getSnakes()) {
                        if (other != this && segments.size()>0) {
                           checkCollision(other);
                        }

                }
            }
        }
    }

    public int getSize(){return segments.size();}

    public void draw(Graphics g) {
        if(segments.size()>0){
            for (int i = 0; i < segments.size() && segments.size() > 0 ; i++) {
                Segment current = segments.get(i);
                current.draw(g);
                if (i > 0 && segments.size()>0) {
                    Segment prev = segments.get(i-1);
                    g.drawLine(prev.x + 10, prev.y + 10, current.x + 10, current.y + 10);
                }
            }
        }
    }
}

