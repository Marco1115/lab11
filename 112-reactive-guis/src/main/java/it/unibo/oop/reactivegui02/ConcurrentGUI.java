package it.unibo.oop.reactivegui02;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.LoggerFactory;

import it.unibo.oop.JFrameUtil;

import org.slf4j.Logger;

import java.io.Serial;
import java.lang.reflect.InvocationTargetException;

/**
 * Second example of reactive GUI.
 */
public final class ConcurrentGUI extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentGUI.class);

    private final JLabel display = new JLabel();

    /**
     * Builds a new GUI.
     */
    public ConcurrentGUI() {
        super();
        JFrameUtil.dimensionJFrame(this);
        final JPanel pane = new JPanel();
        pane.add(this.display);
        final JButton up = new JButton("up");
        final JButton down = new JButton("down");
        final JButton stop = new JButton("stop");
        pane.add(up);
        pane.add(down);
        pane.add(stop);
        this.setContentPane(pane);
        this.setVisible(true);

        final Agent counterAgent = new Agent();
        new Thread(counterAgent).start();

        // Handlers
        up.addActionListener(a -> counterAgent.countUp());
        down.addActionListener(a -> counterAgent.countDown());
        stop.addActionListener(a -> {
            counterAgent.stopCounting();
            up.setEnabled(false);
            down.setEnabled(false);
            stop.setEnabled(false);
        });
    }

    private final class Agent implements Runnable {

        private volatile boolean stopped;
        private volatile boolean up = true;
        private int count;

        @Override
        public void run() {
            while (!this.stopped) {
                try {
                    final String nextText = String.valueOf(count);
                    SwingUtilities.invokeAndWait(() -> ConcurrentGUI.this.display.setText(nextText));
                    if (this.up) {
                        count++;
                    } else {
                        count--;
                    }
                    Thread.sleep(100);
                } catch (InvocationTargetException | InterruptedException e) {
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
}
