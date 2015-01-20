package majorprojectsem7.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Rajat Bothra
 */
public class GlobalTimer extends Thread {

    private javax.swing.Timer timer;
    private long counter = 0;
    private ActionListener ac = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            counter++;
        }
    };

    public GlobalTimer() {
    }

    @Override
    public void start() {
        timer = new Timer(1, ac);
    }

    public long getTimer() {
        return counter;
    }

    public void reset() {
        counter = 0;
    }

    public void resetAndStop() {
        counter = 0;
        timer.stop();
    }
}
