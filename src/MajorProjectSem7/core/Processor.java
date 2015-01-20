package majorprojectsem7.core;

import java.util.ArrayList;

/**
 *
 * @author Rajat Bothra
 */
public class Processor implements Comparable<Processor>, Cloneable {

    private double timer = 0;
    private String number;
    
    private ArrayList<Graph> tasksAllocated = new ArrayList<>();
    ArrayList<Double> startTime = new ArrayList<>();

    public void reset() {
        timer = 0;
        tasksAllocated = new ArrayList<>();
        startTime = new ArrayList<>();
    }

    public Processor(int num) {
        number = "processor-" + num + " ";
    }

    public void updateTimer(double value) {
        timer = value;
    }

    public int getId() {
        return Integer.parseInt(number.trim().replace("processor-", ""));
    }

    public void allocateTask(Graph g) {
        //whenever the task g is allocated to this processor, it will update the current timer
        //if(this.getTimer() > g.getStartTime())
        getTasksAllocated().add(g);
        updateTimer(g.getEndTime());        
    }

    @Override
    public String toString() {
        return number;
    }

    /**
     * @return the tasksAllocated
     */
    public ArrayList<Graph> getTasksAllocated() {
        return tasksAllocated;
    }

    /**
     * @return the timer
     */
    public double getTimer() {
        return timer;
    }

    @Override
    public int compareTo(Processor o) {
        int num1 = Integer.parseInt(number.trim().replace("processor-", ""));
        int num2 = Integer.parseInt(o.number.trim().replace("processor-", ""));
        if (num1 > num2) {
            return 1;
        } else if (num1 == num2) {
            return 0;
        }
        return -1;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        Processor p = new Processor(0);
        p.number = number;
        p.startTime.addAll(startTime);
        p.timer = timer;
        p.tasksAllocated.addAll(tasksAllocated);
        return p;
    }

}
