import dataSet.DataSetLoader;
import main.Point;
import main.KMeansConfig;
import main.KMeansSequential;
import main.KMeansVisualizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
           List<Point> tmp =  DataSetLoader.loadCSV("D:\\code\\java\\untitled\\src\\dataSet\\medium.csv");
           int k = DataSetLoader.getK(tmp);
           KMeansConfig config = new KMeansConfig(k,10000,k,3000);

           ArrayList<Point> points = new ArrayList<Point>(tmp);
           KMeansSequential seq = new KMeansSequential(points,config);
           seq.run();

           // Display the visualization
           new KMeansVisualizer(seq.getClusters(), points);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




