import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class SnakeAnimation {
    private JFrame frame;
    private SnakePanel panel;
    private Timer timer;
    private boolean isPaused;
    private JButton addSnakeButton = new JButton("Add Snake");
    private JButton pauseResumeButton = new JButton("Pause");

    public SnakeAnimation() {
        // Create the frame
        frame = new JFrame("Snake Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        // Create the panel
        panel = new SnakePanel();

        addSnakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.addSnake();
            }
        });

        pauseResumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPaused) {
                    timer.start();
                    pauseResumeButton.setText("Pause");
                } else {
                    timer.stop();
                    pauseResumeButton.setText("Resume");
                }
                changeIsPaused();
            }
        });


        // Create a timer to update the animation
        timer = new Timer(50, e -> panel.update(timer));

        panel.setFocusable(true);


        frame.add(panel);
        frame.add(addSnakeButton, BorderLayout.NORTH);
        frame.add(pauseResumeButton, BorderLayout.SOUTH);
        addSnakeButton.setVisible(true);
        pauseResumeButton.setVisible(true);
        frame.setVisible(true);
    }

    private void render() {
        if(!isPaused){
            panel.update(timer);
        }
        BufferStrategy buffer = frame.getBufferStrategy();
        Graphics g = null;
        while (true) {
            try {
                g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
                panel.paintComponent(g);
            } finally {
                    g.dispose();
            }
            buffer.show();
        }
    }

    public void start() {
        timer.start();
        render();
    }

    public void stopTimer() {timer.stop() ;}
    public void startTimer() {timer.start();}
    public boolean getIsPaused(){return isPaused;}
    public void changeIsPaused(){isPaused=!isPaused;}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeAnimation animation = new SnakeAnimation();
            animation.start();
        });
    }
}


