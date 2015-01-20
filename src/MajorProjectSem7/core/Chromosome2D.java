package majorprojectsem7.core;

import java.util.ArrayList;

/**
 *
 * @author Rajat Bothra
 */
public class Chromosome2D {

    ArrayList<Chromosome> data = new ArrayList<>();

    public Chromosome2D(ArrayList<Chromosome> rawData) {
        data.addAll(rawData);
    }

    public Chromosome2D crossOver(Chromosome2D x, double percent, boolean start) throws Exception {
        if (data.size() != x.data.size()) {
            throw new Exception("illegal crossover!!!");
        }
        int n = (int) (percent * data.size());
        ArrayList<Chromosome> data1 = new ArrayList<>();
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
        return new Chromosome2D(data1);
    }

    public ArrayList<Chromosome> toData() {
        return data;
    }

    @Override
    public String toString() {
        return toData().toString();
    }
}
