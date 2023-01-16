import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakePanel extends JPanel {
    private ArrayList<Snake> snakes;

    public SnakePanel() {
        Random random = new Random();
        int numberOfSnakes = random.nextInt(10) + 2;// generates a random number between 1 and 10
        int x,y;
        snakes = new ArrayList<>();
        for(int i = 0; i < numberOfSnakes; i++) {
            x = random.nextInt(300);
            y = random.nextInt(300);
            Snake snake = new Snake(x,y,this);
            snakes.add(snake);
        }

    }

    public void update(Timer timer) {
        if(timer.isRunning()) {
            // Update the positions of the snakes
            for (Snake snake : snakes) {
                if (snake.getSize() > 0 && snake != null) snake.update(timer);
            }
        }
        // Repaint the panel
        repaint();
    }
    public void addSnake() {
        int x = (int)(Math.random() * (getWidth() - 20));
        int y = (int)(Math.random() * (getHeight() - 20));
        snakes.add(new Snake(x, y,this));
    }

    @Override
    public void paintComponent(Graphics g) {
        repaint();
        super.paintComponent(g);

        // Draw the snakes
        for (Snake snake : snakes) {
            if(snake.getSize()>0) snake.draw(g);
        }
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }
}