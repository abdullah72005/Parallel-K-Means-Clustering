import dataSet.DataSetLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.*;

public class Main {
    public static void main(String[] args) {

        try {
           List<Point> tmp =  DataSetLoader.loadCSV("dataSet/great_large.csv");
           int k = DataSetLoader.getK(tmp);
           KMeansConfig config = new KMeansConfig(k,10000,k/3,50, 1000,5);
           Cluster[] cls;
           ArrayList<Point> points = new ArrayList<Point>(tmp);


        //    KMeansSequential seq = new KMeansSequential(points,config);
        //    seq.run();
        // cls = seq.getClusters();

           // KMeansParallel par = new KMeansParallel(points,config);
           // par.run();
           //cls = par.getClusters();


            KMeansExperiment seq = new KMeansExperiment(points,config);
            seq.runBoth();
            cls = seq.getParallel().getClusters();

            // KMeansExperiment exp = new KMeansExperiment(config);
            Cluster[] cls2 = seq.getSequential().getClusters();
            // System.err.println(exp.calculate_SSE(cls));
           new KMeansVisualizer(cls, points);
           new KMeansVisualizer(cls2,points);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




