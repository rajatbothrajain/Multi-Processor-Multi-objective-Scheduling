package majorprojectsem7.core;

import Utility.support.kit.utilities.MessagePane;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import static majorprojectsem7.core.FitnessFunction.cost;
import static majorprojectsem7.core.FitnessFunction.getProcessors;
import static majorprojectsem7.core.FitnessFunction.getTasks;
import static majorprojectsem7.core.FitnessFunction.numberOfProcessor;
import static majorprojectsem7.core.FitnessFunction.numberOfTasks;
import static majorprojectsem7.core.FitnessFunction.p;
import static majorprojectsem7.core.FitnessFunction.tasks;
import majorprojectsem7.gui.ScheduledVisualDiagram;
import majorprojectsem7.gui.ProcessorDisplay;

/**
 *
 * @author Rajat Bothra
 */
public class Implementor {

    private static MessagePane mp = new MessagePane();

    public static void processInput() throws IOException, FileNotFoundException, NumberFormatException {
        BufferedReader br = new BufferedReader(new FileReader("in1.txt"));
        numberOfProcessor = Integer.parseInt(br.readLine());
        numberOfTasks = Integer.parseInt(br.readLine());
        p = new Processor[numberOfProcessor];
        for (int i = 0; i < getProcessors().length; i++) {
            p[i] = new Processor(i);
        }
        tasks = new Graph[numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            double exec = Double.parseDouble(br.readLine());
            tasks[i] = new Graph(i, exec);
        }
        for (int i = 0; i < numberOfTasks; i++) {
            int numOfChildren = Integer.parseInt(br.readLine());
            String x[] = br.readLine().trim().split(" ");
            for (int j = 0; j < numOfChildren; j++) {
                getTasks()[i].addChildren(getTasks()[Integer.parseInt(x[j]) - 1]);
            }
            System.out.println();
        }
        cost = new double[numberOfTasks][numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            String x[] = br.readLine().trim().split(" ");
            for (int j = 0; j < x.length; j++) {
                cost[i][j] = Double.parseDouble(x[j]);
            }
        }

        System.out.println("Topological order : ");
        Arrays.sort(tasks);
        for (int i = 0; i < numberOfTasks; i++) {
            System.out.println(getTasks()[i].getName() + " : " + getTasks()[i].getTopologicalIndex());
        }
        for (int i = 0; i < numberOfTasks; i++) {
            getTasks()[i].allotTo(getProcessors()[(i % getProcessors().length)]);
            getProcessors()[(i % getProcessors().length)].allocateTask(getTasks()[i]);
        }
        mp.setTitle("INFO");
        StringBuilder sb = new StringBuilder("INITIAL TASK PROCESS ALLOCATION");
        for (Graph task : getTasks()) {
            sb.append(task.getName()).append(" : ").append(task.getAlloted().toString()).append(", startTime = ").append(task.getStartTime()).append(", endTime = ").append(task.getEndTime());
            sb.append("\n");
        }
        for (Processor p1 : getProcessors()) {
            sb.append(p1).append(" : ").append(p1.getTasksAllocated()).append("\n");
        }
        mp.setMessage(sb.toString());
        mp.setVisible(true);
        br.close();
    }

    public static void allocate(ArrayList<Chromosome> spins) {
        //System.out.println("chromosome : " + spins);
        for (Processor p1 : p) {
            p1.reset();
        }
        for (int j = 0; j < tasks.length; j++) {
            int maxAllotedToProcessor = 0;
            double max = Double.MIN_VALUE;
            for (int i = 0; i < tasks.length; i++) {
                if (i != j) {
                    for (int k = 0; k < p.length; k++) {
                        double del = 0, ct = 0;
                        if (tasks[i].getTopologicalIndex() < tasks[j].getTopologicalIndex()) {
                            del = 1;
                        }
                        if (tasks[i].isAlloted()) {
                            ct = tasks[i].getEndTime();
                        }
                        double vik = spins.get(i).toData().get(k);
                        double vjk = spins.get(j).toData().get(k);
                        double toCalc = del * ct + cost[i][j] * (1 - (vik * vjk)) * vjk + 1 / p[k].getTimer();
                        //System.out.println(toCalc + "," + del * ct + "," + cost[i][j] * (1 - (vik * vjk)) * vjk);
                        if (toCalc > max) {
                            maxAllotedToProcessor = k;
                            max = toCalc;
                        }
                    }
                }
            }
            //System.out.println("maxAllotedProcessor->" + maxAllotedToProcessor);
            tasks[j].allotTo(p[maxAllotedToProcessor]);
            p[maxAllotedToProcessor].allocateTask(tasks[j]);
        }
    }

    public static void main(String args[]) throws Exception {
        processInput();
        //mp.setTitle("INFO !!");
        MeanFieldAnnealing an = new MeanFieldAnnealing();

        System.out.println("Number of tasks : " + numberOfTasks);
        System.out.println("Number of processors : " + numberOfProcessor);
        System.out.println("Pareto fronts : ");
        an.getAllocationByMFA(numberOfTasks, numberOfProcessor); // *check static 
        System.out.println();
        Graph[] ans = FitnessFunction.getTasks();
        Processor[] p = FitnessFunction.getProcessors();

        System.out.println("final allocation\n");
        System.out.println("Task\tProcessor\t");
        StringBuilder sb = new StringBuilder("TASK : PROCESSOR ALLOCATION : \n");
        for (int i = 0; i < ans.length; i++) {
            sb.append("").append(ans[i].getName()).append(" : ").append(ans[i].getAlloted().toString()).append(", startTime = ").append(ans[i].getStartTime()).append(", endTime = ").append(ans[i].getEndTime()).append("\n");
        }
        for (int i = 0; i < p.length; i++) {
            sb.append(p[i].getTasksAllocated()).append("\n");
        }
        ScheduledVisualDiagram gm = new ScheduledVisualDiagram(p);
        gm.setVisible(true);
        mp.setMessage(sb.toString());
        System.out.println(sb.toString());
        mp.setVisible(true);
    }
}
