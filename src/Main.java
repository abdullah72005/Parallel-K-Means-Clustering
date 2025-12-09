import dataSet.DataSetLoader;
import main.Point;
import main.Cluster;
import main.KMeansConfig;
import main.KMeansSequential;
import main.KMeansVisualizer;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        try {
           Map<Integer, Point> pointsHash =  DataSetLoader.loadCSV("/mnt/c/Users/abdul/OneDrive/Documents/VScode/Parallel-K-Means-Clustering/src/dataSet/small.csv");
           int k = DataSetLoader.getK(pointsHash);
           KMeansConfig config = new KMeansConfig(k,10000,1,7554);

           KMeansSequential seq = new KMeansSequential(pointsHash,config); 
           seq.run();
           Cluster[] clusters = seq.getClusters();
           Map<Integer, Integer> assignments = seq.getPointsToCentroidHash();
           // print first 10 assignments
           int count = 0;
           for (Map.Entry<Integer, Integer> entry : assignments.entrySet()) {
               if (count >= 10) break;
               System.out.println("Point ID: " + entry.getKey() + " assigned to Centroid: " + entry.getValue());
               count++;
           }
           System.out.println("==============================");
           for(Cluster c : clusters){
               System.out.println(c.toString());
           }
           
           // Display visualization
           new KMeansVisualizer(pointsHash, assignments, clusters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}




