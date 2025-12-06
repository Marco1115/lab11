package it.unibo.oop.reactivegui02;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.LoggerFactory;

import it.unibo.oop.JFrameUtil;

import org.slf4j.Logger;

import java.io.Serial;

/**
 * Second example of reactive GUI.
 */
public final class ConcurrentGUI extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentGUI.class);
    private final JLabel display = new JLabel("-5");

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
    }
}
