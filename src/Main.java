import dataSet.DataSetLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import main.Cluster;
import main.KMeansConfig;
import main.KMeansParallel;
import main.KMeansVisualizer;
import main.Point;

public class Main {
    public static void main(String[] args) {

        try {
           List<Point> tmp =  DataSetLoader.loadCSV("dataSet/great_large.csv");
           int k = DataSetLoader.getK(tmp);
           KMeansConfig config = new KMeansConfig(k,10000,k/3,51930, 1000);
           Cluster[] cls;
           ArrayList<Point> points = new ArrayList<Point>(tmp);


        //    KMeansSequential seq = new KMeansSequential(points,config);
        //    seq.run();
        // cls = seq.getClusters();

            KMeansParallel par = new KMeansParallel(points,config);
            par.run();
            cls = par.getClusters();



            // KMeansExperiment exp = new KMeansExperiment(config);

            // System.err.println(exp.calculate_SSE(cls));

           // Display the visualization
           new KMeansVisualizer(cls, points);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




