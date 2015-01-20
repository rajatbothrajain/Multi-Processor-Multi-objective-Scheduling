package majorprojectsem7.core;

import java.util.ArrayList;
import java.util.Random;
import static majorprojectsem7.core.FitnessFunction.cost;

public class MeanFieldAnnealingRajat {

    long range = 2730;

    //double constants[][] = new double[10000][1000];
    double randomTemperature(long range) {
        double x;
        x = (new Random().nextDouble() * range);
        return x;
    }

    ArrayList<ArrayList<Double>> generateRandomSpins(int numberOfTasks, int numberOfProcessors, long range) {
        ArrayList<ArrayList<Double>> list = new ArrayList<>(numberOfTasks);
        int max = numberOfTasks * numberOfProcessors;
        for (int i = 0; i < numberOfTasks; i++) {
            ArrayList<Double> v = new ArrayList<>(numberOfProcessors);
            for (int j = 0; j < numberOfProcessors; j++) {
                v.add(0.5);
            }
            list.add(v);
        }
        return list;
    }

    private boolean isSignificantValue(double delta) {
        return (Math.log(delta) > 1);
    }

    public void getAllocationByMFA(int numberOfTasks, int numberOfProcessors) throws Exception {
        ArrayList<Double> externSpin = new ArrayList<>();
        ArrayList<ArrayList<Double>> externPopulation = new ArrayList<>();
        FitnessFunction.computedFitnessValues = FitnessFunction.computeFitnessValues();
//double T0=randomTemperature(2730);
        double alpha = 1.50;
        double T0 = 5;
        double delE = -2;
        int selectedIndex;
        double R;
        double ujk;
        double vjk;
        double T = T0;
        ArrayList<Double> nujk = new ArrayList<>();
        ArrayList<Double> mujk = new ArrayList<>();

        ArrayList<ArrayList<Double>> initialSpin = generateRandomSpins(numberOfTasks, numberOfProcessors, 1);

        while (T < 100) {
            while (isDecreasing(delE)) {
                selectedIndex = new Random().nextInt(numberOfTasks);
                R = new Random().nextDouble();
                if (R < 0.5) {
                    // compute the mean field vector corresponding to ith spin
                    for (int l = 1; l <= numberOfProcessors; l++) {
                        ujk = 0;
                        for (int i = 1; i <= numberOfProcessors; i++) {
                            for (int j = 1; i != j && j <= selectedIndex; j++) {
                                for (int k = 1; k <= l; k++) {
//ujk=c[i][j]
                                    ujk += ((cost[i - 1][j - 1] - FitnessFunction.multiplierFittness3) * (1 - 2 * initialSpin.get(i - 1).get(k - 1) * initialSpin.get(j - 1).get(k - 1))) / T;
                                }
                            }
                            ujk += FitnessFunction.computedFitnessValues.getFitness2() + FitnessFunction.computedFitnessValues.getFitness3();
                        }

                        vjk = 0.5 * (1 + Math.tanh(ujk));
                        nujk.add(vjk);
                        mujk.add(ujk);
                        
                        System.out.println("ujk"+vjk);
                        System.out.println(ujk);
                        
                    }
                    double sum = 0;
                    for (int i = 0; i < nujk.size(); i++) {
                        sum += mujk.get(i) * (nujk.get(i) - initialSpin.get(selectedIndex).get(i));
                    }
                    delE = sum;

                    for (int j = 0; j < numberOfProcessors; j++) {
                        initialSpin.get(selectedIndex).set(j, nujk.get(j));
                    }
                    nujk.clear();
                    mujk.clear();
                } else {
                    if (externPopulation.size() > 0) {
                        int R1 = new Random().nextInt(externPopulation.size());
//crossover

//externSpin//
                    }
                }
//Update T
                T = alpha * T;
            }
            System.out.println("delE = " + delE + ", T = " + T);
            //System.out.println(initialSpin);
        }

    }

    private static boolean isDecreasing(double delE) {
        return delE < 0;
    }

}
