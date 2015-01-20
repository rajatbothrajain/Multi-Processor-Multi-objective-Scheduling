package majorprojectsem7.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

/**
 *
 * @author Rajat Bothra
 */
public class FitnessFunction {

    static double interCommunicationCost = 1.0;
    public static double multiplierFittness3 = 0.7;
    public static FitnessValues computedFitnessValues = null;
    public static int numberOfProcessor;
    public static int numberOfTasks;
    public static Processor[] p;
    public static Graph[] tasks;
    public static double[][] cost;

    /**
     * @return the p
     */
    public static Processor[] getProcessors() {
        return p;
    }

    /**
     * @return the tasks
     */
    public static Graph[] getTasks() {
        return tasks;
    }

    protected static class FitnessValues {

        private double fitness1 = 0;
        private double fitness2 = 0;
        private double fitness3 = 0;

        public FitnessValues(double v1, double v2, double v3) {
            fitness1 = v1;
            fitness2 = v2;
            fitness3 = v3;
        }

        public boolean equals(FitnessValues fv) {
            return (fv.fitness1 == fitness1 && fv.fitness2 == fitness2 && fv.fitness3 == fitness3);
        }

        /**
         * @return the fitness1
         */
        public double getFitness1() {
            return fitness1;
        }

        /**
         * @return the fitness2
         */
        public double getFitness2() {
            return fitness2;
        }

        /**
         * @return the fitness3
         */
        public double getFitness3() {
            return fitness3;
        }

        @Override
        public String toString() {
            return "{" + fitness1 + "," + fitness2 + "," + fitness3 + ")";
        }

    }

    /**
     *
     * @return
     */
    public static FitnessValues computeFitnessValues() {

        double globalTimer = 0; // create a global-timer 

        for (Graph taskGraph1 : tasks) {
            taskGraph1.setReferenceCounter(taskGraph1.getParent().size());
        }

        /* */
        for (int i = 0; i < tasks.length; i++) {
            Graph task = tasks[i];
            if (task.getReferenceCounter() != 0) {
                continue;
            }
            double localTimer = tasks[i].getAlloted().getTimer();
            if (globalTimer > localTimer) {
                tasks[i].getAlloted().updateTimer(globalTimer + tasks[i].getExecutionTime());
                globalTimer += 1.0;
            } else if (globalTimer == localTimer) {
                for (Graph children : tasks[i].getChildren()) {
                    children.setReferenceCounter(children.getReferenceCounter() - 1);
                }
            }
        }

        double fitness1 = -1, localSum = 0;

        for (Processor pi : p) {
            double timer = pi.getTimer();
            localSum += timer;
            //System.out.println("localSum + =timer->" + timer + ", process-name : " + pi.toString());
            if (timer > fitness1) {
                fitness1 = timer;
            }
        }
        //System.out.println("graphLength =" + tasks.length);
        for (int i = 0; i < tasks.length; i++) {
            ArrayList<Graph> parent = tasks[i].getParent();
            for (int j = 0; j < parent.size(); j++) {
                //System.out.println("j = " + j + " i = " + i);
                if (parent.get(j).isAlloted()) {
                    fitness1 += cost[j][i];
                    //System.out.println("fitness1 update : "+cost[i][j]);
                }
            }
        }
        double fitness2 = localSum / p.length;
        double fitness3 = new BigDecimal("" + (multiplierFittness3 * localSum)).round(MathContext.DECIMAL32).doubleValue();
        return new FitnessValues(fitness1, fitness2, fitness3);
    }

    public static void geneticAlgoCalculateFitness() throws Exception {
        computedFitnessValues = computeFitnessValues();
    }

}
