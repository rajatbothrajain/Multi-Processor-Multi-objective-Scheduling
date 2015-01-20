package majorprojectsem7.core;

import java.util.ArrayList;

/**
 *
 * @author Rajat Bothra 
 */
public class Chromosome implements Comparable<Chromosome> {

    public ArrayList<Double> data = new ArrayList<>();
    double fitness;

    public Chromosome(ArrayList<Double> thread, double fitness) {
        data.addAll(thread);
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Chromosome x) {
        if (fitness > x.fitness) {
            return 1;
        } else if (fitness < x.fitness) {
            return -1;
        }
        return 0;
    }

    public Chromosome crossOver(Chromosome x, double percent, boolean start) throws Exception {
        if (data.size() != x.data.size()) {
            throw new Exception("illegal crossover!!!");
        }
        int n = (int) (percent * data.size());
        ArrayList<Double> data1 = new ArrayList<>();
        if (start) {
            for (int i = 0; i < n; i++) {
                data1.add(x.data.get(i));
            }
            for (int i = n; i < x.data.size(); i++) {
                data1.add(data.get(i));
            }
        } else {
            for (int i = 0; i < n; i++) {
                data1.add(data.get(i));
            }
            for (int i = n; i < data.size(); i++) {
                data1.add(x.data.get(i));
            }
        }
        return new Chromosome(data1, fitness);
    }

    public ArrayList<Double> toData() {
        return data;
    }

    @Override
    public String toString() {
        return toData().toString();
    }
}
