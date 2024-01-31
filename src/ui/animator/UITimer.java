package ui.animator;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * The UITimer class for scheduling draw events
 */
public class UITimer {

    // frequency of the timer in Hertz
    private static final int REFRESH_RATE = 50;
    private static final int TIMER_INTERVAL = 1000 / REFRESH_RATE;

    private static UITimer instance;
    private static boolean initialized;
    private static Timer timer;

    /**
     * Get the Timer instance
     * @return the instance
     */
    public static synchronized UITimer getInstance() {
        if (!initialized) {
            instance = new UITimer();
            initialized = true;
        }
        return instance;
    }

    private UITimer() {
        timer = new Timer(TIMER_INTERVAL, null);
        timer.start();
    }

    /**
     * Call this to add an action listener to this timer, which will periodically call actionPerformed
     * @param listener the listener to add
     */
    public void addActionListener(ActionListener listener) {
        timer.addActionListener(listener);
    }
}
