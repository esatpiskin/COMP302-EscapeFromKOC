package model.game.logic;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class wraps Java.util.Timer to manage scheduling a single synchronized
 * task in a robust manner. <br>
 *
 * To use, simply pass a Runnable (ie a () -> void function or lambda) to
 * scheduleTask(), and then call start(). To pause, call pause(), the timer
 * saves the state of execution. Calling start() again will resume from
 * where you were.
 */
public class DomainTimer {
    private Timer timer;

    private long interval = 1000; // milliseconds

    private Runnable runnable = null;

    /**
     * Interval to execute
     * @param interval approximate time between calling the runnable
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * Schedule a runnable to be executed between intervals
     * Execution does not start until start is called
     * @param runnable task to schedule
     */
    public void scheduleTask(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Cancel the task until start() is called again
     */
    public void pause() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Start the scheduled task or resume the paused task
     */
    public void start() {
        if (runnable == null) {
            throw new RuntimeException("No task scheduled");
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        timer = new Timer();
        timer.schedule(task, 1000, this.interval);
    }

    public static void main(String[] args) throws InterruptedException {
        DomainTimer timer = new DomainTimer();
        timer.setInterval(1);
        timer.scheduleTask(new Runnable() {
            long variable = 0;

            @Override
            public void run() {
                System.out.println("Hello: " + variable);
                variable++;
            }
        });
        timer.start();
        Thread.sleep(100);
        timer.pause();
        Thread.sleep(100);
        timer.start();
        Thread.sleep(100);
        timer.pause();
    }
}
