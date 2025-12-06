package it.unibo.oop.reactivegui03;

import java.io.Serial;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.oop.JFrameUtil;

/**
 * Third experiment with reactive gui.
 */
public final class AnotherConcurrentGUI extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AnotherConcurrentGUI.class);

    private final JLabel display = new JLabel();
    private final JButton up = new JButton("up");
    private final JButton down = new JButton("down");
    private final JButton stop = new JButton("stop");

    private final transient Agent counterAgent = new Agent();

    /**
     * Builds a new GUI.
     */
    public AnotherConcurrentGUI() {
        super();
        JFrameUtil.dimensionJFrame(this);
        final JPanel pane = new JPanel();
        pane.add(this.display);
        pane.add(up);
        pane.add(down);
        pane.add(stop);
        this.setContentPane(pane);
        this.setVisible(true);

        new Thread(counterAgent).start();

        // Handlers
        up.addActionListener(a -> counterAgent.countUp());
        down.addActionListener(a -> counterAgent.countDown());
        stop.addActionListener(a -> this.stopCountingAgent());
    }

    private void stopCountingAgent() {
        counterAgent.stopCounting();
        SwingUtilities.invokeLater(() -> {
            this.up.setEnabled(false);
            this.down.setEnabled(false);
            this.stop.setEnabled(false);
        });
    }

    private final class Agent implements Runnable {

        private volatile boolean stopped;
        private volatile boolean up = true;
        private int count;

        @Override
        public void run() {
            new Thread(new StopAgent()).start();
            while (!this.stopped) {
                try {
                    final String nextText = String.valueOf(count);
                    SwingUtilities.invokeAndWait(() -> AnotherConcurrentGUI.this.display.setText(nextText));
                    if (this.up) {
                        count++;
                    } else {
                        count--;
                    }
                    Thread.sleep(100);
                } catch (final InvocationTargetException | InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }

        public void stopCounting() {
            this.stopped = true;
        }

        public void countUp() {
            this.up = true;
        }

        public void countDown() {
            this.up = false;
        }
    }

    private final class StopAgent implements Runnable {

        private static final long STOP_TIME = 10_000;

        @Override
        public void run() {
            try {
                Thread.sleep(STOP_TIME);
                AnotherConcurrentGUI.this.stopCountingAgent();
            } catch (final InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

    }
}
